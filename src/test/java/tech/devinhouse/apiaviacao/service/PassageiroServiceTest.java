package tech.devinhouse.apiaviacao.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.devinhouse.apiaviacao.exception.RegistroNaoEncontradoException;
import tech.devinhouse.apiaviacao.model.Classificacao;
import tech.devinhouse.apiaviacao.model.Passageiro;
import tech.devinhouse.apiaviacao.repository.PassageiroRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PassageiroServiceTest {
    @Mock
    private PassageiroRepository repo;


    @InjectMocks
    private PassageiroService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PassageiroService(repo);
    }

    @Test
    @DisplayName("Quando retorna a lista de passageiro")
    void consultar_DeveRetornarListaDePassageiros() {
        // Arrange
        List<Passageiro> passageiros = List.of(
                new Passageiro(11111111111L,"ze ninguem", LocalDate.of(2000,01,01), Classificacao.ASSOCIADO,100,null,null),
                new Passageiro(22222222222L,"Joao Alguem", LocalDate.of(1990,01,01), Classificacao.VIP,100,null,null)
        );
        when(repo.findAll()).thenReturn(passageiros);

        // Act
        List<Passageiro> resultado = service.consultar();

        // Assert
        assertEquals(passageiros, resultado);
        verify(repo).findAll();
    }


    @Test
    @DisplayName("Quando retorna um passageiro especifico")
    void consultar_DeveRetornarPassageiroExistente() {
        // Arrange
        Long cpf = 123456789L;
        Passageiro passageiro = new Passageiro(/* dados do passageiro */);
        when(repo.findByCpf(cpf)).thenReturn(Optional.of(passageiro));

        // Act
        Passageiro resultado = service.consultar(cpf);

        // Assert
        assertEquals(passageiro, resultado);
        verify(repo).findByCpf(cpf);
    }

    @Test
    @DisplayName("Quando retorna erro de não encontrado passageiro")
    void consultar_DeveLancarRegistroNaoEncontradoException_QuandoPassageiroNaoExistir() {
        // Arrange
        Long cpf = 123456789L;
        when(repo.findByCpf(cpf)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                RegistroNaoEncontradoException.class,
                () -> service.consultar(cpf)
        );
        verify(repo).findByCpf(cpf);
    }

    @Test
    @DisplayName("Quando retorna a lista de assento")
    void consultarAssentos_DeveRetornarListaDeAssentos() {
        // Arrange
        List<String> assentosEsperados = List.of(
                "1A", "1B", "1C", "1D", "1E", "1F",
                "2A", "2B", "2C", "2D", "2E", "2F",
                "3A", "3B", "3C", "3D", "3E", "3F",
                "4A", "4B", "4C", "4D", "4E", "4F",
                "5A", "5B", "5C", "5D", "5E", "5F",
                "6A", "6B", "6C", "6D", "6E", "6F",
                "7A", "7B", "7C", "7D", "7E", "7F",
                "8A", "8B", "8C", "8D", "8E", "8F",
                "9A", "9B", "9C", "9D", "9E", "9F"
        );

        // Act
        List<String> assentosResultantes = service.consultarAssentos();

        // Assert
        assertEquals(assentosEsperados, assentosResultantes);
    }

}