package com.minify.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Fila<T> {

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

    public T espiar() {
        if (primeiro == null) {
            throw new NoSuchElementException("A fila está vazia");
        }
        return primeiro.valor;
    }

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
