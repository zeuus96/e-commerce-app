package com.link.linkbackend.repository;

import com.link.linkbackend.domain.ProspectUser;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProspectUserRepository extends JpaRepository<ProspectUser, Long> {
    Optional<ProspectUser> findByEmail(@NotEmpty String email);
    Optional<ProspectUser> findByCin(@NotEmpty String cin);
}
