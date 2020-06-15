package cr.una.frontend.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cr.una.frontend.utilities.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.WS_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
