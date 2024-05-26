package id.ac.ui.cs.advprog.fashionpediapayment.exceptions;


public class TopupException extends PaymentServiceException {
    public enum TopupExceptionType {
        TopupIdDoesNotExist,
        TopupAlreadyCancelled,
        TopupCannotBeCancelled,
    }

    private static int typeToErrorCode(TopupExceptionType type){
        return switch (type) {
            case TopupIdDoesNotExist -> 2820;
            case TopupAlreadyCancelled -> 2821;
            case TopupCannotBeCancelled -> 2822;
        };
    }

    public TopupException(TopupExceptionType type){
        super(typeToErrorCode(type));
    }
}

