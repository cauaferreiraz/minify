package com.minify.service;

import com.minify.model.Musica;
import com.minify.model.Playlist;

import java.util.Objects;

public class PlaylistService {
    public void adicionarMusica(Playlist playlist, Musica musica) {
        Objects.requireNonNull(playlist, "playlist não pode ser nula")
                .adicionarMusica(musica);
    }

    public boolean removerMusica(Playlist playlist, Musica musica) {
        return Objects.requireNonNull(playlist, "playlist não pode ser nula")
                .removerMusica(musica);
    }
}
