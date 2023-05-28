package tech.devinhouse.apiaviacao.controller;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tech.devinhouse.apiaviacao.dto.CheckInRequest;
import tech.devinhouse.apiaviacao.dto.CheckInResponse;
import tech.devinhouse.apiaviacao.dto.PassageiroCompactResponse;
import tech.devinhouse.apiaviacao.dto.PassageiroResponse;
import tech.devinhouse.apiaviacao.model.Passageiro;
import tech.devinhouse.apiaviacao.service.CheckInService;
import tech.devinhouse.apiaviacao.service.PassageiroService;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PassageiroControllerTest {


        @InjectMocks
        private PassageiroController passageiroController;

        @Mock
        private ModelMapper modelMapper;

        @Mock
        private PassageiroService passageiroService;

        @Mock
        private CheckInService checkInService;

        private Validator validator;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }

        @Test
        @DisplayName("Quando  returna Lista de passageiro")
        public void testListar() {
            // Mocking
            List<Passageiro> passageirosMock = new ArrayList<>();
            Passageiro passageiroMock = new Passageiro();
            passageirosMock.add(passageiroMock);

            List<PassageiroResponse> passageirosResponseMock = new ArrayList<>();
            PassageiroResponse passageiroResponseMock = new PassageiroResponse();
            passageirosResponseMock.add(passageiroResponseMock);

            when(passageiroService.consultar()).thenReturn(passageirosMock);
            when(modelMapper.map(passageiroMock, PassageiroResponse.class)).thenReturn(passageiroResponseMock);

            // Execução do teste
            ResponseEntity<List<PassageiroResponse>> responseEntity = passageiroController.listar();
            List<PassageiroResponse> passageirosResponse = responseEntity.getBody();

            // Verificação
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Assertions.assertEquals(passageirosResponseMock, passageirosResponse);
        }

        @Test
        @DisplayName("Quando  returna um passageiro especifico pelo cpf")
        public void testConsultarPeloCpf() {
            // Mocking
            Long cpfMock = 123456789L;
            Passageiro passageiroMock = new Passageiro();

            PassageiroCompactResponse passageiroCompactResponseMock = new PassageiroCompactResponse();

            when(passageiroService.consultar(cpfMock)).thenReturn(passageiroMock);
            when(modelMapper.map(passageiroMock, PassageiroCompactResponse.class)).thenReturn(passageiroCompactResponseMock);

            // Execução do teste
            ResponseEntity<PassageiroCompactResponse> responseEntity = passageiroController.consultarPeloCpf(cpfMock);
            PassageiroCompactResponse passageiroCompactResponse = responseEntity.getBody();

            // Verificação
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(passageiroCompactResponseMock, passageiroCompactResponse);
        }

        @Test
        @DisplayName("Quando  returna o checkin de um passageiro")
        public void testConfirmacao() {
            // Mocking
            CheckInRequest checkInRequestMock = new CheckInRequest(11111111111L,"1A",true);
            Passageiro passageiroMock = new Passageiro();
            CheckInResponse checkInResponseMock = new CheckInResponse();

            when(modelMapper.map(checkInRequestMock, Passageiro.class)).thenReturn(passageiroMock);
            when(checkInService.checkin(passageiroMock)).thenReturn(passageiroMock);
            when(modelMapper.map(passageiroMock, CheckInResponse.class)).thenReturn(checkInResponseMock);

            // Execução do teste
            ResponseEntity<CheckInResponse> responseEntity = passageiroController.confirmacao(checkInRequestMock);
            CheckInResponse checkInResponse = responseEntity.getBody();

            // Verificação
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(checkInResponseMock, checkInResponse);
        }
    }