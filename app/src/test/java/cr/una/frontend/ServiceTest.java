package cr.una.frontend;

import cr.una.frontend.model.Appointment;
import cr.una.frontend.service.AppointmentService;
import cr.una.frontend.utilities.Constants;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServiceTest {

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
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getId() + " (" + appointment.getPatient().getName() + ")");
        }
    }

    @Test
    public void findById() throws IOException {
        Call<Appointment> call = service.findById(1);

        Appointment appointment = call.execute().body();
        assert appointment != null;
        System.out.println(appointment.getId() + " (" + appointment.getPatient().getName() + ")");
    }

}
