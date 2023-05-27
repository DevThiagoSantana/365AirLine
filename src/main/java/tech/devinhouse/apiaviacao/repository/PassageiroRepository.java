package tech.devinhouse.apiaviacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.devinhouse.apiaviacao.model.Passageiro;

import java.util.Optional;

public interface PassageiroRepository  extends JpaRepository<Passageiro,Long> {
    Optional<Passageiro> findByCpf (Long cpf);

    Boolean existsByAssento(String assent);
}
