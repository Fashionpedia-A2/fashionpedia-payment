package id.ac.ui.cs.advprog.fashionpediapayment.dto.response;

import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;

import java.util.HashMap;
import java.util.List;


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
