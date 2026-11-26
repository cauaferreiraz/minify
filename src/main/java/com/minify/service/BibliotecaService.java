package com.minify.service;

import com.minify.model.Musica;
import com.minify.structures.ArvoreBinariaBusca;

import java.util.List;
import java.util.Optional;

public class BibliotecaService {
    private final ArvoreBinariaBusca<Musica> musicas = new ArvoreBinariaBusca<>();

    public boolean adicionarMusica(Musica musica) {
        return musicas.inserir(musica);
    }

    public Optional<Musica> buscarPorId(long id) {
        Musica chave = new Musica(id, "", "", "", 0);
        return musicas.buscar(chave);
    }

    public boolean removerMusica(long id) {
        Musica chave = new Musica(id, "", "", "", 0);
        return musicas.remover(chave);
    }

    public List<Musica> listarMusicas() {
        return musicas.listarEmOrdem();
    }

    public int quantidadeDeMusicas() {
        return musicas.tamanho();
    }
}
