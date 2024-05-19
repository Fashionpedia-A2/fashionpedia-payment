package id.ac.ui.cs.advprog.fashionpediapayment.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter @Getter
@Entity
@Table(name = "topup")
public class Topup {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "topup_id")
    private String topupId;
    @Column(name = "buyer_id", nullable = false)
    private String buyerId;
    @Column(name = "buyer_name")
    private String buyerName;
    @Column(name = "date", nullable = false)
    private Timestamp date = new Timestamp(System.currentTimeMillis());
    @Column(name = "method", nullable = false)
    private String method;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "account_number",
            columnDefinition = "char(16) NOT NULL CHECK (account_number ~ '^\\d+') ")
    private String accountNumber;
    @Column(name = "photo_proof")
    private String photoProof;
    @Column(name = "approval", nullable = false)
    private String approval = "PENDING";
    @Column(name = "nominal", nullable = false)
    private long nominal;

    // Constructors
    public Topup() {
    }

    public Topup(String buyerId, String method, String bankName, String accountNumber, long nominal) {
        this.buyerId = buyerId;
        this.method = method;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.nominal = nominal;
    }
    public Topup(String buyerId, String method, String bankName, String accountNumber, long nominal, String photoProof) {
        this(buyerId, method, bankName, accountNumber, nominal);
        this.photoProof = photoProof;
    }

    @Override
    public String toString() {
        return "Topup {\n\t" +
                "topupId = " + topupId + ",\n\t" +
                "buyerId = " + buyerId + ",\n\t" +
                "buyerName = " + buyerName + ",\n\t" +
                "date = " + date.toString() + ",\n\t" +
                "method = " + method + ",\n\t" +
                "bankName = " + bankName + ",\n\t" +
                "accountNumber = " + accountNumber + ",\n\t" +
                "photoProof = " + photoProof + ",\n\t" +
                "approval = " + approval + ",\n\t" +
                "nominal = " + nominal + "\n" +
            "}";
    }
}
