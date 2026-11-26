package com.minify.model;

import com.minify.structures.ListaDuplamenteEncadeada;

import java.util.Objects;

/**
 * Representa uma playlist no sistema Minify.
 * Utiliza internamente uma LISTA DUPLAMENTE ENCADEADA para gerenciar suas músicas,
 * permitindo inserções, remoções e navegação flexível.
 */
public class Playlist {
    private long id;
    private String nome;
    private final ListaDuplamenteEncadeada<Musica> musicas = new ListaDuplamenteEncadeada<>();

    public Playlist(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Iterable<Musica> getMusicas() {
        return musicas;
    }

    public boolean temMusicas() {
        return !musicas.estaVazia();
    }

    public void adicionarMusica(Musica musica) {
        musicas.adicionar(Objects.requireNonNull(musica, "musica não pode ser nula"));
    }

    public boolean removerMusica(Musica musica) {
        return musicas.remover(musica);
    }

    public int quantidadeMusicas() {
        return musicas.tamanho();
    }
}
