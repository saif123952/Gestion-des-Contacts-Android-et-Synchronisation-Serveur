<?php
/**
 * Représente une entrée dans le carnet de contacts.
 * Valide automatiquement les champs à la construction.
 */
class EntreeContact {
    public ?int    $id_entree     = null;
    public string  $nom;
    public string  $telephone;
    public string  $origine;
    public ?string $enregistre_le = null;

    public function __construct(string $nom, string $telephone, string $origine = "mobile") {
        $this->nom       = trim($nom);
        $this->telephone = trim($telephone);
        $this->origine   = $origine;
    }

    public function estValide(): bool {
        return strlen($this->nom) > 0 && strlen($this->telephone) >= 6;
    }
}
