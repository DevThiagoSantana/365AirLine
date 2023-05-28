package tech.devinhouse.apiaviacao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tech.devinhouse.apiaviacao.model.Classificacao;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(title = "PassageiroResposta", description = "Representa a resposta da API em relação a um Passageiro Cadrastrado")
public class PassageiroResponse {

    private Long cpf;

    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private Classificacao classificacao;

    private Integer milhas;

    private String eticket;

    private String assento;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHoraConfirmacao;

}
