package com.minify.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ListaDuplamenteEncadeada<T> implements Iterable<T> {

    private static final class No<T> {
        private final T valor;
        private No<T> anterior;
        private No<T> proximo;

        private No(T valor) {
            this.valor = valor;
        }
    }

    private No<T> primeiro;
    private No<T> ultimo;
    private int tamanho;

    public void adicionar(T valor) {
        No<T> novo = new No<>(valor);
        if (ultimo == null) {
            primeiro = novo;
        } else {
            ultimo.proximo = novo;
            novo.anterior = ultimo;
        }
        ultimo = novo;
        tamanho++;
    }

    public T obter(int indice) {
        return noNoIndice(indice).valor;
    }

    public T remover(int indice) {
        No<T> no = noNoIndice(indice);
        desvincular(no);
        return no.valor;
    }

    public boolean remover(T valor) {
        No<T> atual = primeiro;
        while (atual != null) {
            if (Objects.equals(atual.valor, valor)) {
                desvincular(atual);
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    private void desvincular(No<T> no) {
        if (no.anterior == null) {
            primeiro = no.proximo;
        } else {
            no.anterior.proximo = no.proximo;
        }
        if (no.proximo == null) {
            ultimo = no.anterior;
        } else {
            no.proximo.anterior = no.anterior;
        }
        tamanho--;
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

    private No<T> noNoIndice(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", tamanho: " + tamanho);
        }
        if (indice < tamanho / 2) {
            No<T> atual = primeiro;
            for (int i = 0; i < indice; i++) {
                atual = atual.proximo;
            }
            return atual;
        }
        No<T> atual = ultimo;
        for (int i = tamanho - 1; i > indice; i--) {
            atual = atual.anterior;
        }
        return atual;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private No<T> atual = primeiro;

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T valor = atual.valor;
                atual = atual.proximo;
                return valor;
            }
        };
    }
}
