package com.ennoukra.carnetcontacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button boutonCharger, boutonSynchroniser, boutonChercher;
    private EditText champRecherche;
    private RecyclerView listeContacts;
    private AdaptateurContacts adaptateur;
    private List<EntreeContact> listeLocale = new ArrayList<>();
    private ServiceReseau serviceReseau;

    // Gère le résultat de la demande de permission d'accès aux contacts
    private final ActivityResultLauncher<String> demandePermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), accordee -> {
                if (accordee) {
                    chargerContacts();
                } else {
                    afficherMessage("Permission d'accès aux contacts refusée");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiserVues();
        configurerListe();
        serviceReseau = ClientHttp.getInstance().create(ServiceReseau.class);

        boutonCharger.setOnClickListener(v -> verifierEtChargerContacts());
        boutonSynchroniser.setOnClickListener(v -> envoyerVersServeur());
        boutonChercher.setOnClickListener(v -> rechercherContacts());
    }

    private void initialiserVues() {
        boutonCharger      = findViewById(R.id.boutonCharger);
        boutonSynchroniser = findViewById(R.id.boutonSynchroniser);
        boutonChercher     = findViewById(R.id.boutonChercher);
        champRecherche     = findViewById(R.id.champRecherche);
        listeContacts      = findViewById(R.id.listeContacts);
    }

    private void configurerListe() {
        listeContacts.setLayoutManager(new LinearLayoutManager(this));
        adaptateur = new AdaptateurContacts(listeLocale);
        listeContacts.setAdapter(adaptateur);
    }

    // ─── Lecture des contacts du téléphone ───────────────────────────────────

    private void verifierEtChargerContacts() {
        boolean permissionOk = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED;

        if (permissionOk) {
            chargerContacts();
        } else {
            demandePermission.launch(Manifest.permission.READ_CONTACTS);
        }
    }

    private void chargerContacts() {
        listeLocale.clear();

        String[] colonnes = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        String tri = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";

        Cursor curseur = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                colonnes, null, null, tri
        );

        if (curseur == null) {
            afficherMessage("Impossible d'accéder aux contacts du téléphone");
            return;
        }

        int colNom = curseur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int colTel = curseur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        while (curseur.moveToNext()) {
            String nom       = curseur.getString(colNom);
            String telephone = curseur.getString(colTel);

            // Ignore les entrées incomplètes (nom ou numéro vide)
            if (!TextUtils.isEmpty(nom) && !TextUtils.isEmpty(telephone)) {
                listeLocale.add(new EntreeContact(nom, telephone));
            }
        }
        curseur.close();

        adaptateur.rafraichirDonnees(listeLocale);
        afficherMessage(listeLocale.size() + " contact(s) chargé(s)");
    }

    // ─── Envoi des contacts vers le serveur ──────────────────────────────────

    private void envoyerVersServeur() {
        if (listeLocale.isEmpty()) {
            afficherMessage("Aucun contact à synchroniser — chargez-les d'abord");
            return;
        }

        int total = listeLocale.size();
        AtomicInteger compteurSucces = new AtomicInteger(0);
        AtomicInteger compteurEchec  = new AtomicInteger(0);

        for (int i = 0; i < total; i++) {
            EntreeContact entree = listeLocale.get(i);
            serviceReseau.ajouterContact(entree).enqueue(new Callback<ReponseServeur>() {
                @Override
                public void onResponse(@NonNull Call<ReponseServeur> appel,
                                       @NonNull Response<ReponseServeur> reponse) {
                    if (reponse.isSuccessful() && reponse.body() != null && reponse.body().estSucces()) {
                        compteurSucces.incrementAndGet();
                    } else {
                        compteurEchec.incrementAndGet();
                    }
                    signalerFinSiTermine(compteurSucces, compteurEchec, total);
                }

                @Override
                public void onFailure(@NonNull Call<ReponseServeur> appel, @NonNull Throwable erreur) {
                    compteurEchec.incrementAndGet();
                    signalerFinSiTermine(compteurSucces, compteurEchec, total);
                }
            });
        }
        afficherMessage("Synchronisation de " + total + " contact(s) en cours…");
    }

    // Affiche le bilan uniquement quand toutes les requêtes sont terminées
    private void signalerFinSiTermine(AtomicInteger succes, AtomicInteger echecs, int total) {
        if (succes.get() + echecs.get() == total) {
            runOnUiThread(() -> afficherMessage(
                    succes.get() + " envoyé(s), " + echecs.get() + " échec(s) sur " + total
            ));
        }
    }

    // ─── Recherche distante ───────────────────────────────────────────────────

    private void rechercherContacts() {
        String motCle = champRecherche.getText().toString().trim();

        if (motCle.length() < 2) {
            afficherMessage("Saisissez au moins 2 caractères pour rechercher");
            return;
        }

        serviceReseau.rechercherContacts(motCle).enqueue(new Callback<List<EntreeContact>>() {
            @Override
            public void onResponse(@NonNull Call<List<EntreeContact>> appel,
                                   @NonNull Response<List<EntreeContact>> reponse) {
                if (reponse.isSuccessful() && reponse.body() != null) {
                    List<EntreeContact> resultats = reponse.body();
                    adaptateur.rafraichirDonnees(resultats);
                    afficherMessage(resultats.size() + " résultat(s) trouvé(s)");
                } else {
                    afficherMessage("Aucun résultat pour « " + motCle + " »");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<EntreeContact>> appel, @NonNull Throwable erreur) {
                afficherMessage("Erreur réseau : " + erreur.getMessage());
            }
        });
    }

    // ─── Utilitaire ──────────────────────────────────────────────────────────

    private void afficherMessage(String texte) {
        Toast.makeText(this, texte, Toast.LENGTH_SHORT).show();
    }
}
