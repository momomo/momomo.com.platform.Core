package momomo.com.exceptions;

public class $MapsCloneException extends $RuntimeException {
    public $MapsCloneException(ReflectiveOperationException e) {
        super(e);
    }
    
    public $MapsCloneException(String message) {
        super(message);
    }
}
