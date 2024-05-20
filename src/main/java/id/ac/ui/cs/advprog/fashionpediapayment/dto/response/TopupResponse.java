package id.ac.ui.cs.advprog.fashionpediapayment.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;

import java.util.HashMap;
import java.util.List;


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TopupResponse
        extends HashMap<String, Topup>
        implements PaymentServiceResponse {

    public TopupResponse (Topup topup) {
        this.put(topup.getTopupId(), topup);
    }

    public TopupResponse (List<Topup> topupList) {
        for (Topup topup : topupList) {
            this.put(topup.getTopupId(), topup);
        }
    }
}
