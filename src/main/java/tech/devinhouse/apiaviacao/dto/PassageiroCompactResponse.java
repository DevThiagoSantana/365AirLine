package tech.devinhouse.apiaviacao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import tech.devinhouse.apiaviacao.model.Classificacao;

import java.time.LocalDate;

@Data
public class PassageiroCompactResponse {

    private Long cpf;

    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private Classificacao classificacao;

    private Integer milhas;
}
