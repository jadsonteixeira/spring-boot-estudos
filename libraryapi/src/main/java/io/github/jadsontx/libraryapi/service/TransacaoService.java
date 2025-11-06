package io.github.jadsontx.libraryapi.service;

import io.github.jadsontx.libraryapi.model.Autor;
import io.github.jadsontx.libraryapi.model.GeneroLivro;
import io.github.jadsontx.libraryapi.model.Livro;
import io.github.jadsontx.libraryapi.repository.AutorRepository;
import io.github.jadsontx.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;



    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository
                .findById(UUID.fromString("dd901b1c-df0c-4fb8-9b0e-9092551f1198"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));
    }

    @Transactional
    public void executar() {
        // salva o autor
        Autor autor = new  Autor();
        autor.setNome("Jadson Tx");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2001, 5, 11));

        autorRepository.save(autor);

        // salva o livro
        Livro livro = new  Livro();
        livro.setIsbn("5050-5050");
        livro.setPreco(BigDecimal.valueOf(500));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Spring Cloud");
        livro.setDataPublicacao(LocalDate.of(2025, 10, 10));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Jadson")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
