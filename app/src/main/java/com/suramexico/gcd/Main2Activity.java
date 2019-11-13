package com.suramexico.gcd;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.suramexico.gcd.utils.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    private TextView tvNombre;
    private TextView tvApellidoPaterno;
    private TextView tvApellidoMaterno;
    private ImageView ivAvatar;
    private ImageButton ibCamera;
    private ImageButton ibWeb;

    private final String[] PERMISSIONS_MEDIA = {
            Manifest.permission.CAMERA
    };
    private final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private Uri photoURI;
    private final int REQUEST_CAMERA = 101;
    private final int REQUEST_STORAGE = 102;
    private final int REQUEST_TAKE_PHOTO = 103;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        loadValues();
       // requestPermissions(Main2Activity.this, PERMISSIONS_MEDIA, REQUEST_CAMERA);
        requestPermissions(Main2Activity.this, PERMISSIONS_STORAGE, REQUEST_STORAGE);
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
        ibCamera.setOnClickListener(new TomarFotoListener());

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
                return getResources().getString(R.string.mensaje_texto_no_disponible);
            }
        }else{
            return getResources().getString(R.string.mensaje_texto_no_disponible);
        }

    }


    public void requestPermissions(Activity context, String[] permissions, int requestCode){
        if(ActivityCompat.checkSelfPermission(context , permissions[0]) !=
                PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(context, permissions, requestCode);
        }else{
            Toast.makeText(Main2Activity.this,
                    getResources().getString(R.string.mensaje_permiso_concedido), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CAMERA){
            if((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(Main2Activity.this,
                        getResources().getString(R.string.mensaje_permiso_concedido), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Main2Activity.this,
                        getResources().getString(R.string.mensaje_permiso_necesario), Toast.LENGTH_LONG).show();
                requestPermissions(Main2Activity.this, PERMISSIONS_MEDIA, REQUEST_CAMERA);
            }

        }else if(requestCode == REQUEST_STORAGE){
            if((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(Main2Activity.this,
                        getResources().getString(R.string.mensaje_permiso_concedido), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(Main2Activity.this,
                        getResources().getString(R.string.mensaje_permiso_necesario), Toast.LENGTH_LONG).show();
                requestPermissions(Main2Activity.this, PERMISSIONS_STORAGE, REQUEST_STORAGE);
            }
        }

    }



    private class TomarFotoListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(ActivityCompat.checkSelfPermission(Main2Activity.this ,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ){
                requestPermissions(Main2Activity.this, PERMISSIONS_MEDIA, REQUEST_CAMERA);
            }else{
                TomarFoto();
            }
        }
    }


    private void TomarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            //File photoFile = getFilePhoto();
            //if (photoFile != null) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "Avatar");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Foto_" + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            //}
        }
    }


    private File getFilePhoto(){
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Toast.makeText(Main2Activity.this, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return photoFile;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }

    private Bitmap getBitmap(Uri photoURI){
        Bitmap bitmap = null;
        try {
        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            ivAvatar.setImageBitmap(getBitmap(photoURI));
        }
    }
}
