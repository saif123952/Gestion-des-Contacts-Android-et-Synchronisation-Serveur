package com.ennoukra.carnetcontacts;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Interface Retrofit — déclare les points d'entrée de l'API distante.
 */
public interface ServiceReseau {

    @POST("ajouterEntree.php")
    Call<ReponseServeur> ajouterContact(@Body EntreeContact entree);

    @GET("listerEntrees.php")
    Call<List<EntreeContact>> listerTousLesContacts();

    @GET("rechercherEntree.php")
    Call<List<EntreeContact>> rechercherContacts(@Query("q") String motCle);
}
