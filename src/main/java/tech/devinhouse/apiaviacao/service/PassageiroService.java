package tech.devinhouse.apiaviacao.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.devinhouse.apiaviacao.exception.RegistroNaoEncontradoException;
import tech.devinhouse.apiaviacao.model.Passageiro;
import tech.devinhouse.apiaviacao.repository.PassageiroRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PassageiroService {

    private PassageiroRepository repo;

    public List<Passageiro>consultar(){
        return repo.findAll();
    }
    public Passageiro consultar(Long cpf){
        Optional<Passageiro> passageiroOpt = repo.findByCpf(cpf);
        if(passageiroOpt.isEmpty())
            throw  new RegistroNaoEncontradoException("Passageiro",cpf);
        Passageiro passageiro = passageiroOpt.get();
        return passageiro;
    }

    public List<String> consultarAssentos(){
        List<String> assentos = new ArrayList<>();

        for(int fileira = 1;fileira <= 9; fileira++){
            for(char poltrona ='A'; poltrona <= 'F'; poltrona++){
                String assento = fileira + String.valueOf(poltrona);
                assentos.add(assento);
            }
        }
        return assentos;




    }


}
