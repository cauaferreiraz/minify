package com.minify.util;

import com.minify.model.Musica;
import com.minify.model.Playlist;
import com.minify.service.BibliotecaService;
import com.minify.service.PlayerService;
import com.minify.service.PlaylistService;
import com.minify.structures.ListaDuplamenteEncadeada;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interface interativa via console do sistema Minify.
 * Demonstra a integração entre as 3 estruturas de dados:
 * - Árvore Binária de Busca (Biblioteca)
 * - Lista Duplamente Encadeada (Playlists)
 * - Fila FIFO (Reprodução)
 */
public class Menu {
    private final BibliotecaService biblioteca = new BibliotecaService();
    private final PlaylistService playlistsService = new PlaylistService();
    private final PlayerService player = new PlayerService();
    private final ListaDuplamenteEncadeada<Playlist> playlists = new ListaDuplamenteEncadeada<>();

    public void exibir() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean executando = true;
            while (executando) {
                mostrarOpcoes();
                System.out.print("Escolha uma opção: ");
                if (!scanner.hasNextLine()) {
                    break;
                }

                String opcao = scanner.nextLine().trim();
                System.out.println();

                switch (opcao) {
                    // Árvore Binária de Busca (Biblioteca)
                    case "1" -> cadastrarMusica(scanner);
                    case "2" -> listarMusicas();
                    case "3" -> buscarMusica(scanner);
                    case "4" -> removerMusicaDaBiblioteca(scanner);

                    // Lista Duplamente Encadeada (Playlists)
                    case "5" -> criarPlaylist(scanner);
                    case "6" -> listarPlaylists();
                    case "7" -> adicionarMusicaAPlaylist(scanner);
                    case "8" -> removerMusicaDaPlaylist(scanner);

                    // Fila (Player de Reprodução)
                    case "9" -> adicionarMusicaAFila(scanner);
                    case "10" -> verFilaDeReproducao();
                    case "11" -> tocarProxima();

                    // Auxiliar
                    case "12" -> carregarDadosDeExemplo();
                    case "0" -> executando = false;
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
                System.out.println();
            }
        }
        System.out.println("Minify encerrado. Até mais!");
    }

    private void mostrarOpcoes() {
        System.out.println("=========================================");
        System.out.println("             MINIFY PLAYER               ");
        System.out.println("=========================================");
        System.out.println("[ BIBLIOTECA - Árvore Binária de Busca ]");
        System.out.println("  1. Cadastrar música");
        System.out.println("  2. Listar músicas (em ordem de ID)");
        System.out.println("  3. Buscar música por ID");
        System.out.println("  4. Remover música da biblioteca");
        System.out.println();
        System.out.println("[ PLAYLISTS - Lista Duplamente Encadeada ]");
        System.out.println("  5. Criar playlist");
        System.out.println("  6. Listar playlists");
        System.out.println("  7. Adicionar música à playlist");
        System.out.println("  8. Remover música da playlist");
        System.out.println();
        System.out.println("[ PLAYER - Fila de Reprodução (FIFO) ]");
        System.out.println("  9. Adicionar música à fila");
        System.out.println(" 10. Ver fila de reprodução");
        System.out.println(" 11. Tocar próxima música");
        System.out.println();
        System.out.println("[ OUTROS ]");
        System.out.println(" 12. Carregar músicas de exemplo");
        System.out.println("  0. Sair");
        System.out.println("=========================================");
    }

    // ==========================================
    // MÉTODOS DA BIBLIOTECA (Árvore Binária)
    // ==========================================

    private void cadastrarMusica(Scanner scanner) {
        Long id = lerLong(scanner, "ID da música (número): ");
        if (id == null) return;

        String titulo = lerTexto(scanner, "Título: ");
        String artista = lerTexto(scanner, "Artista: ");
        String album = lerTexto(scanner, "Álbum: ");
        Integer duracao = lerInteiro(scanner, "Duração em segundos: ");

        if (titulo == null || artista == null || album == null || duracao == null) {
            return;
        }

        Musica novaMusica = new Musica(id, titulo, artista, album, duracao);
        boolean inseriu = biblioteca.adicionarMusica(novaMusica);

        if (inseriu) {
            System.out.println(">> Música cadastrada com sucesso na Árvore Binária!");
        } else {
            System.out.println(">> Erro: Já existe uma música com o ID " + id + " na árvore.");
        }
    }

    private void listarMusicas() {
        List<Musica> musicas = biblioteca.listarMusicas();
        if (musicas.isEmpty()) {
            System.out.println(">> Nenhuma música cadastrada na biblioteca.");
            return;
        }
        System.out.println("--- Músicas na Biblioteca (Percurso Em-Ordem na BST) ---");
        for (Musica musica : musicas) {
            imprimirMusica(musica);
        }
        System.out.println("Total: " + musicas.size() + " música(s).");
    }

    private void buscarMusica(Scanner scanner) {
        Long id = lerLong(scanner, "ID da música a buscar: ");
        if (id == null) return;

        Optional<Musica> resultado = biblioteca.buscarPorId(id);
        if (resultado.isPresent()) {
            System.out.println(">> Música encontrada na Árvore:");
            imprimirMusica(resultado.get());
        } else {
            System.out.println(">> Música com ID " + id + " não encontrada na árvore.");
        }
    }

    private void removerMusicaDaBiblioteca(Scanner scanner) {
        Long id = lerLong(scanner, "ID da música a remover: ");
        if (id == null) return;

        boolean removeu = biblioteca.removerMusica(id);
        if (removeu) {
            System.out.println(">> Música com ID " + id + " removida com sucesso da árvore.");
        } else {
            System.out.println(">> Música não encontrada para remoção.");
        }
    }

    // ==========================================
    // MÉTODOS DE PLAYLIST (Lista Duplamente Encadeada)
    // ==========================================

    private void criarPlaylist(Scanner scanner) {
        Long id = lerLong(scanner, "ID da playlist: ");
        if (id == null) return;

        String nome = lerTexto(scanner, "Nome da playlist: ");
        if (nome == null) return;

        playlists.adicionar(new Playlist(id, nome));
        System.out.println(">> Playlist '" + nome + "' criada com sucesso!");
    }

    private void listarPlaylists() {
        if (playlists.estaVazia()) {
            System.out.println(">> Nenhuma playlist criada até o momento.");
            return;
        }
        System.out.println("--- Playlists Cadastradas (Lista Duplamente Encadeada) ---");
        for (Playlist playlist : playlists) {
            System.out.printf("[%d] %s (%d música(s))%n", playlist.getId(), playlist.getNome(), playlist.quantidadeMusicas());
            if (!playlist.temMusicas()) {
                System.out.println("     (Nenhuma música adicionada)");
            } else {
                for (Musica musica : playlist.getMusicas()) {
                    System.out.println("     -> " + musica);
                }
            }
        }
    }

    private void adicionarMusicaAPlaylist(Scanner scanner) {
        Long playlistId = lerLong(scanner, "ID da playlist: ");
        if (playlistId == null) return;

        Playlist playlist = buscarPlaylist(playlistId);
        if (playlist == null) {
            System.out.println(">> Playlist não encontrada.");
            return;
        }

        Long musicaId = lerLong(scanner, "ID da música (da biblioteca): ");
        if (musicaId == null) return;

        Optional<Musica> musica = biblioteca.buscarPorId(musicaId);
        if (musica.isEmpty()) {
            System.out.println(">> Música com ID " + musicaId + " não encontrada na biblioteca.");
            return;
        }

        playlistsService.adicionarMusica(playlist, musica.get());
        System.out.println(">> Música '" + musica.get().getTitulo() + "' adicionada à playlist '" + playlist.getNome() + "'!");
    }

    private void removerMusicaDaPlaylist(Scanner scanner) {
        Long playlistId = lerLong(scanner, "ID da playlist: ");
        if (playlistId == null) return;

        Playlist playlist = buscarPlaylist(playlistId);
        if (playlist == null) {
            System.out.println(">> Playlist não encontrada.");
            return;
        }

        Long musicaId = lerLong(scanner, "ID da música a remover da playlist: ");
        if (musicaId == null) return;

        Optional<Musica> musica = biblioteca.buscarPorId(musicaId);
        if (musica.isEmpty()) {
            System.out.println(">> Música não encontrada na biblioteca.");
            return;
        }

        boolean removeu = playlistsService.removerMusica(playlist, musica.get());
        if (removeu) {
            System.out.println(">> Música removida da playlist!");
        } else {
            System.out.println(">> Essa música não está presente nesta playlist.");
        }
    }

    // ==========================================
    // MÉTODOS DO PLAYER (Fila FIFO)
    // ==========================================

    private void adicionarMusicaAFila(Scanner scanner) {
        Long id = lerLong(scanner, "ID da música para enfileirar: ");
        if (id == null) return;

        Optional<Musica> musica = biblioteca.buscarPorId(id);
        if (musica.isEmpty()) {
            System.out.println(">> Música não encontrada na biblioteca.");
            return;
        }

        player.adicionarNaFila(musica.get());
        System.out.println(">> Música '" + musica.get().getTitulo() + "' inserida na Fila de Reprodução!");
    }

    private void verFilaDeReproducao() {
        List<Musica> fila = player.listarFila();
        if (fila.isEmpty()) {
            System.out.println(">> A fila de reprodução está vazia.");
            return;
        }
        System.out.println("--- Fila de Reprodução Atual (Ordem FIFO) ---");
        int pos = 1;
        for (Musica m : fila) {
            System.out.println(" " + (pos++) + "º na fila: " + m.getTitulo() + " - " + m.getArtista());
        }
    }

    private void tocarProxima() {
        Optional<Musica> musica = player.proximaMusica();
        if (musica.isPresent()) {
            Musica m = musica.get();
            System.out.println(">> [TOCANDO AGORA]: " + m.getTitulo() + " - " + m.getArtista()
                    + " (" + m.getDuracaoSegundos() + "s) [Álbum: " + m.getAlbum() + "]");
        } else {
            System.out.println(">> Nenhuma música na fila para tocar.");
        }
    }

    // ==========================================
    // UTILITÁRIO: DADOS DE TESTE
    // ==========================================

    private void carregarDadosDeExemplo() {
        biblioteca.adicionarMusica(new Musica(10, "Bohemian Rhapsody", "Queen", "A Night at the Opera", 354));
        biblioteca.adicionarMusica(new Musica(5, "Billie Jean", "Michael Jackson", "Thriller", 294));
        biblioteca.adicionarMusica(new Musica(20, "Hotel California", "Eagles", "Hotel California", 390));
        biblioteca.adicionarMusica(new Musica(2, "Shape of You", "Ed Sheeran", "Divide", 233));
        biblioteca.adicionarMusica(new Musica(8, "Smells Like Teen Spirit", "Nirvana", "Nevermind", 301));

        Playlist p1 = new Playlist(1, "Clássicos do Rock");
        biblioteca.buscarPorId(10).ifPresent(p1::adicionarMusica);
        biblioteca.buscarPorId(20).ifPresent(p1::adicionarMusica);
        biblioteca.buscarPorId(8).ifPresent(p1::adicionarMusica);
        playlists.adicionar(p1);

        System.out.println(">> 5 músicas cadastradas na biblioteca (IDs: 10, 5, 20, 2, 8).");
        System.out.println(">> 1 playlist 'Clássicos do Rock' criada com 3 músicas.");
        System.out.println(">> Dica: Liste as músicas (opção 2) para ver que a árvore as ordenou automaticamente por ID!");
    }

    // ==========================================
    // MÉTODOS AUXILIARES DE LEITURA
    // ==========================================

    private Playlist buscarPlaylist(long id) {
        for (Playlist playlist : playlists) {
            if (playlist.getId() == id) {
                return playlist;
            }
        }
        return null;
    }

    private String lerTexto(Scanner scanner, String mensagem) {
        System.out.print(mensagem);
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }

    private Long lerLong(Scanner scanner, String mensagem) {
        while (true) {
            String valor = lerTexto(scanner, mensagem);
            if (valor == null) return null;
            try {
                return Long.parseLong(valor);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private Integer lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            String valor = lerTexto(scanner, mensagem);
            if (valor == null) return null;
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        }
    }

    private void imprimirMusica(Musica musica) {
        System.out.printf("  [ID: %d] %s | Artista: %s | Álbum: %s (%ds)%n",
                musica.getId(), musica.getTitulo(), musica.getArtista(),
                musica.getAlbum(), musica.getDuracaoSegundos());
    }
}
