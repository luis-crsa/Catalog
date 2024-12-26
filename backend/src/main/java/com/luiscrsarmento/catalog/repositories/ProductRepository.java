package com.luiscrsarmento.catalog.repositories;

import com.luiscrsarmento.catalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
