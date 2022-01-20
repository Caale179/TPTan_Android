package fr.nantes.iut.tptan.data.repository.tan;

import fr.nantes.iut.tptan.data.entity.ListArret;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenDataTanRetrofitService {

    @GET("/ewp/arrets.json?output=json")
    Call<ListArret> getProximityStops(@Query("latitude") double latitude, @Query("longitude") double longitude);
}

