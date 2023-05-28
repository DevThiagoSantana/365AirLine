package tech.devinhouse.apiaviacao.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.devinhouse.apiaviacao.exception.*;
import tech.devinhouse.apiaviacao.model.Classificacao;
import tech.devinhouse.apiaviacao.model.Passageiro;
import tech.devinhouse.apiaviacao.repository.PassageiroRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckInServiceTest {

    @Mock
    private PassageiroRepository repo;


    @Mock
    private PassageiroService service;

    @InjectMocks
    private CheckInService checkInService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("quando o checkin funciona")
    void chickIn_ok() {
        Passageiro passageiro = new Passageiro();
        passageiro.setCpf(12345678900L);
        passageiro.setAssento("1A");
        passageiro.setMalasDespachadas(true);

        Passageiro passageiroTest = new Passageiro();
        passageiroTest.setCpf(12345678900L);
        passageiroTest.setDataNascimento(LocalDate.of(2000, 1, 1));
        passageiroTest.setFezCheckIn(false);
        passageiroTest.setClassificacao(Classificacao.VIP);
        passageiroTest.setMilhas(100);

        List<String> assentosDisponiveis = new ArrayList<>();
        assentosDisponiveis.add("1A");
        assentosDisponiveis.add("1B");
        assentosDisponiveis.add("2A");

        when(repo.findByCpf(passageiro.getCpf())).thenReturn(Optional.of(passageiroTest));
        when(service.consultarAssentos()).thenReturn(assentosDisponiveis);
        when(repo.existsByAssento("1A")).thenReturn(false);
        when(repo.save(passageiroTest)).thenReturn(passageiroTest);

        Passageiro passageiroCheckIn = checkInService.checkin(passageiro);

        // Assert
        Assertions.assertEquals("1A", passageiroCheckIn.getAssento());
        Assertions.assertNotNull(passageiroCheckIn.getEticket());
        Assertions.assertEquals(200, passageiroCheckIn.getMilhas());
        Assertions.assertTrue(passageiroCheckIn.getMalasDespachadas());
        Assertions.assertNotNull(passageiroCheckIn.getDataHoraConfirmacao());
        Assertions.assertTrue(passageiroCheckIn.getFezCheckIn());

        verify(repo).save(passageiroTest);
    }



    @Test
    @DisplayName("Quando não encontra o cpf")
    void checkin_cpf_nao_encontrado() {

        Passageiro passageiro = new Passageiro();
        passageiro.setCpf(10000000000L);
        passageiro.setAssento("1A");
        passageiro.setMalasDespachadas(true);


        when(repo.findByCpf(passageiro.getCpf())).thenReturn(Optional.empty());

        Assertions.assertThrows(RegistroNaoEncontradoException.class,()-> checkInService.checkin(passageiro));
        verify(repo, times(1)).findByCpf(passageiro.getCpf());

    }
    @Test
    @DisplayName("Quando o passageiro ja fez checkin")
    void checkin_ja_fez_chekin() {
        Passageiro passageiro = new Passageiro();
        passageiro.setCpf(12345678900L);
        passageiro.setAssento("1A");
        passageiro.setMalasDespachadas(true);

        Passageiro passageiroTest = new Passageiro();
        passageiroTest.setCpf(12345678900L);
        passageiroTest.setDataNascimento(LocalDate.of(2000, 1, 1));
        passageiroTest.setFezCheckIn(false);
        passageiroTest.setClassificacao(Classificacao.VIP);
        passageiroTest.setMilhas(100);
        passageiroTest.setFezCheckIn(true);

        List<String> assentosDisponiveis = new ArrayList<>();
        assentosDisponiveis.add("1A");
        assentosDisponiveis.add("1B");
        assentosDisponiveis.add("2A");


        when(repo.findByCpf(passageiro.getCpf())).thenReturn(Optional.of(passageiroTest));
        when(service.consultarAssentos()).thenReturn(assentosDisponiveis);
        Assertions.assertThrows(CheckInException.class,()-> checkInService.checkin(passageiro));
        verify(repo, times(1)).findByCpf(passageiroTest.getCpf());

    }

    @Test
    @DisplayName("Quando o Assento não existe")
    void checkin_assento_nao_existe() {
        Passageiro passageiro = new Passageiro();
        passageiro.setCpf(12345678900L);
        passageiro.setAssento("2B");
        passageiro.setMalasDespachadas(true);

        Passageiro passageiroTest = new Passageiro();
        passageiroTest.setCpf(12345678900L);
        passageiroTest.setDataNascimento(LocalDate.of(2000, 1, 1));
        passageiroTest.setFezCheckIn(false);
        passageiroTest.setClassificacao(Classificacao.VIP);
        passageiroTest.setMilhas(100);
        passageiroTest.setFezCheckIn(false);

        List<String> assentosDisponiveis = new ArrayList<>();
        assentosDisponiveis.add("1A");
        assentosDisponiveis.add("1B");
        assentosDisponiveis.add("2A");


        when(repo.findByCpf(passageiro.getCpf())).thenReturn(Optional.of(passageiroTest));
        when(service.consultarAssentos()).thenReturn(assentosDisponiveis);
        Assertions.assertThrows(AssentoNaoEncontradoException.class,()-> checkInService.checkin(passageiro));
        verify(repo, times(1)).findByCpf(passageiroTest.getCpf());

    }

    @Test
    @DisplayName("Quando o Assento já esta ocupado")
    void checkin_assento_ocupado() {
        Passageiro passageiro = new Passageiro();
        passageiro.setCpf(12345678900L);
        passageiro.setAssento("1B");
        passageiro.setMalasDespachadas(true);

        Passageiro passageiroTest = new Passageiro();
        passageiroTest.setCpf(12345678900L);
        passageiroTest.setDataNascimento(LocalDate.of(2000, 1, 1));
        passageiroTest.setFezCheckIn(false);
        passageiroTest.setClassificacao(Classificacao.VIP);
        passageiroTest.setMilhas(100);
        passageiroTest.setFezCheckIn(false);

        List<String> assentosDisponiveis = new ArrayList<>();
        assentosDisponiveis.add("1A");
        assentosDisponiveis.add("1B");
        assentosDisponiveis.add("2A");


        when(repo.findByCpf(passageiro.getCpf())).thenReturn(Optional.of(passageiroTest));
        when(service.consultarAssentos()).thenReturn(assentosDisponiveis);
        when(repo.existsByAssento(passageiro.getAssento())).thenReturn(true);

        Assertions.assertThrows(AssentoOcupadoException.class,()-> checkInService.checkin(passageiro));
        verify(repo, times(1)).findByCpf(passageiroTest.getCpf());

    }

    @Test
    @DisplayName("Quando o Assento é emergencial e é menor")
    void checkin_assento_emergencial_deMenor() {
        Passageiro passageiro = new Passageiro();
        passageiro.setCpf(12345678900L);
        passageiro.setAssento("4A");
        passageiro.setMalasDespachadas(true);

        Passageiro passageiroTest = new Passageiro();
        passageiroTest.setCpf(12345678900L);
        passageiroTest.setDataNascimento(LocalDate.of(2007, 1, 1));
        passageiroTest.setFezCheckIn(false);
        passageiroTest.setClassificacao(Classificacao.VIP);
        passageiroTest.setMilhas(100);
        passageiroTest.setFezCheckIn(false);

        List<String> assentosDisponiveis = new ArrayList<>();
        assentosDisponiveis.add("1A");
        assentosDisponiveis.add("1B");
        assentosDisponiveis.add("2A");
        assentosDisponiveis.add("4A");


        when(repo.findByCpf(passageiro.getCpf())).thenReturn(Optional.of(passageiroTest));
        when(service.consultarAssentos()).thenReturn(assentosDisponiveis);
        when(repo.existsByAssento(passageiro.getAssento())).thenReturn(false);

        Assertions.assertThrows(DeMenorException.class,()-> checkInService.checkin(passageiro));
        verify(repo, times(1)).findByCpf(passageiroTest.getCpf());

    }

    @Test
    @DisplayName("Quando o Assento é emergencial e a mala não foi despachada")
    void checkin_assento_emergencial_malaNaoDespachada() {
        Passageiro passageiro = new Passageiro();
        passageiro.setCpf(12345678900L);
        passageiro.setAssento("4A");
        passageiro.setMalasDespachadas(false);

        Passageiro passageiroTest = new Passageiro();
        passageiroTest.setCpf(12345678900L);
        passageiroTest.setDataNascimento(LocalDate.of(2000, 1, 1));
        passageiroTest.setFezCheckIn(false);
        passageiroTest.setClassificacao(Classificacao.VIP);
        passageiroTest.setMilhas(100);
        passageiroTest.setFezCheckIn(false);

        List<String> assentosDisponiveis = new ArrayList<>();
        assentosDisponiveis.add("1A");
        assentosDisponiveis.add("1B");
        assentosDisponiveis.add("2A");
        assentosDisponiveis.add("4A");


        when(repo.findByCpf(passageiro.getCpf())).thenReturn(Optional.of(passageiroTest));
        when(service.consultarAssentos()).thenReturn(assentosDisponiveis);
        when(repo.existsByAssento(passageiro.getAssento())).thenReturn(false);

        Assertions.assertThrows(MalaNaoDespachadaExpection.class,()-> checkInService.checkin(passageiro));
        verify(repo, times(1)).findByCpf(passageiroTest.getCpf());

    }

}