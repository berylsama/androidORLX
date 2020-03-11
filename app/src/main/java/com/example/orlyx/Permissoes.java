package com.example.orlyx;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

class Permissoes {
    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode) {
     if(Build.VERSION.SDK_INT>=23){
         List<String>listaPermissoes = new ArrayList<>();

         for (String permissao:permissoes){

             Boolean temPermissoes = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
             if (!temPermissoes) listaPermissoes.add(permissao);
         }
            if (listaPermissoes.isEmpty())return true;
            String[] novasPermissoes =new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

         ActivityCompat.requestPermissions(activity,novasPermissoes,requestCode);
     }
     return true;
    }
}
