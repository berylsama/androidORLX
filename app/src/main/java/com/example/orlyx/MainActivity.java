package com.example.orlyx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //verificar se o Database estará conectado ao android
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usuariosReferencia = firebaseDatabase.child("usuarios");
        DatabaseReference produtosReferencia = firebaseDatabase.child("produtos");

        //1o teste ----- firebasedatabase deverá ter uma classe de sua tabela

        Usuarios usuario = new Usuarios();
        usuario.setNome("Amaria");
        usuario.setSobrenome("Barros");
        usuario.setIdade(17);
        usuario.setCidade("Santos");
        usuario.setBairro("Aparecida");
        usuario.setTelefone("11 - 9 8989-7865");

        usuariosReferencia.child("120").setValue(usuario);


        Produtos produto = new Produtos();
        produto.setNomeProduto("Bolo de Morango");
        produto.setDescricao("Bolo recheado de Morango");
        produto.setValor(4.80);
        produto.setQuantidade(10);
        produto.setCategoria("Bolos");

        produtosReferencia.child("420").setValue(produto);


    }
}
