package id.ac.ui.cs.advprog.fashionpediapayment.controller;

import id.ac.ui.cs.advprog.fashionpediapayment.dto.response.PaymentServiceResponse;
import id.ac.ui.cs.advprog.fashionpediapayment.dto.response.TopupResponse;
import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import id.ac.ui.cs.advprog.fashionpediapayment.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/top-up")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping("/{id}/approve")
    public ResponseEntity<PaymentServiceResponse> approveTopup(@PathVariable String id) {
        Topup approvedTopup = approvalService.approveTopup(id);
        return ResponseEntity.ok(new TopupResponse(approvedTopup));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<PaymentServiceResponse> rejectTopup(@PathVariable String id) {
        Topup rejectedTopup = approvalService.rejectTopup(id);
        return ResponseEntity.ok(new TopupResponse(rejectedTopup));
    }
}
