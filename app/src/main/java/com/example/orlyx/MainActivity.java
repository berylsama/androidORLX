package com.example.orlyx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MainActivity extends AppCompatActivity {

    private static DatabaseReference referenceFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference referenciaStorage;

    private Button botaoacessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inicializarComponentes();

        autenticacao = ConfigFirebase.getReferenciaAutenticacao();
        botaoacessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                if (!email.isEmpty()) {
                    if (!senha.isEmpty()) {
                        if (tipoAcesso.isChecked()) {
                            autenticacao.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Cadastro realizado com Sucesso!!! :)", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), CadastrarAnunciosActivity.class));
                                    }

                                    else {
                                        String erroExcecao = "";
                                        try {
                                            throw task.getException();
                                        }
                                        catch(FirebaseAuthWeakPasswordException e){
                                            erroExcecao = "Por favor, digite uma senha mais forte!";
                                        }
                                        catch (FirebaseAuthInvalidCredentialsException e) {
                                            erroExcecao = "Por favor, digite um e-mail válido!";
                                        }
                                        catch(FirebaseAuthUserCollisionException e) {
                                            erroExcecao="Esta conta já possui cadastro...";
                                        }
                                        catch (Exception e) {
                                            erroExcecao = "ao cadastrar usuário:"+e.getMessage();

                                            e.printStackTrace();
                                        }
                                        Toast.makeText(MainActivity.this,"Erro: "+erroExcecao, Toast.LENGTH_LONG).show();

                                    }

                                }

                            });

                        }
                        else{
                            autenticacao.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this,"Ok, logado com sucesso!",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),AnunciosActivity.class));
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this,"Erro ao fazer login..."+task.getException(),Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }
                    }

                    else{
                        Toast.makeText(MainActivity.this,"Ei, preencha sua senha...",Toast.LENGTH_LONG).show();

                    }
                }

            }

        });

//
//        //verificar se o Database estará conectado ao android
//        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference usuariosReferencia = firebaseDatabase.child("usuarios");
//        DatabaseReference produtosReferencia = firebaseDatabase.child("produtos");
//
//        //1o teste ----- firebasedatabase deverá ter uma classe de sua tabela
//
//        Usuarios usuario = new Usuarios();
//        usuario.setNome("Amaria");
//        usuario.setSobrenome("Barros");
//        usuario.setIdade(17);
//        usuario.setCidade("Santos");
//        usuario.setBairro("Aparecida");
//        usuario.setTelefone("11 - 9 8989-7865");
//
//        usuariosReferencia.child("120").setValue(usuario);
//
//
//        Produtos produto = new Produtos();
//        produto.setNomeProduto("Bolo de Morango");
//        produto.setDescricao("Bolo recheado de Morango");
//        produto.setValor(4.80);
//        produto.setQuantidade(10);
//        produto.setCategoria("Bolos");
//
//        produtosReferencia.child("420").setValue(produto);




    }
    private void inicializarComponentes(){

        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoacessar = findViewById(R.id.btnAcessar);
        tipoAcesso = findViewById(R.id.switchAcesso);

    }
}
