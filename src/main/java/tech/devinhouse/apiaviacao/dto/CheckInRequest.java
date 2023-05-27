package tech.devinhouse.apiaviacao.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckInRequest {

    @NotNull(message = "{campo.obrigatorio}")
    private Long cpf;

    @NotEmpty(message = "{campo.obrigatorio}")
    private String assento;

    @NotNull(message = "{campo.obrigatorio}")
    private Boolean malasDespachadas;

}
