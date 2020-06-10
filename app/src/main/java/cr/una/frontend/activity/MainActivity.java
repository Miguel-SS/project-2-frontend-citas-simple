package cr.una.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cr.una.frontend.R;
import cr.una.frontend.service.AppointmentService;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private  AppointmentService appointmentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

}
