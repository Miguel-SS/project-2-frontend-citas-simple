package cr.una.frontend;

import cr.una.frontend.model.Appointment;
import cr.una.frontend.service.AppointmentService;
import cr.una.frontend.utilities.Constants;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class maintest {

    @Test
    public void serviceTest() throws IOException {
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(Constants.WS_ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        // Create an instance of our GitHub API interface.
        AppointmentService service = retrofit.create(AppointmentService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Appointment>> call = service.findAll();

        // Fetch and print a list of the contributors to the library.
        List<Appointment> appointments = call.execute().body();
        for (Appointment appointment : appointments) {
            System.out.println(appointment.getId() + " (" + appointment.getPatient().getName() + ")");
        }
    }
}
