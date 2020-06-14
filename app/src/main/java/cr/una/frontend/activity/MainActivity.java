package cr.una.frontend.activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cr.una.frontend.R;
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

    private EditText idAppointment;
    private Button searchBtn;
    private TextView patientTxt;
    private TextView doctorTxt;
    private TextView dateTxt;
    private TextView typeTxt;
    private TextView costTxt;
    private Button acceptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init retrofit
        retrofit = RetroClient.getClient();
        appointmentService = retrofit.create(AppointmentService.class);
        initWidgets();
    }

    /**
     *
     */
    public void initWidgets() {
        idAppointment = findViewById(R.id.codeAppointmentTxt);
        searchBtn = findViewById(R.id.acceptBtn);
        patientTxt = findViewById(R.id.patientTxt);
        doctorTxt = findViewById(R.id.doctorTxt);
        dateTxt = findViewById(R.id.dateTxt);
        typeTxt = findViewById(R.id.serviceTxt);
        costTxt = findViewById(R.id.costTxt);
        acceptBtn = findViewById(R.id.confirmBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateAppointment();
        }
    });
        acceptBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateLbls();
        }
    });


}

    private void validateAppointment() {

        int id = Integer.parseInt(idAppointment.getText().toString());
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

                    patientTxt.setText(getString(R.string.nameLbl) + " " +
                            appointmentAux.getPatient().getName() + " " +
                            appointmentAux.getPatient().getLastName());
                    doctorTxt.setText( getString(R.string.doctorLbl) + " " +
                            appointmentAux.getDoctor().getName() + " " +
                            appointmentAux.getDoctor().getLastName());
                    dateTxt.setText(getString(R.string.dateLbl)  + " " +
                            appointmentAux.getDate().getDay() + "/" +
                            appointmentAux.getDate().getMonth() + "/" +
                            appointmentAux.getDate().getYear());
                    getTypeOfService(appointmentAux.getTypeOfService_id());
                    typeTxt.setText(getString(R.string.serviceLbl)  + " " + typeService);
                    costTxt.setText(getString(R.string.costLbl)  + " " + appointmentAux.getTotalCost());
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
        patientTxt.setText(getString(R.string.nameLbl));
        doctorTxt.setText(getString(R.string.doctorLbl));
        dateTxt.setText(getString(R.string.dateLbl));
        typeTxt.setText(getString(R.string.serviceLbl));
        costTxt.setText(getString(R.string.costLbl));

        Call<Appointment> request = appointmentService.update(appointment.getId(), appointment);

        appointment.setActive(true);

        request.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful()) {
                    //updateAppointment(appointment);
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