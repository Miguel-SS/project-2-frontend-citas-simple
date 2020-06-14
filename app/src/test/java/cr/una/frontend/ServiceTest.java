package cr.una.frontend;

import android.util.Log;
import cr.una.frontend.model.Appointment;
import cr.una.frontend.model.TypeOfService;
import cr.una.frontend.service.AppointmentService;
import cr.una.frontend.utilities.Constants;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceTest {

    private static final String TAG = ServiceTest.class.getSimpleName();

    private Retrofit retrofit;
    private AppointmentService service;

    @Before
    public void init() {
        retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.WS_ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        service = retrofit.create(AppointmentService.class);
    }

    @Test
    public void findAll() throws IOException {
        // Create a call instance for looking up Retrofit contributors.
        Call<List<Appointment>> call = service.findAll();

        // Fetch and print a list of the contributors to the library.
        List<Appointment> appointments = call.execute().body();
        assert appointments != null;
        assertTrue(0 < appointments.size());
    }

    @Test
    public void findById() throws IOException {
        int id = 15;
        Call<Appointment> call = service.findById(id);

        Appointment appointment = call.execute().body();
        assert appointment != null;
        assertEquals(id, appointment.getId());
    }

    @Test
    public void save() throws IOException {
        Call<Appointment> call = service.findById(15);
        Appointment appointment = call.execute().body();
        assert appointment != null;
        appointment.setHour("1:45");
        appointment.setActive(true);
        call = service.save(appointment);

        appointment = call.execute().body();
        assert appointment != null;
        assertTrue(appointment.isActive());
        assertEquals("1:45", appointment.getHour());
    }

    @Test
    public void update() throws IOException {
        Call<Appointment> call = service.findById(15);
        Appointment appointment = call.execute().body();
        assert appointment != null;
        appointment.setActive(true);
        Call<Appointment> updateCall = service.update(appointment.getId(), appointment);
        updateCall.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Fallo de llamado");
                    return;
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });


        Call<Appointment> call2 = service.findById(15);
        Appointment appointment2 = call2.execute().body();
        assert appointment2 != null;
        assertTrue(appointment2.isActive());
    }

    @Test
    public void delete() throws IOException {
        service.delete(13);

        Call<Appointment> call = service.findById(13);
        Appointment appointment = call.execute().body();
        assertNull(appointment);
    }

    @Test
    public void findAllTypes() throws IOException{
        Call<List<TypeOfService>> call = service.findAllTypes();

        List<TypeOfService> services = call.execute().body();
        assert services != null;
        assertTrue(0 < services.size());
    }

    @Test
    public void findTypesById() throws IOException {
        String id = "5ec579c4e7179a6b63630241";
        Call<TypeOfService> call = service.findTypeById(id);

        TypeOfService service = call.execute().body();
        assert service != null;
        assertEquals(id, service.getId().get$oid());
        assertEquals("Consulta de Médico General + Certificado médico en consultorio privado",
                service.getService());
    }
}
