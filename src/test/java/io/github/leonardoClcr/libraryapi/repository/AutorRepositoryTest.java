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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Marcelus");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1964,4,7));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("a5652e86-fb7a-43e8-ba27-bea35fcd3f46");

        Optional<Autor> possivelAutor =  repository.findById(id);

        if(possivelAutor.isPresent()){

            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor:");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960,1,30));

            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("a5652e86-fb7a-43e8-ba27-bea35fcd3f46");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("f85d6457-965e-46e1-a0b0-e81787d629b2");
        var marcelus = repository.findById(id).get();
        repository.delete(marcelus);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Thiago");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2003,11,19));

        Livro livro = new Livro();
        livro.setIsbn("2003-3002");
        livro.setPreco(BigDecimal.valueOf(320));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("A evolução da Humanidade");
        livro.setDataPublicacao(LocalDate.of(2008,1,5));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("1964-4691");
        livro2.setPreco(BigDecimal.valueOf(320));
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setTitulo("A vida de Santo");
        livro2.setDataPublicacao(LocalDate.of(2013,6,14));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void listarLivrosAutor(){
        UUID id = UUID.fromString("191adee0-87f6-404e-b243-74a752f6fb98");
        Autor autor = repository.findById(id).get();

        List<Livro> listaLivros = livroRepository.findByAutor(autor);
        autor.setLivros(listaLivros);

        autor.getLivros().forEach(System.out::println);
    }
}
