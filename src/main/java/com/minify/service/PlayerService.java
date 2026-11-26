package com.minify.service;

import com.minify.model.Musica;
import com.minify.structures.Fila;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Serviço responsável pelo controle de reprodução.
 * Utiliza uma FILA (FIFO) para garantir que as músicas toquem na ordem em que foram adicionadas.
 */
public class PlayerService {
    private final Fila<Musica> fila = new Fila<>();

    /**
     * Adiciona uma música ao final da fila de reprodução.
     */
    public void adicionarNaFila(Musica musica) {
        fila.enfileirar(musica);
    }

    /**
     * Toca e remove a próxima música da fila.
     */
    public Optional<Musica> proximaMusica() {
        if (fila.estaVazia()) {
            return Optional.empty();
        }
        return Optional.of(fila.desenfileirar());
    }

    /**
     * Consulta a próxima música sem removê-la da fila.
     */
    public Optional<Musica> espiarProxima() {
        if (fila.estaVazia()) {
            return Optional.empty();
        }
        return Optional.of(fila.espiar());
    }

    /**
     * Retorna a lista de músicas que estão aguardando na fila.
     */
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
