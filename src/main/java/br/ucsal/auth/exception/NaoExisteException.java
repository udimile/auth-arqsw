package br.ucsal.auth.exception;

public class NaoExisteException extends RuntimeException {
    public NaoExisteException(String message) {
        super(message);
    }
}
