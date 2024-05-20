package id.ac.ui.cs.advprog.fashionpediapayment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopupCreationRequest implements PaymentServiceRequest {
    @JsonProperty("buyer_id")
    String buyerId;
    String method;
    @JsonProperty("bank_name")
    String bankName;
    @JsonProperty("account_number")
    String accountNumber;
    Long nominal;
    @JsonProperty("photo_proof")
    String photoProof = null;


    public boolean checkValidity() {
        return buyerId != null && method != null && bankName != null && accountNumber != null && nominal != null;
    }
}
