package com.luiscrsarmento.catalog.repositories;

import com.luiscrsarmento.catalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
