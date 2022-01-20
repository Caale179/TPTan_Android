package fr.nantes.iut.tptan.data.repository.tan;

import android.content.Context;
import android.util.Log;

import fr.nantes.iut.tptan.BuildConfig;
import fr.nantes.iut.tptan.R;
import fr.nantes.iut.tptan.data.entity.ListArret;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenDataTanRepo {

    public ListArret getListArretProche(double latitude, double longitude, Context context ) throws Exception {

        Retrofit retrofit = createRetrofit(context);

        OpenDataTanRetrofitService service = retrofit.create(OpenDataTanRetrofitService.class);

        Log.d(BuildConfig.LOG_TAG, "query service with latitude = "
                + latitude + " and longitude = " + longitude);

        Call<ListArret> listArretCall = service.getProximityStops(latitude, longitude);
        Response<ListArret> listArretResponse = listArretCall.execute();

        return listArretResponse.body();
    }


    protected Retrofit createRetrofit( Context context ) {
        return new Retrofit.Builder()
                .baseUrl(context.getString(R.string.tan_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
