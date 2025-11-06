package io.github.jadsontx.libraryapi.repository;

import io.github.jadsontx.libraryapi.model.Autor;
import io.github.jadsontx.libraryapi.model.GeneroLivro;
import io.github.jadsontx.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest // contexto da aplicacao fica sendo como teste
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new  Autor();
        autor.setNome("Teixeira");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2001, 05, 10));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("a4202ef6-c9bc-4c3b-843e-3fc253f67017");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do Autor: ");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(2000, 1, 30));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println); // pega cada elemento e chama o sout
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("789c4469-4382-4fcb-9dad-bdfe058c6080");

        repository.deleteById(id);
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("54ef5bbf-3ae4-44fc-b4bb-00d12e8fa1ae");
        var jadson = repository.findById(id).get(); // so posso usar o get porque eu sei que o objeto existe no db

        repository.delete(jadson); // deleta a entidade completa
    }

    @Test
    void salvarAutorComLivrosTest() {
        Autor autor = new Autor();
        autor.setNome("Marcelo");
        autor.setNacionalidade("Polonês");
        autor.setDataNascimento(LocalDate.of(1990, 10, 10));

        Livro livro = new Livro();
        livro.setIsbn("11111-11111");
        livro.setPreco(BigDecimal.valueOf(50));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Mister");
        livro.setDataPublicacao(LocalDate.of(2020, 10,10));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("22222-2222");
        livro2.setPreco(BigDecimal.valueOf(50));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("Mister 2");
        livro2.setDataPublicacao(LocalDate.of(2020, 10,10));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutor() {
        var id = UUID.fromString("88a046de-98c1-48d4-8ed5-90830573c6c6");
        var autor = repository.findById(id).get();

        // buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
