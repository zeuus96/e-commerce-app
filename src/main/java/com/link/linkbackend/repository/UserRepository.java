package com.link.linkbackend.repository;

import com.link.linkbackend.domain.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(@NotEmpty String username);
    boolean existsByUsername(@NotEmpty String username);
}
