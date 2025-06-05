package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
