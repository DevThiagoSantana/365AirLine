package tech.devinhouse.apiaviacao.exception;

public class CheckInException extends RuntimeException{

    public CheckInException( long cpf) {
        super("Passageiro com identificador " + cpf + " já fez o CheckIn!");
    }
}
