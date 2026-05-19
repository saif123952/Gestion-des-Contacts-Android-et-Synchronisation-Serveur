package com.ennoukra.carnetcontacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptateur RecyclerView pour afficher la liste des entrées du carnet de contacts.
 */
public class AdaptateurContacts extends RecyclerView.Adapter<AdaptateurContacts.VueContact> {

    private List<EntreeContact> entrees;

    public AdaptateurContacts(List<EntreeContact> entrees) {
        this.entrees = new ArrayList<>(entrees);
    }

    @NonNull
    @Override
    public VueContact onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vue = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new VueContact(vue);
    }

    @Override
    public void onBindViewHolder(@NonNull VueContact holder, int position) {
        EntreeContact entree = entrees.get(position);
        holder.texteNom.setText(entree.getNom());
        holder.texteTelephone.setText(entree.getTelephone());
    }

    @Override
    public int getItemCount() {
        return entrees.size();
    }

    public void rafraichirDonnees(List<EntreeContact> nouvellesEntrees) {
        this.entrees = new ArrayList<>(nouvellesEntrees);
        notifyDataSetChanged();
    }

    static class VueContact extends RecyclerView.ViewHolder {
        TextView texteNom, texteTelephone;

        public VueContact(@NonNull View itemView) {
            super(itemView);
            texteNom       = itemView.findViewById(android.R.id.text1);
            texteTelephone = itemView.findViewById(android.R.id.text2);
        }
    }
}
