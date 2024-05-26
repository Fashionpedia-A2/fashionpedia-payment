package id.ac.ui.cs.advprog.fashionpediapayment.auth.repository;

import id.ac.ui.cs.advprog.fashionpediapayment.auth.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByEmail(String email);
    //Optional<UserProfile> findByUserName(String username);
}