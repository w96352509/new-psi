package com.study.springmvc.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;

import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.study.springmvc.entity.OrderItem;
import com.study.springmvc.entity.view.Inventory;
import com.study.springmvc.repository.ProductRepository;

@Component
public class InventoryValidator implements Validator {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return OrderItem.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		OrderItem orderItem = (OrderItem)target;
		if(orderItem.getAmount() == null || orderItem.getAmount() <= 0) {
			errors.rejectValue("amount", "order_item.amount.required");
			
		} else {
			Inventory inventory = productRepository.findInventoryById(orderItem.getProduct().getId());
			int remaining = 0;
			try {
				// amount1 : 進貨數量
				// amount2 : 銷售數量
				if(inventory.getAmount2() == null) { // 是否有無銷售數量
					// 若無銷售數量，則庫存數量 = 進貨數量
					remaining = inventory.getAmount1();
				} else {
					remaining = inventory.getAmount1() - inventory.getAmount2();
				}
				if(orderItem.getAmount() > remaining) {
					errors.rejectValue("amount", "inventory.amount.insufficient", "目前庫存數量不足（庫存：" + remaining + "）");
				}
			} catch (Exception e) {
				e.printStackTrace();
				errors.rejectValue("amount", "inventory.amount.error", "此商品無進貨資料無法計算庫存");
			}
		}
	}

}