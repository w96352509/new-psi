package com.study.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.study.springmvc.entity.Customer;
import com.study.springmvc.entity.Product;
import com.study.springmvc.entity.view.Inventory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(nativeQuery = true,
			   value = "SELECT p.id, p.name, p.cost, p.price, \n" + 
			   		"(SELECT sum(amount) FROM purchase_items WHERE product_id = p.id limit 1) as amount1, \n" + 
			   		"(SELECT sum(amount) FROM order_items WHERE product_id = p.id limit 1) as amount2 \n" + 
			   		"FROM products p")
		List<Inventory> queryInventories();
		
		@Query(nativeQuery = true,
				   value = "SELECT p.id, p.name, p.cost, p.price, \n" + 
				   		"(SELECT sum(amount) FROM purchase_items WHERE product_id = p.id limit 1) as amount1, \n" + 
				   		"(SELECT sum(amount) FROM order_items WHERE product_id = p.id limit 1) as amount2 \n" + 
				   		"FROM products p WHERE p.id=:id")
		Inventory findInventoryById(Long id);
	
		
		@Query(nativeQuery = true ,
			   value ="SELECT p.id, p.name, p.cost, p.price,\r\n"
			   		 + "(SELECT sum(amount) FROM purchase_items WHERE product_id = p.id limit 1) as amount1,\r\n"
			   		 + "(SELECT sum(amount) FROM order_items WHERE product_id = p.id limit 1) as amount2 ,\r\n"
			   		 + "(SELECT max(amount2) ) as max\r\n"
			   		 + "FROM products p  order by max desc limit 1"
			   )
		Inventory findMaxamount();
}
