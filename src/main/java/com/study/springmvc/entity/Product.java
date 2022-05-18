package com.study.springmvc.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Size(min = 0 , max = 50 , message = "product.name.size")
	private String name;
	
	@Column
	@NotNull(message = "{product.cost.notnull}")
	@Range(min = 10, max = 1000, message = "{product.cost.range}")
	private Integer cost;
	
	@Column
	@NotNull(message = "{product.price.notnull}")
	@Range(min = 10, max = 1000, message = "{product.price.range}")
	private Integer price;
	
	@Column
	private String remark;
	
	@OneToMany(mappedBy = "product")
	@OrderBy("id ASC")
	private Set<PurchaseItem> purchaseItems = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "product")
	@OrderBy("id ASC")
	private Set<OrderItem> orderItemrs  = new LinkedHashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Set<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(Set<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public Set<OrderItem> getOrderItemrs() {
		return orderItemrs;
	}

	public void setOrderItemrs(Set<OrderItem> orderItemrs) {
		this.orderItemrs = orderItemrs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
