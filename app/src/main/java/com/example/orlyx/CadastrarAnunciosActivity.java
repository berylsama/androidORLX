package com.example.orlyx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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

//        dialog = new SpotsDialog.Builder().setContext(this).setMessage("Salvando Anúncio...").setCancelable("false").build();

        for (int contador = 0; contador <listaFotosRecuperadas.size(); contador++){
            String urlString = listaFotosRecuperadas.get(contador);
            int totalFotos = listaFotosRecuperadas.size();
            salvarFotosStorage (urlString,totalFotos,contador);
        }
    }


    private void salvarFotosStorage(final String urlString, final int totalFotos, int contador) {
        //Criar a referencia do Storage
        StorageReference imagemAnuncio = storage.child("imagens").child("anuncios").child(anuncio.getIdAnuncio()).child("imagem" + contador);
        //Fazer upload do arquivo
        //Upload Task uploada

        final UploadTask uploadTask = imagemAnuncio.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri fireaseUrl = taskSnapshot.getUploadSessionUri();
                String urlConvertida = fireaseUrl.toString();
                listaURLFotos.add(urlConvertida);
                if(totalFotos == listaURLFotos.size());{
                 anuncio.setFotos(listaURLFotos);
                 anuncio.salvar();
                 dialog.dismiss();
                 finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //mensagem de erro
                exibirMensagemErro("Falha ao fazer Upload");
                //Log.i ("INFO", "Falha no upload" + e.getMessage())
            }

        });
    }

    private void exibirMensagemErro(String falha_ao_fazer_upload) {
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
        //recuperando os valores digitados
        anuncio = configurarAnuncio();
        String telefone = "";
        String valor = String.valueOf(campoValor.getRawValue());
        String estado = campoEstado.getSelectedItem().toString();
        String categoria = campoCategoria.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();

        if(campoContato.getRawText()!=null){
            telefone = campoContato.getRawText().toString();
        }
        String desc = campoDesc.getText().toString();
        //recuperar imagens
        if(listaFotosRecuperadas.size()!=0){
            if(!anuncio.getEstado().isEmpty()){
                if(!anuncio.getTitulo().isEmpty()){
                    if(valor.isEmpty()&&!valor.equals("0")){
                        if (!anuncio.getTelefone().isEmpty()&&anuncio.getTelefone().length()==11){
                           if(!anuncio.getDescricao().isEmpty()){
                               salvarAnuncio(view);
                           }
                        }
                        else{
                            exibirMensagemErro("Digite um número com 11 dígitos...");

                        }
                    }
                    else{
                        exibirMensagemErro("Aplique um valor do produto...");

                    }
                }
                else{
                    exibirMensagemErro("Dê um título...");

                }
            }
            else{
                exibirMensagemErro("Escolha um estado...");
            }
        }
        else{
            exibirMensagemErro("Escolha ao menos uma imagem...");
        }
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

    private Anuncio configurarAnuncio(){

        String cel = "";
        String estado = campoEstado.getSelectedItem().toString();
        String categoria = campoCategoria.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String desc = campoDesc.getText().toString();
        String valor = campoValor.getText().toString();
        String contato = campoContato.getText().toString();

        Anuncio anuncio= new Anuncio();
        anuncio.setEstado(estado);
        anuncio.setDescricao(desc);
        anuncio.setTelefone(contato);
        anuncio.setCategoria(categoria);
        anuncio.setTitulo(titulo);
        anuncio.setValor(valor);

        return anuncio;
    }

  }


