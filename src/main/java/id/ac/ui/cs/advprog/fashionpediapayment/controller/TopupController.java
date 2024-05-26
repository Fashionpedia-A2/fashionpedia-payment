package id.ac.ui.cs.advprog.fashionpediapayment.controller;

import id.ac.ui.cs.advprog.fashionpediapayment.dto.request.TopupCreationRequest;
import id.ac.ui.cs.advprog.fashionpediapayment.dto.request.TopupSearchRequest;
import id.ac.ui.cs.advprog.fashionpediapayment.dto.response.ErrorCodeResponse;
import id.ac.ui.cs.advprog.fashionpediapayment.dto.response.PaymentServiceResponse;
import id.ac.ui.cs.advprog.fashionpediapayment.dto.response.TopupResponse;
import id.ac.ui.cs.advprog.fashionpediapayment.exceptions.PaymentServiceException;
import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import id.ac.ui.cs.advprog.fashionpediapayment.service.TopupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/top-up")
public class TopupController {
    @Autowired
    private TopupService topupService;

    @PostMapping("/")
    public ResponseEntity<PaymentServiceResponse> createTopup(
        @RequestBody TopupCreationRequest request
    ) {
        if (! request.checkValidity()) {
            return ResponseEntity.badRequest().body(new ErrorCodeResponse(2800));
        }

        Topup created;
        try {
            created = topupService.createTopup(
                    request.getBuyerId(),
                    request.getMethod(),
                    request.getBankName(),
                    request.getAccountNumber(),
                    request.getNominal(),
                    request.getPhotoProof()
            );
        } catch (PaymentServiceException e) {
            return ResponseEntity.badRequest().body(e.getErrorCodeResponse());
        }

        return ResponseEntity.ok().body(new TopupResponse(created));
    }

    @GetMapping("/")
    public ResponseEntity<PaymentServiceResponse> getTopup(
        @RequestBody TopupSearchRequest request
    ) {
        if (! request.checkValidity()) {
            return ResponseEntity.badRequest().body(new ErrorCodeResponse(2800));
        }

        List<Topup> found;
        try {
            found = topupService.getTopups(
                    request.getBuyerId(),
                    request.getApproval(),
                    request.getAfterDate()
            );
        } catch (PaymentServiceException e) {
            return ResponseEntity.badRequest().body(e.getErrorCodeResponse());
        }

        return ResponseEntity.ok().body(new TopupResponse(found));
    }

    @GetMapping("/batal")
    public ResponseEntity<PaymentServiceResponse> cancelTopup(
            @RequestParam String topupId
    ) {
        Topup cancelled;

        try {
            cancelled = topupService.cancelTopup(topupId);
        } catch (PaymentServiceException e) {
            return ResponseEntity.badRequest().body(e.getErrorCodeResponse());
        }

        return ResponseEntity.ok().body(new TopupResponse(cancelled));
    }

    @DeleteMapping("/")
    public ResponseEntity<PaymentServiceResponse> deleteTopup(
            @RequestParam String topupId
    ) {
        Topup deleted;

        try {
            deleted = topupService.deleteTopup(topupId);
        } catch (PaymentServiceException e) {
            return ResponseEntity.badRequest().body(e.getErrorCodeResponse());
        }

        return ResponseEntity.ok().body(new TopupResponse(deleted));
    }
}
