package com.ennoukra.carnetcontacts;

import com.google.gson.annotations.SerializedName;

/**
 * Représente une entrée dans le carnet de contacts.
 * Les annotations @SerializedName alignent les champs Java avec les colonnes JSON du serveur.
 */
public class EntreeContact {

    @SerializedName("id_entree")
    private int idEntree;

    @SerializedName("nom")
    private String nom;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("origine")
    private String origine;

    @SerializedName("enregistre_le")
    private String enregistreLe;

    public EntreeContact() {}

    public EntreeContact(String nom, String telephone) {
        this.nom       = nom;
        this.telephone = telephone;
        this.origine   = "mobile";
    }

    public int    getIdEntree()    { return idEntree; }
    public String getNom()         { return nom; }
    public String getTelephone()   { return telephone; }
    public String getOrigine()     { return origine; }
    public String getEnregistreLe(){ return enregistreLe; }

    public void setIdEntree(int idEntree)         { this.idEntree = idEntree; }
    public void setNom(String nom)                { this.nom = nom; }
    public void setTelephone(String telephone)    { this.telephone = telephone; }
    public void setOrigine(String origine)        { this.origine = origine; }
    public void setEnregistreLe(String val)       { this.enregistreLe = val; }
}
