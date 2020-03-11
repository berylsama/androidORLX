package com.example.orlyx;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

class Permissoes {
    public static boolean validarPermissoes(String[] permissoes, CadastrarAnunciosActivity cadastrarAnunciosActivity, int requestCode) {
     if(Build.VERSION.SDK_INT>=23){
         List<String>listaPermissoes = new ArrayList<>();

         for (String permissao:permissoes){

             Boolean temPermissoes = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
             if (!temPermissoes) listaPermissoes.add(permissao);
         }
     }
    }
}
