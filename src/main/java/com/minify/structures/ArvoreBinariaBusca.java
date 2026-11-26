package com.minify.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Árvore Binária de Busca (BST - Binary Search Tree).
 * Utilizada para gerenciar a Biblioteca de Músicas com busca rápida O(log n).
 *
 * Propriedades:
 * - Subárvore esquerda contém apenas elementos menores que a raiz.
 * - Subárvore direita contém apenas elementos maiores que a raiz.
 */
public class ArvoreBinariaBusca<T extends Comparable<? super T>> {

    // Nó interno da árvore
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

    /**
     * Insere um novo elemento na árvore de forma ordenada.
     */
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
                // Elemento duplicado (mesmo ID) não é permitido
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

    /**
     * Busca um elemento na árvore com complexidade média O(log n).
     */
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

    /**
     * Remove um elemento da árvore binária mantendo suas propriedades.
     */
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
            // Encontrou o nó a ser removido
            tamanho--;

            // Caso 1: Nó folha (sem filhos)
            if (atual.esquerda == null && atual.direita == null) {
                return null;
            }
            // Caso 2: Apenas um filho (à direita ou à esquerda)
            if (atual.esquerda == null) {
                return atual.direita;
            }
            if (atual.direita == null) {
                return atual.esquerda;
            }

            // Caso 3: Nó com dois filhos
            // Substitui pelo menor valor da subárvore direita (sucessor em ordem)
            T menorValor = encontrarMenorValor(atual.direita);
            atual.valor = menorValor;
            // Remove o nó duplicado na subárvore direita
            atual.direita = removerRecursivo(atual.direita, menorValor);
            tamanho++; // Compensa o decremento pois a chamada recursiva acima já decrementa
        }
        return atual;
    }

    private T encontrarMenorValor(No<T> no) {
        return no.esquerda == null ? no.valor : encontrarMenorValor(no.esquerda);
    }

    /**
     * Percurso Em-Ordem (In-Order: Esquerda -> Raiz -> Direita).
     * Retorna os elementos em ordem crescente (no caso de músicas, por ID).
     */
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
