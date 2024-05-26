package id.ac.ui.cs.advprog.fashionpediapayment.auth.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
public class UserProfile {

    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    private String cartId;

    @PrePersist
    public void prePersist() {
        if (this.cartId == null) {
            this.cartId = UUID.randomUUID().toString();
        }
    }

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String address;

    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    @Column(columnDefinition = "TEXT")
    private String about;


}
