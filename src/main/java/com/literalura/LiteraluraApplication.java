package com.literalura;

import com.literalura.model.Autor;
import com.literalura.model.Livro;
import com.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        exibirMenu();
    }

    private void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                    
                    ╔══════════════════════════════════════╗
                    ║         📚  L I T E R A L U R A      ║
                    ║         Catálogo de Livros            ║
                    ╠══════════════════════════════════════╣
                    ║  1 - Buscar livro por título          ║
                    ║  2 - Listar livros cadastrados        ║
                    ║  3 - Listar autores cadastrados       ║
                    ║  4 - Listar autores vivos em um ano   ║
                    ║  5 - Listar livros por idioma         ║
                    ║  6 - Contagem de livros por idioma    ║
                    ║  0 - Sair                             ║
                    ╚══════════════════════════════════════╝
                    Escolha uma opção:""");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // limpa o buffer

                switch (opcao) {
                    case 1 -> buscarLivro(scanner);
                    case 2 -> listarLivros();
                    case 3 -> listarAutores();
                    case 4 -> listarAutoresVivosNoAno(scanner);
                    case 5 -> listarLivrosPorIdioma(scanner);
                    case 6 -> contarLivrosPorIdioma(scanner);
                    case 0 -> System.out.println("\nEncerrando o LiterAlura. Até logo! 👋");
                    default -> System.out.println("\nOpção inválida. Por favor, escolha entre 0 e 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nEntrada inválida! Por favor, digite apenas números.");
                scanner.nextLine(); // limpa o buffer após entrada inválida
                opcao = -1;
            }
        }
        scanner.close();
    }

    // Opção 1: Busca livro na API pelo título e salva no banco
    private void buscarLivro(Scanner scanner) {
        System.out.print("\nDigite o título do livro que deseja buscar: ");
        String titulo = scanner.nextLine();

        if (titulo.isBlank()) {
            System.out.println("O título não pode estar em branco.");
            return;
        }

        try {
            System.out.println("\nBuscando na API Gutendex...");
            Livro livro = livroService.buscarESalvarLivro(titulo);
            System.out.println("\nLivro encontrado e salvo com sucesso!");
            System.out.println(livro);
        } catch (RuntimeException e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }

    // Opção 2: Lista todos os livros salvos no banco
    private void listarLivros() {
        List<Livro> livros = livroService.listarTodosLivros();

        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro cadastrado ainda. Use a opção 1 para buscar livros.");
            return;
        }

        System.out.println("\n📚 Livros cadastrados no catálogo (" + livros.size() + " no total):");
        livros.forEach(System.out::println);
    }

    // Opção 3: Lista todos os autores salvos no banco
    private void listarAutores() {
        List<Autor> autores = livroService.listarTodosAutores();

        if (autores.isEmpty()) {
            System.out.println("\nNenhum autor cadastrado ainda.");
            return;
        }

        System.out.println("\n✍️  Autores cadastrados no catálogo (" + autores.size() + " no total):");
        autores.forEach(a -> {
            System.out.println(a);
            System.out.println("   Livros: " + a.getLivros().stream()
                    .map(Livro::getTitulo)
                    .reduce((t1, t2) -> t1 + ", " + t2)
                    .orElse("nenhum"));
        });
    }

    // Opção 4: Lista autores que estavam vivos em um determinado ano
    private void listarAutoresVivosNoAno(Scanner scanner) {
        System.out.print("\nDigite o ano para verificar quais autores estavam vivos: ");

        try {
            int ano = scanner.nextInt();
            scanner.nextLine();

            if (ano < 0 || ano > 2025) {
                System.out.println("Por favor, insira um ano válido.");
                return;
            }

            List<Autor> autores = livroService.listarAutoresVivosNoAno(ano);

            if (autores.isEmpty()) {
                System.out.println("\nNenhum autor cadastrado estava vivo em " + ano + ".");
            } else {
                System.out.println("\n✅ Autores vivos em " + ano + " (" + autores.size() + " encontrados):");
                autores.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Por favor, insira um número de ano válido.");
            scanner.nextLine();
        }
    }

    // Opção 5: Filtra livros por idioma
    private void listarLivrosPorIdioma(Scanner scanner) {
        System.out.println("""
                
                Idiomas disponíveis para busca:
                  en  - Inglês
                  pt  - Português
                  es  - Espanhol
                  fr  - Francês
                  de  - Alemão
                  it  - Italiano
                """);
        System.out.print("Digite o código do idioma: ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (idioma.isBlank()) {
            System.out.println("O idioma não pode estar em branco.");
            return;
        }

        List<Livro> livros = livroService.listarLivrosPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("\nNenhum livro encontrado no idioma: " + idioma);
        } else {
            System.out.println("\n📖 Livros em \"" + idioma + "\" (" + livros.size() + " encontrado(s)):");
            livros.forEach(System.out::println);
        }
    }

    // Opção 6: Exibe contagem de livros por idioma
    private void contarLivrosPorIdioma(Scanner scanner) {
        System.out.println("""
                
                Idiomas disponíveis:
                  en  - Inglês
                  pt  - Português
                  es  - Espanhol
                  fr  - Francês
                  de  - Alemão
                  it  - Italiano
                """);
        System.out.print("Digite o código do idioma para ver a contagem: ");
        String idioma = scanner.nextLine().trim().toLowerCase();

        if (idioma.isBlank()) {
            System.out.println("O idioma não pode estar em branco.");
            return;
        }

        long contagem = livroService.contarLivrosPorIdioma(idioma);
        System.out.printf("\n📊 Total de livros em \"%s\" no catálogo: %d%n", idioma, contagem);
    }
}
