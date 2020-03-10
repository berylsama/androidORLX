package com.example.orlyx;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Anuncio {

    private String idAnuncio;
    private String estado;
    private String titulo;
    private String valor;
    private String descricao;
    private String telefone;
    private String categoria;
    private List<String> fotos;


    public Anuncio() {

        DatabaseReference anuncioRef = ConfigFirebase.getReferenceFirebase().child("meus_anuncios");
        setIdAnuncio(anuncioRef.push().getKey());
    }

    public void salvar() {
       //String idUsuario = ConfigFirebase.getIdUsuario();
        //DatabaseReference anuncioRef = ConfigFirebase.getReferenceFirebase().child("meus_anuncios");
       // anuncioRef.child(idUsuario).child(getIdAnuncio()).setValue(this);

    }

    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getFoto(){

        return fotos;
    }

    public void setFotos (List<String> fotos){
        this.fotos = fotos;
    }
}

