package com.example.orlyx;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
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
import java.util.Locale;

public class CadastrarAnunciosActivity extends AppCompatActivity

    implements View.OnClickListener{

        private EditText campoTitulo, campoDesc;
        private CurrencyEditText campoValor;
        private MaskEditText campoContato;
        private ImageView imagem1, imagem2, imagem3;
        private Spinner campoEstado, campoCategoria;
        private Anuncio anuncio;
        private StorageReference storage;
        private android.app.AlertDialog dialog;

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

        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Salvando Anúncio...").setCancelable("false").build();

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

        campoTitulo = findViewById(R.id.editTitulo);
        campoDesc = findViewById(R.id.editDesc);
        campoValor = findViewById(R.id.editValor);
        campoContato = findViewById(R.id.editContato);
        imagem1 = findViewById(R.id.imgCadastro1);
        imagem2 = findViewById(R.id.imgCadastro2);
        imagem3 = findViewById(R.id.imgCadastro3);
        campoEstado = findViewById(R.id.spinnerEstado);
        campoCategoria = findViewById(R.id.spinnerCategoria);

        imagem1.setOnClickListener(this);
        imagem2.setOnClickListener(this);
        imagem3.setOnClickListener(this);

        Locale locale = new Locale("pt","BR");
        campoValor.setLocale(locale);
    }

    @Override
            public void onClick (View v){

    }

    public void validarDadosAnuncio (View view){

    }

    public void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas :(");
        builder.setMessage("Para utilizar o app, é necessário aceitar as permissões!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
  }


