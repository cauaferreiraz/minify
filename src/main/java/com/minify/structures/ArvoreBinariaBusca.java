package com.minify.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArvoreBinariaBusca<T extends Comparable<? super T>> {

    private static final class No<T> {
        private T valor;
        private No<T> esquerda;
        private No<T> direita;

        private No(T valor) {
            this.valor = valor;
        }
    }

    private No<T> raiz;
    private int tamanho;

    public boolean inserir(T valor) {
        if (valor == null) {
            throw new NullPointerException("valor não pode ser nulo");
        }
        if (raiz == null) {
            raiz = new No<>(valor);
            tamanho++;
            return true;
        }

        No<T> atual = raiz;
        while (true) {
            int comparacao = valor.compareTo(atual.valor);
            if (comparacao == 0) {
                return false;
            }
            if (comparacao < 0) {
                if (atual.esquerda == null) {
                    atual.esquerda = new No<>(valor);
                    tamanho++;
                    return true;
                }
                atual = atual.esquerda;
            } else {
                if (atual.direita == null) {
                    atual.direita = new No<>(valor);
                    tamanho++;
                    return true;
                }
                atual = atual.direita;
            }
        }
    }

    public Optional<T> buscar(T valor) {
        if (valor == null) {
            return Optional.empty();
        }
        No<T> atual = raiz;
        while (atual != null) {
            int comparacao = valor.compareTo(atual.valor);
            if (comparacao == 0) {
                return Optional.of(atual.valor);
            }
            atual = comparacao < 0 ? atual.esquerda : atual.direita;
        }
        return Optional.empty();
    }

    public boolean remover(T valor) {
        if (valor == null || raiz == null) {
            return false;
        }
        int tamanhoAntes = tamanho;
        raiz = removerRecursivo(raiz, valor);
        return tamanho < tamanhoAntes;
    }

    private No<T> removerRecursivo(No<T> atual, T valor) {
        if (atual == null) {
            return null;
        }

        int comparacao = valor.compareTo(atual.valor);
        if (comparacao < 0) {
            atual.esquerda = removerRecursivo(atual.esquerda, valor);
        } else if (comparacao > 0) {
            atual.direita = removerRecursivo(atual.direita, valor);
        } else {
            tamanho--;

            if (atual.esquerda == null && atual.direita == null) {
                return null;
            }
            if (atual.esquerda == null) {
                return atual.direita;
            }
            if (atual.direita == null) {
                return atual.esquerda;
            }

            T menorValor = encontrarMenorValor(atual.direita);
            atual.valor = menorValor;
            atual.direita = removerRecursivo(atual.direita, menorValor);
            tamanho++;
        }
        return atual;
    }

    private T encontrarMenorValor(No<T> no) {
        return no.esquerda == null ? no.valor : encontrarMenorValor(no.esquerda);
    }

    public List<T> listarEmOrdem() {
        List<T> lista = new ArrayList<>();
        percursoEmOrdem(raiz, lista);
        return lista;
    }

    private void percursoEmOrdem(No<T> no, List<T> lista) {
        if (no != null) {
            percursoEmOrdem(no.esquerda, lista);
            lista.add(no.valor);
            percursoEmOrdem(no.direita, lista);
        }
    }

    public int tamanho() {
        return tamanho;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }
}
