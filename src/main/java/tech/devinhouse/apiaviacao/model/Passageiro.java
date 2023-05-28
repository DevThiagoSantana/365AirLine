package tech.devinhouse.apiaviacao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PASSAGEIRO")
public class Passageiro {

    @Id
    private Long cpf;

    private String nome;

    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private Classificacao classificacao;

    private Integer milhas;

    private String eticket;

    private String assento;

    private Boolean malasDespachadas;

    private LocalDateTime dataHoraConfirmacao;

    private Boolean fezCheckIn = false;


    public Passageiro(Long cpf, String nome, LocalDate dataNascimento, Enum classificacao,
                      Integer milhas, String eticket, String assento){
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.classificacao = Classificacao.valueOf("ASSOCIADO");
        this.milhas = 100;
        this.eticket = null;
        this.assento = null;
        this.fezCheckIn = false;
    }
}
