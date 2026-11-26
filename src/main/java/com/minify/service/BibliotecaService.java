package com.minify.service;

import com.minify.model.Musica;
import com.minify.structures.ArvoreBinariaBusca;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelo acervo geral de músicas.
 * Utiliza a ÁRVORE BINÁRIA DE BUSCA (BST) para armazenar, buscar e listar músicas.
 */
public class BibliotecaService {
    // A biblioteca é uma Árvore Binária de Busca!
    private final ArvoreBinariaBusca<Musica> musicas = new ArvoreBinariaBusca<>();

    /**
     * Adiciona uma música na árvore. Retorna true se adicionada, false se ID já existir.
     */
    public boolean adicionarMusica(Musica musica) {
        return musicas.inserir(musica);
    }

    /**
     * Busca uma música pelo ID usando a busca binária na árvore (O(log n)).
     */
    public Optional<Musica> buscarPorId(long id) {
        // Cria uma música fictícia apenas com o ID para pesquisar na BST
        Musica chave = new Musica(id, "", "", "", 0);
        return musicas.buscar(chave);
    }

    /**
     * Remove uma música da árvore binária pelo ID.
     */
    public boolean removerMusica(long id) {
        Musica chave = new Musica(id, "", "", "", 0);
        return musicas.remover(chave);
    }

    /**
     * Retorna todas as músicas em ordem crescente de ID usando percurso Em-Ordem.
     */
    public List<Musica> listarMusicas() {
        return musicas.listarEmOrdem();
    }

    public int quantidadeDeMusicas() {
        return musicas.tamanho();
    }
}
