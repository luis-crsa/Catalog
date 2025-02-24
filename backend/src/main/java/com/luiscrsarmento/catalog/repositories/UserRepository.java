package com.luiscrsarmento.catalog.repositories;

import com.luiscrsarmento.catalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
