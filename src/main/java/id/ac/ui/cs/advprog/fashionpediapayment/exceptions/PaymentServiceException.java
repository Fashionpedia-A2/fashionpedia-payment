package id.ac.ui.cs.advprog.fashionpediapayment.exceptions;

import id.ac.ui.cs.advprog.fashionpediapayment.dto.response.ErrorCodeResponse;
import id.ac.ui.cs.advprog.fashionpediapayment.dto.response.PaymentServiceResponse;
import lombok.Getter;

@Getter
public class PaymentServiceException extends Exception {
    int errorCode;

    public PaymentServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCodeResponse getErrorCodeResponse() {
        return new ErrorCodeResponse(errorCode);
    }

}
