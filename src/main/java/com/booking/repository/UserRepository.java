package com.booking.repository;


import com.booking.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Integer> {
    Optional<UserApp> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<UserApp> findByEmail(String email);
    Optional<UserApp> findById(Integer userId);
}
