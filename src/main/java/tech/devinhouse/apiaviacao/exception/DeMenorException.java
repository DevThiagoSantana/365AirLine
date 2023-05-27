package tech.devinhouse.apiaviacao.exception;

public class DeMenorException extends RuntimeException{
    public DeMenorException( Long cpf) {
        super("Passageiro  com identificador " + cpf + " não pode ocupar assento emergencial, pois é de menor!");
    }
}
