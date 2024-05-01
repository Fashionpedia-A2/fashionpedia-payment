package id.ac.ui.cs.advprog.fashionpediapayment.service;

import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import id.ac.ui.cs.advprog.fashionpediapayment.repository.TopupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopupService {
    @Autowired
    private TopupRepository topupRepository;

    public Topup createTopup (
            String buyerId, String method, String bankName, String accountNumber, String photo_proof) {
        return null;
    }

    public List<Topup> getAllTopupsFromBuyerId(String buyerId) {
        return null;
    }

    public Topup cancelTopup(String topupId) {
        return null;
    }

    public Topup deleteTopup(String topupId) {
        return null;
    }
}
