package br.com.curso_udemy.product_api.modules.product.repository;

import br.com.curso_udemy.product_api.modules.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
