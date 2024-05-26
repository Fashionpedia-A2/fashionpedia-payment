package id.ac.ui.cs.advprog.fashionpediapayment.exceptions;


public class TopupException extends PaymentServiceException {
    public enum TopupExceptionType {
        WrongOrEmptyFields, // 2800
        TopupIdDoesNotExist, // 2820
        TopupAlreadyCancelled, // 2821
        TopupCannotBeCancelled, // 2822
    }

    private static int typeToErrorCode(TopupExceptionType type){
        return switch (type) {
            case WrongOrEmptyFields-> 2800;
            case TopupIdDoesNotExist -> 2820;
            case TopupAlreadyCancelled -> 2821;
            case TopupCannotBeCancelled -> 2822;
        };
    }

    public TopupException(TopupExceptionType type){
        super(typeToErrorCode(type));
    }
}

