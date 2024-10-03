package com.manisha.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manisha.ecommerce.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	//custom findBy<field name> feature of Spring
	List<Product> findByBrand(String brand);
	
	/*for search involving more than one features, we use JPQL - JPA Query Language
	 * p is Alias for Product and Product is the Class name not Table name
	 * In WHERE condition, we will check for the keyword in every field
	 */
	//method to return list of products matching some keyword
	@Query("SELECT p from Product p WHERE "+
            "LOWER(p.prodName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<Product> searchProducts(String keyword);
	
}
