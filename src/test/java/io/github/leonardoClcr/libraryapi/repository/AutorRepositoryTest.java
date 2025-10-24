package io.github.leonardoClcr.libraryapi.repository;

import io.github.leonardoClcr.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Leonardo");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1998,1,30));

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
}
