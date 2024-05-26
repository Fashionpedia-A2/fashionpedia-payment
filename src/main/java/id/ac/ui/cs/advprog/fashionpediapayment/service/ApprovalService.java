package id.ac.ui.cs.advprog.fashionpediapayment.service;

import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import id.ac.ui.cs.advprog.fashionpediapayment.repository.TopupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ApprovalService {

    @Autowired
    private TopupRepository topupRepository;

    public Topup approveTopup(String topupId) {
        Optional<Topup> optionalTopup = topupRepository.findById(topupId);
        if (optionalTopup.isPresent()) {
            Topup topup = optionalTopup.get();
            topup.setApproval("APPROVED");
            return topupRepository.save(topup);
        } else {
            throw new RuntimeException("Topup not found");
        }
    }

    public Topup rejectTopup(String topupId) {
        Optional<Topup> optionalTopup = topupRepository.findById(topupId);
        if (optionalTopup.isPresent()) {
            Topup topup = optionalTopup.get();
            topup.setApproval("REJECTED");
            return topupRepository.save(topup);
        } else {
            throw new RuntimeException("Topup not found");
        }
    }
}
