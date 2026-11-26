package com.minify.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Fila Encadeada (FIFO - First In, First Out).
 * Utilizada para a Fila de Reprodução do Player.
 * O primeiro elemento adicionado é sempre o primeiro a ser reproduzido.
 */
public class Fila<T> {

    // Nó interno da fila
    private static final class No<T> {
        private final T valor;
        private No<T> proximo;

        private No(T valor) {
            this.valor = valor;
        }
    }

    private No<T> primeiro;
    private No<T> ultimo;
    private int tamanho;

    /**
     * Adiciona um elemento ao final da fila (Enqueue).
     */
    public void enfileirar(T valor) {
        No<T> novo = new No<>(valor);
        if (ultimo == null) {
            primeiro = novo;
        } else {
            ultimo.proximo = novo;
        }
        ultimo = novo;
        tamanho++;
    }

    /**
     * Remove e retorna o primeiro elemento da fila (Dequeue).
     */
    public T desenfileirar() {
        if (primeiro == null) {
            throw new NoSuchElementException("A fila está vazia");
        }
        T valor = primeiro.valor;
        primeiro = primeiro.proximo;
        if (primeiro == null) {
            ultimo = null;
        }
        tamanho--;
        return valor;
    }

    /**
     * Retorna o primeiro elemento sem removê-lo (Peek / Consulta).
     */
    public T espiar() {
        if (primeiro == null) {
            throw new NoSuchElementException("A fila está vazia");
        }
        return primeiro.valor;
    }

    /**
     * Retorna todos os elementos da fila na ordem de reprodução
     * sem desenfileirar (iteração/impressão).
     */
    public List<T> listar() {
        List<T> elementos = new ArrayList<>();
        No<T> atual = primeiro;
        while (atual != null) {
            elementos.add(atual.valor);
            atual = atual.proximo;
        }
        return elementos;
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}
