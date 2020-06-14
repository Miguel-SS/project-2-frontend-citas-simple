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
    private AppointmentService appointmentService;
    //private Appointment appointment;
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
        /**
         * Notas de Jason:
         * Si les interesa una forma más eficiente de inicializar componentes de UI.
         * Les recomiendo que se busquen como se utiliza la librería llamada butterknife,
         * y como se implementa el BindView. Utiliza un concepto muy interesante llamado
         * inyección de dependencias para evitarles a uds en el código, la instanciación de componentes de UI
         * como se hace en este método.
         *
         * Así como está funciona perfecto, es solo si tienen curiosidad de aprender otra forma de hacerlo
         * */
        idAppointment = findViewById(R.id.codeAppointmentTxt);
        searchBtn = findViewById(R.id.acceptBtn);
        patientTxt = findViewById(R.id.patientTxt);
        doctorTxt = findViewById(R.id.doctorTxt);
        dateTxt = findViewById(R.id.dateTxt);
        typeTxt = findViewById(R.id.serviceTxt);
        costTxt = findViewById(R.id.costTxt);
        acceptBtn = findViewById(R.id.confirmBtn);
        searchBtn.setOnClickListener(v -> validateAppointment());
        acceptBtn.setOnClickListener(v -> upDateLbls());


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
                //Notifiquen con un toast o algun mensaje que la llamada al servicio falló, el objeto Throwable siempre tiene
                //más información del error que les va a seguir para arreglar errores en caso de fallo
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void printAppointmentInfo(final Appointment appointment){
        /**
         * Notas de Jason:
         Cuando se modifica cualquier componente del UI, y la acción es invocada desde un hilo secundario,
         como en este caso que están haciendo un setText que se invoca desde el onResponse de retrofit,
         deben asegurarse que la acción se realiza en el hilo principal del app. No estoy 100% seguro si Retrofit
         ya se asegura que los callbacks del enqueue se retornen en el hilo principal, pero por si les fallara, les
         dejo acá como pueden hacer para solucioar el problema.
         El método runOnUIThread obliga que el contenido del Runnable se ejecute de esa forma.
         */
        runOnUiThread(() -> {
            if (appointment != null) {
                patientTxt.setText(getString(R.string.nameLbl) + " " + appointment.getPatient().getName() + " " +
                        appointment.getPatient().getLastName());
                doctorTxt.setText( getString(R.string.doctorLbl) + " " + appointment.getDoctor().getName() + " " +
                        appointment.getDoctor().getLastName());
                dateTxt.setText(getString(R.string.dateLbl)  + " " +
                        appointment.getDate().getDay() + "/" +
                        appointment.getDate().getMonth() + "/" +
                        appointment.getDate().getYear());
                typeTxt.setText(getString(R.string.serviceLbl)  + " " +
                        getTypeOfService(appointment.getTypeOfService_id()));
                costTxt.setText(getString(R.string.costLbl)  + " " + appointment.getTotalCost());
            } else {
                Toast.makeText(getBaseContext(), "Cita no encontrada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     *
     * @param id id
     * @return type of service from data base.
     */
    private TypeOfService getTypeOfService(String id) {
        final TypeOfService[] type = {null};
        Call<TypeOfService> request = appointmentService.findTypeById(id);
        request.enqueue(new Callback<TypeOfService>() {
            @Override
            public void onResponse(Call<TypeOfService> call, Response<TypeOfService> response) {
                type[0] = getTypeInfo(response.body());
            }

            @Override
            public void onFailure(Call<TypeOfService> call, Throwable t) {
                Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return type[0];
    }


    private TypeOfService getTypeInfo(final TypeOfService typeOfService){
        final TypeOfService[] type = {null};
        runOnUiThread(() -> type[0] = typeOfService);
        return type[0];
    }


    private void upDateLbls(){
        patientTxt.setText(getString(R.string.nameLbl));
        doctorTxt.setText(getString(R.string.doctorLbl));
        dateTxt.setText(getString(R.string.dateLbl));
        typeTxt.setText(getString(R.string.serviceLbl));
        costTxt.setText(getString(R.string.costLbl));
    }

}