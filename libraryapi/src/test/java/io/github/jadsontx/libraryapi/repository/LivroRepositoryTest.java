package io.github.jadsontx.libraryapi.repository;

import io.github.jadsontx.libraryapi.model.Autor;
import io.github.jadsontx.libraryapi.model.GeneroLivro;
import io.github.jadsontx.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("2020-2020");
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Spring Boot");
        livro.setDataPublicacao(LocalDate.of(2025, 8, 10));

        Autor autor = autorRepository
                .findById(UUID.fromString("88a046de-98c1-48d4-8ed5-90830573c6c6"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("1010-1010");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Cloud");
        livro.setDataPublicacao(LocalDate.of(2020, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Teixeira");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2001, 05, 11));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("1010-1010");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Cloud");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new  Autor();
        autor.setNome("Martins");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2001, 05, 11));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("ad903c76-d4de-4c69-aae0-75ea500d4dd0");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("88a046de-98c1-48d4-8ed5-90830573c6c6");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar() {
        UUID id = UUID.fromString("de43f9f1-2f78-4ae9-bbaf-994014b49c04");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        repository.delete(livroParaAtualizar);
    }

    @Test
    void deletarCascade() {
        UUID id = UUID.fromString("8a440514-f1a3-4657-b284-98ee30c08150");
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID id = UUID.fromString("22ba62fa-1ce4-472d-b495-32b38b866c21");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> lista = repository.findByTitulo("Java");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest() {
        List<Livro> lista = repository.findByIsbn("1010-1010");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest() {
        var preco = BigDecimal.valueOf(100.00);
        var tituloPesquisa = "Java";
        List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa, preco);
    }

    @Test
    void listarLivroComQueryJPQL() {
        var resultado = repository.listarTodos();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros() {
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros() {
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros() {
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest() {
        var resultado = repository.findByGenero(GeneroLivro.CIENCIA, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamTest() {
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.CIENCIA, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest() {
        repository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacao() {
        repository.updateDataPublicacao(LocalDate.of(2000, 1, 1));
    }
}
