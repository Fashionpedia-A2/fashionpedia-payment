package id.ac.ui.cs.advprog.fashionpediapayment.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Entity
@Table(name = "topup")
public class Topup {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "topup_id")
    private UUID topupId;

    @Column(name = "buyer_id", nullable = false)
    private String buyerId;

    @Setter
    @Column(name = "buyer_name")
    private String buyerName;

    @Setter
    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number",
            columnDefinition = "char(16) NOT NULL CHECK (account_number ~ '^\\d+') ")
    private String accountNumber;

    @Setter
    @Column(name = "photo_proof")
    private String photoProof;

    @Setter
    @Column(name = "approval", nullable = false)
    private String approval;

    // Constructors
    public Topup() {
    }

    public Topup(String buyerId, String method, String bankName, String accountNumber) {
        this.buyerId = buyerId;
        this.method = method;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }
    public Topup(String buyerId, String method, String bankName, String accountNumber, String photoProof) {
        this(buyerId, method, bankName, accountNumber);
        this.photoProof = photoProof;
    }
}
