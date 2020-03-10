package com.example.orlyx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao = ConfigFirebase.getReferenciaAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        autenticacao = ConfigFirebase.getReferenciaAutenticacao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
        //metodo para chamar o menu toda vez que entrar no app...
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        if (autenticacao.getCurrentUser() ==null){ //user deslogado
            menu.setGroupVisible(R.id.group_deslogado,true);
        }
        else{
            menu.setGroupVisible(R.id.group_logado,true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_cadastrar:startActivity(new Intent(getApplicationContext(),MainActivity.class));
            break;

            case R.id.menu_sair:autenticacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.meus_anuncios:startActivity(new Intent(getApplicationContext(),MeusAnunciosActivity.class));
            break;
        }
        return super.onOptionsItemSelected(item);
    } //owo;

}
