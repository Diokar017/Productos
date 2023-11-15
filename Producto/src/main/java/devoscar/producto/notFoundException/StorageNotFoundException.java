package productos.notFoundException;

public class StorageNotFoundException extends RuntimeException {
    public StorageNotFoundException(String message) {
        super(message);
    }

    public StorageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
