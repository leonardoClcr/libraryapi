package io.github.leonardoClcr.libraryapi.repository;

import io.github.leonardoClcr.libraryapi.model.Autor;
import io.github.leonardoClcr.libraryapi.model.GeneroLivro;
import io.github.leonardoClcr.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("1998-8991");
        livro.setPreco(BigDecimal.valueOf(250));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Hobbit");
        livro.setDataPublicacao(LocalDate.of(1940,8,23));

        Autor autor = autorRepository
                .findById(UUID.fromString("03fc4a90-84f0-4b97-98a3-a9d06048a6cc"))
                .orElse(null);

        livro.setAutor(new Autor());

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("1998-8991");
        livro.setPreco(BigDecimal.valueOf(250));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Hobbit");
        livro.setDataPublicacao(LocalDate.of(1940,8,23));

        Autor autor = new Autor();
        autor.setNome("Leonardo");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1998,1, 30));

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("1998-8991");
        livro.setPreco(BigDecimal.valueOf(250));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Harry Potter");
        livro.setDataPublicacao(LocalDate.of(1980,11,9));

        Autor autor = new Autor();
        autor.setNome("Marisa");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1964,3, 8));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorLivro(){
        UUID id = UUID.fromString("bb651d19-c6f5-4531-9c05-91bf883b1201");
        var livroParaAtualizar  = repository
                .findById(id)
                .orElse(null);

        UUID idAutor = UUID.fromString("7af21fdb-f288-497e-84de-7f8da966bc58");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletarLivroTest(){
        UUID idLivroDeletar = UUID.fromString("bb651d19-c6f5-4531-9c05-91bf883b1201");
        repository.deleteById(idLivroDeletar);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("9ee49cdb-ba42-4407-b101-d0c1694a45fd");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }
}