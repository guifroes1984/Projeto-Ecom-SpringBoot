package br.com.guifroes1984.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.guifroes1984.ecom.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
