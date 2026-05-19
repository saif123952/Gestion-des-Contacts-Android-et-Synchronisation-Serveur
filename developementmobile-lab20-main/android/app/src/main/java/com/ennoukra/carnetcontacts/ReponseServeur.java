package com.ennoukra.carnetcontacts;

import com.google.gson.annotations.SerializedName;

/**
 * Modèle de la réponse JSON retournée par le serveur après une opération d'écriture.
 */
public class ReponseServeur {

    @SerializedName("succes")
    private boolean succes;

    @SerializedName("message")
    private String message;

    public boolean estSucces()   { return succes; }
    public String  getMessage()  { return message; }
}
