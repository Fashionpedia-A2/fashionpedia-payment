package id.ac.ui.cs.advprog.fashionpediapayment.repository;

import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TopupRepository extends JpaRepository<Topup, String> {
}
