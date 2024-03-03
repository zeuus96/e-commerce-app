package com.link.linkbackend.repository;

import com.link.linkbackend.domain.Authority;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,String> {
    Optional<Authority> findByName(@NotEmpty String name);
}
