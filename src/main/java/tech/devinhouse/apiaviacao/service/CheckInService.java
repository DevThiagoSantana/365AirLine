package tech.devinhouse.apiaviacao.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.devinhouse.apiaviacao.exception.*;
import tech.devinhouse.apiaviacao.model.Passageiro;
import tech.devinhouse.apiaviacao.repository.PassageiroRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CheckInService {

    private PassageiroRepository repo;

    private PassageiroService service;

    public Passageiro checkin(Passageiro passageiro){
        Optional<Passageiro> passageiroOpt = repo.findByCpf(passageiro.getCpf());
        List<String>assentos = service.consultarAssentos();
        if(passageiroOpt.isEmpty()){
            throw new RegistroNaoEncontradoException("Passageiro",passageiro.getCpf());
        }

        var passageiroCheckIn = passageiroOpt.get();
        if (passageiroCheckIn.getFezCheckIn()!=null && passageiroCheckIn.getFezCheckIn()){
            throw new CheckInException(passageiroCheckIn.getCpf());
        }

       boolean existAssento = assentos.contains(passageiro.getAssento());
        if(!existAssento){
            throw new RegistroNaoEncontradoException("Assento",passageiro.getAssento());
        }
        boolean occupiedAssento = repo.existsByAssento(passageiro.getAssento());
        if(occupiedAssento){
            throw  new AssentoOcupadoException(passageiro.getAssento());
        }
        boolean emergencialAssento = (passageiro.getAssento().contains("4")||passageiro.getAssento().contains("5"));
        if (emergencialAssento){
            Integer idade = Math.toIntExact(ChronoUnit.YEARS.between(passageiroCheckIn.getDataNascimento(), LocalDate.now()));
            if(idade<18){
                throw new DeMenorException(passageiroCheckIn.getCpf());
            }
            if(!passageiro.getMalasDespachadas()){
                throw new MalaNaoDespachadaExpection();
            }
        }
        String eticket = UUID.randomUUID().toString();

        Integer newMilhas =passageiroCheckIn.getMilhas();

        switch (passageiroCheckIn.getClassificacao()){
            case VIP:
                newMilhas= newMilhas + 100;
                break;

            case OURO:
                newMilhas= newMilhas + 80;
                break;

            case PRATA:
                newMilhas= newMilhas + 50;
                break;

            case BRONZE:
                newMilhas= newMilhas + 30;
                break;

            case ASSOCIADO:
                newMilhas= newMilhas + 10;
                break;
        }

        passageiroCheckIn.setAssento(passageiro.getAssento());
        passageiroCheckIn.setEticket(eticket);
        passageiroCheckIn.setMilhas(newMilhas);
        passageiroCheckIn.setMalasDespachadas(passageiro.getMalasDespachadas());
        passageiroCheckIn.setDataHoraConfirmacao(LocalDateTime.now());
        passageiroCheckIn.setFezCheckIn(true);

        repo.save(passageiroCheckIn);

        return passageiroCheckIn;
    }


}
