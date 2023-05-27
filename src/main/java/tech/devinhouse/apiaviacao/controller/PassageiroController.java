package tech.devinhouse.apiaviacao.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.apiaviacao.dto.CheckInRequest;
import tech.devinhouse.apiaviacao.dto.CheckInResponse;
import tech.devinhouse.apiaviacao.dto.PassageiroCompactResponse;
import tech.devinhouse.apiaviacao.dto.PassageiroResponse;
import tech.devinhouse.apiaviacao.model.Passageiro;
import tech.devinhouse.apiaviacao.service.CheckInService;
import tech.devinhouse.apiaviacao.service.PassageiroService;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/passageiros")
public class PassageiroController {

    private ModelMapper mapper;

    private PassageiroService service;

    private CheckInService serviceCheckIN;

    @GetMapping
    public ResponseEntity<List<PassageiroResponse>>listar(){
        List<Passageiro> passageiros = service.consultar();
        List<PassageiroResponse>resp = new ArrayList<>();
        for(Passageiro passageiro:passageiros){
            resp.add(mapper.map(passageiro,PassageiroResponse.class));
        }
        return ResponseEntity.ok(resp);

    }

    @GetMapping("{cpf}")
    public ResponseEntity<PassageiroCompactResponse>consultarPeloCpf(@PathVariable("cpf") Long cpf){
            Passageiro passageiro = service.consultar(cpf);
            PassageiroCompactResponse resp = mapper.map(passageiro, PassageiroCompactResponse.class);
            return ResponseEntity.ok(resp);


    }
    @PostMapping("/confirmacao")
    public ResponseEntity<CheckInResponse>confirmacao(@RequestBody @Valid CheckInRequest request){
        Passageiro passageiro =mapper.map(request,Passageiro.class);
        passageiro= serviceCheckIN.checkin(passageiro);
        CheckInResponse resp =mapper.map(passageiro ,CheckInResponse.class);
        return ResponseEntity.ok(resp);
    }


}
