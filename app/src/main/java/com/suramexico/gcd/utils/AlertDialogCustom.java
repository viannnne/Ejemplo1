package com.suramexico.gcd.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class AlertDialogCustom {

    public static AlertDialogCustom newInstance(){
        AlertDialogCustom alertDialogCustom = new AlertDialogCustom();

        return alertDialogCustom;
    }


    public AlertDialog getGenericAlertDialogSuccess(final Activity activity, String titulo,
                                                    String mensaje, String etiquetaBoton){
        final AlertDialog.Builder  alertDialog  =  new AlertDialog.Builder(activity);
        alertDialog.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton(etiquetaBoton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity, " Aceptar ", Toast.LENGTH_LONG).show();
                    }
                });

        return alertDialog.create();
    }


}
