package cr.una.frontend.service;

import cr.una.frontend.model.Appointment;
import cr.una.frontend.model.TypeOfService;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AppointmentService {

    @GET("appointments")
    Call<List<Appointment>> findAll();

    @GET("appointments/{id}/")
    Call<Appointment> findById(@Path("id") int id);

    @POST("appointments")
    Call<Appointment> save(@Body Appointment appointment);

    @PUT("appointments/{id}")
    Call<Appointment> update(@Path("id") int id, @Body Appointment appointment);

    @DELETE("appointments/{id}/")
    Call<Void> delete(@Path("id") int id);

    @GET("appointments/typesOfService")
    Call<List<TypeOfService>> findAllTypes();

    @GET("appointments/typesOfService/{id}")
    Call<TypeOfService> findTypeById(@Path("id") String id);

}
