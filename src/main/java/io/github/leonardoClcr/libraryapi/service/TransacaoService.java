package io.github.leonardoClcr.libraryapi.service;

import io.github.leonardoClcr.libraryapi.model.Autor;
import io.github.leonardoClcr.libraryapi.model.GeneroLivro;
import io.github.leonardoClcr.libraryapi.model.Livro;
import io.github.leonardoClcr.libraryapi.repository.AutorRepository;
import io.github.leonardoClcr.libraryapi.repository.LivroRepository;
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
     public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("9ee49cdb-ba42-4407-b101-d0c1694a45fd")).orElse(null);
        livro.setDataPublicacao(LocalDate.of(1990,01,01));
    }

    @Transactional
    public void executar(){
        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Eduardo");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2004, 4, 2));

        autorRepository.save(autor);

        // salva o livro
        Livro livro = new Livro();
        livro.setIsbn("1997-4002");
        livro.setPreco(BigDecimal.valueOf(120));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Naruto");
        livro.setDataPublicacao(LocalDate.of(2023, 1, 14));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Eduardo")) {
            throw new RuntimeException("Rollback");
        }
    }
}
