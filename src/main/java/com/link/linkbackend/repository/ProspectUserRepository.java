package com.link.linkbackend.repository;

import com.link.linkbackend.domain.ProspectUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProspectUserRepository extends JpaRepository<ProspectUser, Long> {
    Optional<ProspectUser> findByEmail(String email);
}
