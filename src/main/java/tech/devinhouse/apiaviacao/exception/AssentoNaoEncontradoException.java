package tech.devinhouse.apiaviacao.exception;

public class AssentoNaoEncontradoException extends RuntimeException{
    public AssentoNaoEncontradoException( String id) {
        super("o Assento com identificador " + id + " não existe!");
    }
}
