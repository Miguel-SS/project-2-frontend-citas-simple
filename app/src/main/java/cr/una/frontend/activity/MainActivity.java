package cr.una.frontend.activity;
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
import cr.una.frontend.service.Service;
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
        retrofit = Service.getRetrofit();
        appointmentService = retrofit.create(AppointmentService.class);
        initWidgets();
    }
    public void initWidgets(){
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
            }
        });
    }
    private void validateAppointment() {
        /**
         * Notas de Jason:
         * La razón por la cual la implementación para obtener la lista de citas no estaba sirviendo,
         * es porque no se permite que acciones pesadas como acceder a bases de datos, llamar a
         * Web Services, etc, se realicen en el hilo de ejecución principal de la aplicación.
         * Esto porque de ser así el UI y el App quedarían estancados mientras se espera por la respuesta
         * del web service, lo cual es muy mala experiencia para el usuario.
         * Para garantizar que el App siempre siga operacional, es necesario utilizar un
         * hilo secundario.
         *
         * Si desean utilizar su approach actual con el execute() que
         * retorna directamente la lista de citas, deben utilizar alguno de los siguientes:
         *
         * 1. Un Handler: por defecto ellos crean internamente un segundo hilo de ejecución.
         * 2. Un AsyncTask: es exclusivo de Android, y es una implementación específica para ejecutar acciones en hilos
         * secundarios, que tiene más cosas como métodos que reportan progreso, notificación de cuando la acción que
         * se está haciendo en el background ya terminó, entre otras funcionalidades.
         * 3. Un Thread de Java directo: esta es la implementación más básica para ejecutar procesos en segundo plano.
         *
         * Esto es solo si quieren mandarse valientes y hacer la ejecución del hilo por su cuenta.
         * La buena noticia es que no es necesario, ya que Retrofit les ofrece una opción más simple para esto, y es utilizar una cola de requests.
         *
         * En lugar de llamar al execute directamente, pueden instanciar un objeto de Tipo Call y meterlo a una cola.
         * Retrofit automaticamente hará el request si es el único call existente, o bien, si tuvieran varias llamadas a la
         * vez, Retrofit se encarga de administrarle cual call se ejecuta primero, y lo más importante, se encarga
         * de hacer la ejecución en un segundo hilo por su propia cuenta.
         *
         * En este ejemplo abajo les modifiqué un poco el código para que vean como lo deben hacer:
         *
         * */
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (appointment!=null) {
                    /**
                     * Notas de Jason:
                     -  Me imagino que usan el patientTxt.getText().toString() al inicio del texto para mostrar el label de "Nombre: ..." o "Fecha: ..."
                     El problema con su approach actual es que después de la primera consulta exitosa se les va a concatenar el getText anterior que contenía la info del
                     appointment consultado primero, con la nueva búsqueda, y así sucesivamente con las consultas subsequentes, el texto va a seguir creciendo y creciendo.
                     - Les recomiendo que utilicen en lugar de eso, un string que contenga el inicio del label que quieren mostrar unicamente, y lo guarden en strings.xml.
                     Luego lo obtienen con el método getString(StringID) para imprimirlo en la pantalla.
                     - Tambien, pueden buscar como se utiliza el String.format(), es más seguro a la hora de formar textos.
                     */
                    patientTxt.setText(patientTxt.getText().toString()+" "+appointment.getPatient().getName()+" "+
                            appointment.getPatient().getLastName());
                    doctorTxt.setText(doctorTxt.getText().toString()+" "+appointment.getDoctor().getName()+" "+
                            appointment.getDoctor().getLastName());
                }
                else{
                    Toast.makeText(getBaseContext(), "Cita no encontrada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}