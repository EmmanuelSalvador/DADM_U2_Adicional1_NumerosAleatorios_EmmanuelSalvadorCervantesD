package mx.edu.ittepic.dadm_u2_adicional1_numerosaleatorios_emmanuelsalvadorcervantesd;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LinearLayout layito;
    EditText[] camposGenerados;
    int [] numerosGenerados;
    CountDownTimer timercito;
    String allData="";
    int i=0;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layito=findViewById(R.id.layo);

        final EditText cantidad = new EditText (this);
        cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Número de elementos").setMessage("Escribe la cantidad de campos que deseas generar")
                .setView(cantidad).setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog, int which){
                int intCantidad = Integer.parseInt(cantidad.getText().toString());
                if (intCantidad<=0 || intCantidad>=2000000){
                    Toast.makeText(MainActivity.this,"Debes ingresar un número entre 0 y 2,000,000",Toast.LENGTH_LONG).show();
                }
                else {
                    irTimer(intCantidad);
                    timercito.start();
                }
            }
        }).show();
    }
    private void irTimer (final int intCantidad){
        final int lapso = intCantidad*300;
        timercito = new CountDownTimer (lapso, 300){
            @Override
            public void onTick (long milliUntilFinished){
                crearElementos(intCantidad);
                i++;
            }
            @Override
            public void onFinish(){
                // Comentar la siguiente linea si se ejecuta en el emulador, descomentar si se ejecuta en un teléfono
                // crearElementos(intCantidad);
                guardarEnSD();
            }
        };
    }
    private boolean validarSD() {
        String resp = Environment.getExternalStorageState();
        if (resp.compareTo(Environment.MEDIA_MOUNTED)==0){
            return true;
        }
        return false;
    }

    private void guardarEnSD() {
        try{
            if (!validarSD()){
                Toast.makeText(this,"No tienes memoria SD insertada, o está en modo solo lectura",Toast.LENGTH_SHORT).show();
                return;
            }
            File rutaSD = Environment.getExternalStorageDirectory();
            File datosArchivo = new File(rutaSD.getAbsolutePath(),"archivosd.csv");
            OutputStreamWriter archivo = new OutputStreamWriter(new FileOutputStream(datosArchivo));
            archivo.write(allData);
            archivo.close();
            Toast.makeText(this,"Éxito, se guardó el dato",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void crearElementos(int intCantidad) {
        camposGenerados= new EditText [intCantidad];
        numerosGenerados=new int [intCantidad];
        camposGenerados[i]= new EditText (MainActivity.this);
        Random rand = new Random();
        int randNum = rand.nextInt(100) + 1;
        camposGenerados[i].setText(""+randNum);
        numerosGenerados[i]=randNum;
        int posicion= i+1;
        int chekeador = posicion % 50;
        if (posicion==intCantidad || chekeador==0){
            allData = allData + numerosGenerados[i];
        }
        else {
            allData = allData + numerosGenerados[i] + ",";
        }
        if (chekeador==0){
            allData=allData+"\n";
        }
        layito.addView(camposGenerados[i]);
    }
}