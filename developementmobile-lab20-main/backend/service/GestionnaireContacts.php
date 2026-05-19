<?php
require_once '../config/ConnexionBD.php';
require_once '../modele/EntreeContact.php';

/**
 * Gère toutes les opérations CRUD sur la table entree_contact.
 */
class GestionnaireContacts {
    private PDO $connexion;

    public function __construct() {
        $bd = new ConnexionBD();
        $this->connexion = $bd->etablirConnexion();
    }

    /**
     * Insère un nouveau contact après validation des champs.
     */
    public function ajouterContact(string $nom, string $telephone, string $origine = "mobile"): array {
        $entree = new EntreeContact($nom, $telephone, $origine);

        if (!$entree->estValide()) {
            return ["succes" => false, "message" => "Nom vide ou numéro trop court (min. 6 caractères)"];
        }

        $requete = $this->connexion->prepare(
            "INSERT INTO entree_contact (nom, telephone, origine) VALUES (:nom, :telephone, :origine)"
        );
        $requete->bindValue(':nom',       $entree->nom);
        $requete->bindValue(':telephone', $entree->telephone);
        $requete->bindValue(':origine',   $entree->origine);

        if ($requete->execute()) {
            return ["succes" => true, "message" => "Contact enregistré avec succès"];
        }
        return ["succes" => false, "message" => "Échec de l'enregistrement en base"];
    }

    /**
     * Retourne tous les contacts triés par nom (ordre alphabétique).
     */
    public function obtenirTousLesContacts(): array {
        $requete = $this->connexion->prepare(
            "SELECT * FROM entree_contact ORDER BY nom ASC"
        );
        $requete->execute();
        return $requete->fetchAll();
    }

    /**
     * Recherche des contacts dont le nom ou le téléphone contient le mot-clé.
     */
    public function rechercherParMotCle(string $motCle): array {
        $motCle = trim($motCle);
        $filtre = "%" . $motCle . "%";

        $requete = $this->connexion->prepare(
            "SELECT * FROM entree_contact WHERE nom LIKE :filtre OR telephone LIKE :filtre ORDER BY nom ASC"
        );
        $requete->bindValue(':filtre', $filtre);
        $requete->execute();
        return $requete->fetchAll();
    }
}
