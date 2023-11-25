package br.com.guifroes1984.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.guifroes1984.ecom.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
