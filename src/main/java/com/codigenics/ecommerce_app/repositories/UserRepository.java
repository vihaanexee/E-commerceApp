package com.codigenics.ecommerce_app.repositories;

import com.codigenics.ecommerce_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUserName(String username);
}
