package cr.una.frontend.activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cr.una.frontend.R;
import cr.una.frontend.databinding.ActivityMainBinding;
import cr.una.frontend.model.Appointment;
import cr.una.frontend.model.TypeOfService;
import cr.una.frontend.service.AppointmentService;
import cr.una.frontend.service.RetroClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private String typeService;
    private AppointmentService appointmentService;
    private Appointment appointment;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //init retrofit
        retrofit = RetroClient.getClient();
        appointmentService = retrofit.create(AppointmentService.class);
        initWidgets();
    }

    /**
     *
     */
    public void initWidgets() {
        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateAppointment();
        }
    });
        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateLbls();
        }
    });


}

    private void validateAppointment() {

        int id = Integer.parseInt(binding.codeAppointmentTxt.getText().toString());
        Call<Appointment> request = appointmentService.findById(id);
        request.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                printAppointmentInfo(response.body());
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void printAppointmentInfo(final Appointment appointmentAux){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (appointmentAux != null) {
                    appointment = appointmentAux;

                    binding.patientTxt.setText(getString(R.string.nameLbl) + " " +
                            appointmentAux.getPatient().getName() + " " +
                            appointmentAux.getPatient().getLastName());
                    binding.doctorTxt.setText( getString(R.string.doctorLbl) + " " +
                            appointmentAux.getDoctor().getName() + " " +
                            appointmentAux.getDoctor().getLastName());
                    binding.dateTxt.setText(getString(R.string.dateLbl)  + " " +
                            appointmentAux.getDate().getDay() + "/" +
                            appointmentAux.getDate().getMonth() + "/" +
                            appointmentAux.getDate().getYear());
                    getTypeOfService(appointmentAux.getTypeOfService_id());
                    binding.serviceTxt.setText(getString(R.string.serviceLbl)  + " " + typeService);
                    binding.costTxt.setText(getString(R.string.costLbl)  + " " + appointmentAux.getTotalCost());
                } else {
                    Toast.makeText(getBaseContext(), "Cita no encontrada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     * @param id id
     */
    private void getTypeOfService(String id) {
        Call<TypeOfService> request = appointmentService.findTypeById(id);
        request.enqueue(new Callback<TypeOfService>() {
            @Override
            public void onResponse(Call<TypeOfService> call, Response<TypeOfService> response) {
                 getTypeInfo(response.body());
            }

            @Override
            public void onFailure(Call<TypeOfService> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getTypeInfo(final TypeOfService typeOfService){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
             typeService = typeOfService.getService();
            }
        });
    }


    private void updateLbls(){
        binding.patientTxt.setText(getString(R.string.nameLbl));
        binding.doctorTxt.setText(getString(R.string.doctorLbl));
        binding.dateTxt.setText(getString(R.string.dateLbl));
        binding.serviceTxt.setText(getString(R.string.serviceLbl));
        binding.costTxt.setText(getString(R.string.costLbl));

        Call<Appointment> request = appointmentService.update(appointment);

        appointment.setActive(true);

        request.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Cita confirmada", Toast.LENGTH_SHORT);
                } else Toast.makeText(getBaseContext(), "Respuesta fallida", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAppointment(final Appointment appointmentAux){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                appointmentAux.setActive(true);
            }
        });
    }

}