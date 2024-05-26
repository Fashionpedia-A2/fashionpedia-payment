package id.ac.ui.cs.advprog.fashionpediapayment.service;

import id.ac.ui.cs.advprog.fashionpediapayment.builder.TopupBuilder;
import id.ac.ui.cs.advprog.fashionpediapayment.exceptions.PaymentServiceException;
import id.ac.ui.cs.advprog.fashionpediapayment.exceptions.TopupException;
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
import java.util.ArrayList;
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
            String buyerId, String method, String bankName, String accountNumber, long nominal, String photoProof)
            throws TopupException {
        TopupBuilder topupBuilder = new TopupBuilder()
                .setBuyerId(buyerId)
                .setMethod(method)
                .setBankName(bankName)
                .setAccountNumber(accountNumber)
                .setNominal(nominal);
        if (photoProof != null) {
            topupBuilder = topupBuilder.setPhotoProof(photoProof);
        }

        Topup topup = topupBuilder.build();
        topupRepository.save(topup);
        return topup;
    }

    public List<Topup> getTopups(String buyerId, String approval, String afterDate)
            throws TopupException{
        double seconds = 0;
        if (afterDate != null) {
            LocalDateTime localDateTime;
            try {
                localDateTime = LocalDateTime.parse(afterDate, dateTimeFormat);
            } catch (DateTimeParseException e) {
                throw new TopupException(TopupException.TopupExceptionType.WrongOrEmptyFields);
            }
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            System.out.println(timestamp);
            seconds = timestamp.getTime() / 1000.0;
        }
        return topupRepository.findAll(
                buyerId, approval, seconds
        );
    }

    public Topup cancelTopup(String topupId)
            throws TopupException {
        Topup topup = topupRepository.findById(topupId).orElse(null);
        if (topup == null) {
            throw new TopupException(TopupException.TopupExceptionType.TopupIdDoesNotExist);
        }
        if (topup.getApproval().equals("CANCELLED")) {
            throw new TopupException(TopupException.TopupExceptionType.TopupAlreadyCancelled);
        }
        if (!topup.getApproval().equals("PENDING")) {
            throw new TopupException(TopupException.TopupExceptionType.TopupCannotBeCancelled);
        }
        topup.setApproval("CANCELLED");
        topup = topupRepository.save(topup);
        return topup;
    }

    public Topup deleteTopup(String topupId)
            throws TopupException{
        Topup topup = topupRepository.findById(topupId).orElse(null);
        if (topup == null) {
            throw new TopupException(TopupException.TopupExceptionType.TopupIdDoesNotExist);
        }

        topupRepository.delete(topup);
        return topup;
    }
}
