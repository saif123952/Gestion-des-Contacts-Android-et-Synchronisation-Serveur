package com.ennoukra.carnetcontacts;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Fournit une instance Retrofit partagée (singleton) pour toute l'application.
 * Modifier URL_BASE pour pointer vers l'IP de votre serveur sur le réseau local.
 */
public class ClientHttp {

    // ⚠️ Remplacer par l'adresse IP de votre machine sur le réseau local
    private static final String URL_BASE = "http://192.168.1.10/carnet-contacts-api/api/";

    private static Retrofit instance;

    private ClientHttp() {}

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
