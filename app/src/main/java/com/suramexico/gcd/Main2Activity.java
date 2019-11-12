package com.suramexico.gcd;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.suramexico.gcd.utils.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private TextView tvNombre;
    private TextView tvApellidoPaterno;
    private TextView tvApellidoMaterno;
    private ImageView ivAvatar;
    private ImageButton ibCamera;
    private ImageButton ibWeb;

    private final String[] PERMISSIONS = {
            Manifest.permission.CAMERA
    };
    private final int REQUEST_CAMERA =101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        loadValues();
        requestPermissions(Main2Activity.this);
    }

    private void initView(){
        tvNombre = findViewById(R.id.tv_nombre);
        tvApellidoPaterno = findViewById(R.id.tv_apellido_paterno);
        tvApellidoMaterno = findViewById(R.id.tv_apellido_materno);
        ivAvatar = findViewById(R.id.iv_foto);
        ibCamera = findViewById(R.id.ib_camara);
        ibWeb = findViewById(R.id.ib_web);
    }

    private void loadValues(){
        tvNombre.setText(getCustomExtras(Constants.NOMBRE));
        tvApellidoMaterno.setText(getCustomExtras(Constants.APELLIDO_MATERNO));
        tvApellidoPaterno.setText(getCustomExtras(Constants.APELLIDO_PATERNO));
        ibWeb.setOnClickListener(new SendWebListener());

    }

    private class SendWebListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            getIntentCustom(Main2Activity.this, "http://www.google.com");

        }
    }

    public void getIntentCustom(Context context, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }


    public String getCustomExtras(String clave){
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


    public void requestPermissions(Activity context){
        if(ActivityCompat.checkSelfPermission(context , Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(context, PERMISSIONS, REQUEST_CAMERA);
            Toast.makeText(Main2Activity.this, "Es necesario conseder el permiso",
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Main2Activity.this, "Permiso consedido", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA){
            requestPermissions(Main2Activity.this);

        }

    }
}
