package com.example.orlyx;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.storage.StorageReference;
import com.santalu.maskedittext.MaskEditText;

import java.util.ArrayList;
import java.util.List;

public class CadastrarAnunciosActivity extends AppCompatActivity

    implements View.OnClickListener{

        private EditText campoTitulo, campoDesc;
        private CurrencyEditText campoValor;
        private MaskEditText campoContato;
        private ImageView imagem1, imagem2, imagem3;
        private Spinner campoEstado, campoCategoria;
        private Anuncio anuncio;
        private StorageReference storage;
        
        private String [] permissoes = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        
        private List<String> listaFotosRecuperadas = new ArrayList<>();
        private List<String> listaURLFotos = new ArrayList<>();
        
        

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncios);
        
        storage = ConfigFirebase.getReferenciaStorage();
        
        Permissoes.validarPermissoes(permissoes, this, 1);
        inicializarComponentes();
        carregarDadosSpinner();
    }
    
    public void salvarAnuncio(View v){
        
        for (int i = 0; i <listaFotosRecuperadas.size(); i++){
            String urlimagem = listaFotosRecuperadas.get(i);
            int tamanhoLista = listaFotosRecuperadas.size();
            salvarFotosStorage (urlimagem,tamanhoLista,i);
        }
    }

    private void salvarFotosStorage(String urlimagem, int tamanhoLista, int i) {
    }

    private void carregarDadosSpinner() {
    }

    private void inicializarComponentes() {
        
    }

    @Override
            public void onClick (View v){

    }

  }


