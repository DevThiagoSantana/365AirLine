package tech.devinhouse.apiaviacao.controller;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.devinhouse.apiaviacao.service.PassageiroService;


import java.util.List;

@RequestMapping("/api/assentos")
@RestController
@AllArgsConstructor
public class AeronaveController {
    private PassageiroService service;

    @GetMapping
    public ResponseEntity<List<String>>consultaAssentos(){
        List<String>assentos = service.consultarAssentos();
        return ResponseEntity.ok(assentos);

    }


}
