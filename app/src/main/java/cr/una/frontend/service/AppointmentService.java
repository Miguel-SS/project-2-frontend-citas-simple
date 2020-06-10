package cr.una.frontend.service;

import cr.una.frontend.model.Appointment;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AppointmentService {

    @GET("appointments")
    Call<List<Appointment>> findAll();

    @GET("appointments/{id}")
    Single<Appointment> findById(@Path("id") int id);

    @PUT("appointments/{id}")
    Call<ResponseBody> update(@Path("id") int id, @Body Appointment appointment);
}
