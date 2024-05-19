package id.ac.ui.cs.advprog.fashionpediapayment.service;

import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import id.ac.ui.cs.advprog.fashionpediapayment.repository.TopupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class TopupService {
    @Autowired
    private TopupRepository topupRepository;

    final static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Topup createTopup (
            String buyerId, String method, String bankName, String accountNumber, long nominal, String photo_proof) {
        Topup topup;
        if (photo_proof == null) {
            topup = new Topup(buyerId, method, bankName, accountNumber, nominal);
        }
        else {
            topup = new Topup(buyerId, method, bankName, accountNumber, nominal, photo_proof);
        }
        topupRepository.save(topup);
        return topup;
    }

    public List<Topup> getTopups(String buyerId, String approval, String after_date) {
        double seconds = 0;
        if (after_date != null) {
            LocalDateTime localDateTime;
            try {
                localDateTime = LocalDateTime.parse(after_date, dateTimeFormat);
            } catch (DateTimeParseException e) {
                return null;
            }
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            System.out.println(timestamp);
            seconds = timestamp.getTime() / 1000.0;
        }
        return topupRepository.findAll(
                buyerId, approval, seconds
        );
    }

    public Topup cancelTopup(String topupId) {
        Topup topup = topupRepository.findById(topupId).orElse(null);
        if (topup != null) {
            topup.setApproval("CANCELLED");
            topup = topupRepository.save(topup);
        }
        return topup;
    }

    public Topup deleteTopup(String topupId) {
        Topup topup = topupRepository.findById(topupId).orElse(null);
        if (topup != null) {
            topupRepository.delete(topup);
        }
        return topup;
    }
}
