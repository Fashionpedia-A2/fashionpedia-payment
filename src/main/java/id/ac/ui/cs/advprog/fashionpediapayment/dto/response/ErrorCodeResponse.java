package id.ac.ui.cs.advprog.fashionpediapayment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorCodeResponse implements PaymentServiceResponse {
    @JsonProperty("error_code")
    int errorCode;
}
