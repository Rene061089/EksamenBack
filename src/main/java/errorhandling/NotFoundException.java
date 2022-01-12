package errorhandling;


public class NotFoundException extends Exception {
    int code;

    public NotFoundException(int code, String message) {

        super(message);
        this.code=code;
    }

    public int getCode()
    {
        return code;
    }
}
