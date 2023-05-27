package tech.devinhouse.apiaviacao.exception;

public class MalaNaoDespachadaExpection extends RuntimeException{

    public MalaNaoDespachadaExpection( ) {
        super("Para CheckIn nesse Assento necessario despachar mala!");
    }
}
