package br.com.wise.commerce.product.gateway.database.repositories;

import br.com.wise.commerce.product.gateway.database.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
    boolean existsBySku(String sku);
    Optional<ProductEntity> findBySku(String sku);
}
