package com.suramexico.gcd;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.suramexico.gcd.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView tvNombre;
    private TextView tvApellidoPaterno;
    private TextView tvApellidoMaterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        loadValues();
    }

    private void initView(){
        tvNombre = findViewById(R.id.tv_nombre);
        tvApellidoPaterno = findViewById(R.id.tv_apellido_paterno);
        tvApellidoMaterno = findViewById(R.id.tv_apellido_materno);
    }


    private void loadValues(){
        tvNombre.setText(getCustomExtras(Constants.NOMBRE));
        tvApellidoMaterno.setText(getCustomExtras(Constants.APELLIDO_MATERNO));
        tvApellidoPaterno.setText(getCustomExtras(Constants.APELLIDO_PATERNO));
    }



    public String getCustomExtras(String clave){
        Bundle bundle = getIntent().getExtras();

        if(getIntent() != null){
            if(!getIntent().getExtras().getString(clave).isEmpty()){
                return getIntent().getExtras().getString(clave);
            }else{
                return "No disponible";
            }
        }else{
            return "No disponible";
        }

    }



}
