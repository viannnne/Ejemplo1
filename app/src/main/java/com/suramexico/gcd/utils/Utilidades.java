package com.suramexico.gcd.utils;

import android.widget.EditText;

public class Utilidades {

    public static Utilidades newInstance(){
        return new Utilidades();
    }

    public String getStringFromET(EditText editText){
        if(editText!= null && editText.getText().toString().isEmpty()){
            return editText.getText().toString();
        }else{
            return "Not available";
        }
    }
}
