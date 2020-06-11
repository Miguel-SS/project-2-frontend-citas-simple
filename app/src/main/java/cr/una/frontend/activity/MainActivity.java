package cr.una.frontend.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    private Retrofit retrofit;
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
        appointment = null;
        //init the retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.WS_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        appointmentService = retrofit.create(AppointmentService.class);

        initWidgets();
    }

    public void initWidgets(){

        idAppointment = findViewById(R.id.codeAppointmentTxt);
        searchBtn = findViewById(R.id.acceptBtn);
        patientTxt = findViewById(R.id.patientTxt);
        doctorTxt = findViewById(R.id.doctorTxt);
        costTxt = findViewById(R.id.costTxt);
        acceptBtn = findViewById(R.id.confirmBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    validateAppointment();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void validateAppointment() throws IOException {
        int id = Integer.parseInt(idAppointment.getText().toString());
        Call<Appointment> appointmentSingle = appointmentService.findById(id);
        appointment = appointmentSingle.execute().body();
        if (appointment!=null){

            patientTxt.setText(patientTxt.getText().toString()+" "+appointment.getPatient().getName()+" "+
                    appointment.getPatient().getLastName());
            doctorTxt.setText(doctorTxt.getText().toString()+" "+appointment.getDoctor().getName()+" "+
                    appointment.getDoctor().getLastName());


        }
        else{
            Toast.makeText(getBaseContext(), "Cita no encontrada", Toast.LENGTH_SHORT).show();
        }


    }


}


