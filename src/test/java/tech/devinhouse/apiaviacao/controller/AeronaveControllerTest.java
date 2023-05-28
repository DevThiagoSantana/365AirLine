package tech.devinhouse.apiaviacao.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tech.devinhouse.apiaviacao.service.PassageiroService;

import java.util.List;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AeronaveControllerTest {

    @InjectMocks
    private AeronaveController aeronaveController;

    @Mock
    private PassageiroService passageiroService;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsultaAssentos() {
        // Mocking
        List<String> assentosMock = List.of("1A", "1B");
        when(passageiroService.consultarAssentos()).thenReturn(assentosMock);

        // Execução do teste
        ResponseEntity<List<String>> responseEntity = aeronaveController.consultaAssentos();
        List<String> assentos = responseEntity.getBody();

        // Verificação
        Assertions.assertEquals(assentosMock, assentos);
    }
}
