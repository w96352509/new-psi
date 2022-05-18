package com.study.springmvc.entity;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "purchases")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "{purchase.date.notnull}")
	private Date date = new Date();
	
	@ManyToOne
	@JoinColumn(name = "supplier_id")  // 外鍵
	private Supplier supplier;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")  // 外鍵
	private Employee employee;
	
	// 採購單細目
	@OneToMany(mappedBy = "purchase")
	@OrderBy("id ASC") // @OrderBy("id DESC")
	private Set<PurchaseItem> purchaseItems = new LinkedHashSet<>();
	
	// 計算採購單總價
	public Integer getTotal() {
		if(purchaseItems.size() == 0) {
			return 0;
		}
		return  purchaseItems.stream()
				.mapToInt(pi -> pi.getAmount() * pi.getProduct().getCost())
				.sum();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Set<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(Set<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}
	
	
}