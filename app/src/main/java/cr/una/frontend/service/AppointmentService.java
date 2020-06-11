package cr.una.frontend.service;

import cr.una.frontend.model.Appointment;
import cr.una.frontend.model.TypeOfService;
import cr.una.frontend.utilities.Constants;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.io.IOException;
import java.util.List;

public interface AppointmentService {

    @GET("appointments")
    Call<List<Appointment>> findAll();

    @GET("appointments/{id}")
    Call<Appointment> findById(@Path("id") int id);

    @PUT("appointments/{id}")
    Call<ResponseBody> update(@Path("id") int id, @Body Appointment appointment);

    @GET("appointments/typeOfService")
    Call<List<TypeOfService>> findAllTypes();

    @GET("appointments/typeOfService/{id}")
    Call<TypeOfService> findTypeById(@Path("id") String id);

}
