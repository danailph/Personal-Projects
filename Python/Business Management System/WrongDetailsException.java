// Custom exception class used for input validation

public class WrongDetailsException extends Exception{
    public WrongDetailsException(String message){
        super(message);
    }
} 