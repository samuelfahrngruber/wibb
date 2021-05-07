package at.wibb.server.business;

public class UnknownEntityException extends RuntimeException {

    public UnknownEntityException() {
        super();
    }
    
    public UnknownEntityException(String message) {
        super(message);
    }
    
}
