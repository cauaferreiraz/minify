package com.minify.model;

public class Musica implements Comparable<Musica> {
    private long id;
    private String titulo;
    private String artista;
    private String album;
    private int duracaoSegundos;

    public Musica(long id, String titulo, String artista, String album, int duracaoSegundos) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracaoSegundos = duracaoSegundos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }

    @Override
    public int compareTo(Musica outra) {
        return Long.compare(this.id, outra.id);
    }

    @Override
    public String toString() {
        return titulo + " - " + artista;
    }
}
