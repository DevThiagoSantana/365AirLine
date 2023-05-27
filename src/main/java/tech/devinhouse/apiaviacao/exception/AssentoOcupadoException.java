package tech.devinhouse.apiaviacao.exception;

public class AssentoOcupadoException extends RuntimeException{
    public AssentoOcupadoException( String assento) {
        super("Assento  com identificador " + assento + " já está Ocupado!");
    }
}
