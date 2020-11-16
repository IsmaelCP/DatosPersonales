package es.studium.datospersonales;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    // Views
    Button btnIdioma;
    Locale pais;
    Configuration config = new Configuration();
    EditText txtNombre;
    EditText txtApellidos;
    EditText txtEdad;
    RadioGroup rbGenero;
    RadioButton rbHombre;
    RadioButton rbMujer;
    Spinner spn_opciones;
    Switch swtHijos;
    Button btnInfo;
    Button btnLimpiar;

    // Variables para recoger la info del usuario
    String datoNombre = "";
    String datoApellidos = "";
    String datoEdad = "";
    String generoElegido = "";
    String estadoCivil = "";
    String numHijos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Poner el icono en el action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        // Inicializar los objetos
        btnIdioma = (Button) findViewById(R.id.btn_idioma);
        txtNombre = (EditText) findViewById(R.id.txt_nombre);
        txtApellidos = (EditText) findViewById(R.id.txt_apellidos);
        txtEdad = (EditText) findViewById(R.id.txt_edad);
        rbGenero = (RadioGroup) findViewById(R.id.rg_genero);
        rbHombre = (RadioButton) findViewById(R.id.rb_Hombre);
        rbMujer = (RadioButton) findViewById(R.id.rb_Mujer);
        spn_opciones = (Spinner) findViewById(R.id.spn_estadoCivil);
        swtHijos = (Switch) findViewById(R.id.switch_hijos);
        btnInfo = (Button) findViewById(R.id.btn_info);
        btnLimpiar = (Button) findViewById(R.id.btn_limpiar);

        // Añadir el Listener al botón Información
        btnInfo.setOnClickListener(this);
        // Añadir el Listener al botón Limpiar
        btnLimpiar.setOnClickListener(this);
        // Añadir el Listener al botón Idioma
        btnIdioma.setOnClickListener(this);

        // Adpatador Spinner
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.spn_opciones, android.R.layout.simple_spinner_item);
        // Asignar las opciones del Spinner a nuestro elemento
        spn_opciones.setAdapter(adaptador);
    }

    @Override
    public void onClick(View v)
    {
        // Acción botón Información
        if(v.getId()==(R.id.btn_info))
        {
             // Guardar datos txtNombre
            datoNombre = txtNombre.getText().toString();

            // Guardar datos txtApellidos
            datoApellidos = txtApellidos.getText().toString();

            // Guardar datos txtEdad
            try
            {
                int numEdad = Integer.parseInt(txtEdad.getText().toString());
                if(numEdad>=18)
                {
                    datoEdad = "mayor de edad";
                }
                else
                {
                    datoEdad = "menor de edad";
                }
            } catch (NumberFormatException e)
            {
               // e.printStackTrace();
            }

            // Guardar datos Género
            if (rbHombre.isChecked()) {
                generoElegido = "Hombre";
            } else {
                generoElegido = "Mujer";
            }

            // Guardar datos Estado Civil
            estadoCivil = spn_opciones.getSelectedItem().toString();

            // Guardar datos Hijos
            if (swtHijos.isChecked()) {
                numHijos = "con hijos";
            } else {
                numHijos = "sin hijos";
            }

            // Detectar campos no rellenados y mostrar mensaje
            if (datoNombre.isEmpty())
            {
                Toast toast = Toast.makeText(this, R.string.toast_Nombre, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP| Gravity.LEFT, 0, 400);
                toast.getView().setBackgroundColor(Color.GREEN);
                TextView text = (TextView) toast.getView().findViewById(android.R.id.message);
                text.setTextColor(Color.RED);
                toast.show();
            }
            if (datoApellidos.isEmpty())
            {
                Toast toast = Toast.makeText(this, R.string.toast_Apellidos, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP| Gravity.RIGHT, 0, 400);
                toast.getView().setBackgroundColor(Color.GREEN);
                TextView text = (TextView) toast.getView().findViewById(android.R.id.message);
                text.setTextColor(Color.RED);
                toast.show();
            }
            if (datoEdad.isEmpty())
            {
                Toast toast = Toast.makeText(this, R.string.toast_Edad, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP| Gravity.LEFT, 0, 670);
                toast.getView().setBackgroundColor(Color.GREEN);
                TextView text = (TextView) toast.getView().findViewById(android.R.id.message);
                text.setTextColor(Color.RED);
                toast.show();
            }
            else
            {
                if (datoNombre.isEmpty()||datoApellidos.isEmpty()||datoEdad.isEmpty())
                {
                    Toast.makeText(this, datoApellidos + ", " + datoNombre + ", " + datoEdad + ", " + generoElegido + ", " + estadoCivil + " y " + numHijos + ".", Toast.LENGTH_LONG);
                }
                else
                {
                    Toast.makeText(this, datoApellidos + ", " + datoNombre + ", " + datoEdad + ", " + generoElegido + ", " + estadoCivil + " y " + numHijos + ".", Toast.LENGTH_LONG).show();
                }
            }
        }

        // Acción botón Limpiar
        else if(v.getId()==(R.id.btn_limpiar))
        {
            Intent refrescarApp = new Intent(MainActivity.this, MainActivity.class);
            startActivity(refrescarApp);
        }

        // Acción botón Idioma
        else if(v.getId()==(R.id.btn_idioma))
        {
            mostrarDialogoIdioma();
        }
    }

    private void mostrarDialogoIdioma()
    {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.btn_idioma));
        //obtiene los idiomas del array de string.xml
        String[] types = getResources().getStringArray(R.array.idioma);
        b.setItems(types, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch(which){
                    case 0:
                        pais = new Locale("es");
                        config.locale = pais;
                        break;
                    case 1:
                        pais = new Locale("en");
                        config.locale = pais;
                        break;
                    case 2:
                        pais = new Locale("pt");
                        config.locale = pais;
                        break;
                }
                getResources().updateConfiguration(config, null);
                Intent refrescarApp = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refrescarApp);
                finish();
            }
        });
        b.show();
    }
}

