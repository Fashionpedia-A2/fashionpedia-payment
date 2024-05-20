package id.ac.ui.cs.advprog.fashionpediapayment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopupSearchRequest implements PaymentServiceRequest {
    @JsonProperty("buyer_id")
    String buyerId;

    String approval;

    @JsonProperty("after_date")
    String afterDate;


    public boolean checkValidity() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // check date
        try {
            LocalDateTime.parse(afterDate, dateTimeFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
