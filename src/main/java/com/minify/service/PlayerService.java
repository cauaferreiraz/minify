package com.minify.service;

import com.minify.model.Musica;
import com.minify.structures.Fila;

import java.util.List;
import java.util.Optional;

public class PlayerService {
    private final Fila<Musica> fila = new Fila<>();

    public void adicionarNaFila(Musica musica) {
        fila.enfileirar(musica);
    }

    public Optional<Musica> proximaMusica() {
        if (fila.estaVazia()) {
            return Optional.empty();
        }
        return Optional.of(fila.desenfileirar());
    }

    public Optional<Musica> espiarProxima() {
        if (fila.estaVazia()) {
            return Optional.empty();
        }
        return Optional.of(fila.espiar());
    }

    public List<Musica> listarFila() {
        return fila.listar();
    }

    public boolean filaVazia() {
        return fila.estaVazia();
    }

    public int quantidadeNaFila() {
        return fila.tamanho();
    }
}
