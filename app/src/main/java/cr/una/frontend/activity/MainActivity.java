package cr.una.frontend.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cr.una.frontend.R;
import cr.una.frontend.model.Appointment;
import cr.una.frontend.service.AppointmentService;
import cr.una.frontend.utilities.Constants;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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
    private TextView acceptBtn;



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

        idAppointment = (EditText) findViewById(R.id.codeAppointmentTxt);
        searchBtn = (Button) findViewById(R.id.acceptBtn);
        patientTxt = (EditText) findViewById(R.id.patientTxt);
        doctorTxt = (EditText) findViewById(R.id.doctorTxt);
        costTxt = (EditText) findViewById(R.id.costTxt);
        acceptBtn = (Button) findViewById(R.id.confirmBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAppointment();
            }
        });


        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }



    private void validateAppointment(){
        int id = Integer.parseInt(searchBtn.getText().toString());
        Single<Appointment> appointmentSingle = appointmentService.findById(id);
    }


}


