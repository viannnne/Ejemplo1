package com.suramexico.gcd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.suramexico.gcd.utils.Constants;
import com.suramexico.gcd.utils.Utilidades;

/**
 * ....
 */
public class MainActivity extends AppCompatActivity {
    private EditText etNombre;
    private EditText etApellidoMaterno;
    private EditText etApellidoPaterno;
    private Button btAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        etNombre = findViewById(R.id.et_nombre);
        etApellidoPaterno = findViewById(R.id.et_apellido_pat);
        etApellidoMaterno = findViewById(R.id.et_apellido_mat);
        btAceptar = findViewById(R.id.bt_aceptar);
        btAceptar.setOnClickListener(new AceptarListener());

    }

    private class AceptarListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            startActivity(getCustomIntent(MainActivity.this, Main2Activity.class));
        }
    }

    private Intent getCustomIntent(Activity activity, Class destino){
        Intent intent = new Intent(activity, destino );
        intent.putExtra(Constants.NOMBRE, Utilidades.newInstance().getStringFromET(etNombre));
        intent.putExtra(Constants.APELLIDO_PATERNO, Utilidades.newInstance().getStringFromET(etApellidoPaterno));
        intent.putExtra(Constants.APELLIDO_MATERNO, Utilidades.newInstance().getStringFromET(etApellidoMaterno));
        return intent;
    }




}
