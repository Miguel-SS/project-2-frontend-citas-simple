package cr.una.frontend.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cr.una.frontend.R;
import cr.una.frontend.model.Appointment;
import cr.una.frontend.service.AppointmentService;
import cr.una.frontend.utilities.Constants;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private AppointmentService appointmentService;
    private Appointment appointment;
    private EditText idAppointment;
    private Button searchBttn;
    private TextView patientTxt;
    private TextView doctorTxt;
    private TextView dateTxt;
    private TextView typeTxt;
    private TextView costTxt;
    private TextView aceptBttn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init the retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WS_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        appointmentService = retrofit.create(AppointmentService.class);

        initWidgets();
    }



    @SuppressLint("WrongViewCast")
    public void initWidgets(){

        appointment = null;
        idAppointment = (EditText) findViewById(R.id.codeAppointmentTxt);
        searchBttn = (Button) findViewById(R.id.acceptBtn);
        patientTxt = (EditText) findViewById(R.id.patientTxt);
        doctorTxt = (EditText) findViewById(R.id.doctorTxt);
        costTxt = (EditText) findViewById(R.id.costTxt);
        aceptBttn = (Button) findViewById(R.id.confirmBtn);

        searchBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerAppointment();
            }
        });


        aceptBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }



    private void validerAppointment(){
        int id = Integer.parseInt(searchBttn.getText().toString());
        Single<Appointment> appointmentSingle = appointmentService.findById(id);
        appointment = appointmentSingle.blockingGet();
        if (appointment!=null){
            patientTxt.setText(appointment.getPatient().getName()+" "+ appointment.getPatient().getLastName());
            
        }
        else{
            Toast.makeText(getBaseContext(), "Cita no encontrada", Toast.LENGTH_SHORT).show();
        }
    }


}


