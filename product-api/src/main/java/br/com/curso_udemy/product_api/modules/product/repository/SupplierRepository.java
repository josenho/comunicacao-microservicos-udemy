package br.com.curso_udemy.product_api.modules.product.repository;

import br.com.curso_udemy.product_api.modules.product.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
