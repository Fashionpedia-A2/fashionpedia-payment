package id.ac.ui.cs.advprog.fashionpediapayment.repository;

import id.ac.ui.cs.advprog.fashionpediapayment.model.Topup;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Repository
public interface TopupRepository extends JpaRepository<Topup, String> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM Topup t \
            WHERE (:buyer_id is null or t.buyer_id = :buyer_id) \
            and (:approval is null or t.approval = :approval) \
            and (t.date >= to_timestamp(:date))""")
    List<Topup> findAll(
            @Param("buyer_id") String buyerId,
            @Param("approval") String approval,
            @Param("date") double date
    );
}