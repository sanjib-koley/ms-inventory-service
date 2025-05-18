package com.sanjib.edureka.ms_inventory_service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="inventory")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inventory_id", nullable=false)
	private Integer inventoryId;
	
	
	@Column(name = "product_id", nullable=false)
	private Integer productId;
	

	@Column(name = "product_name", nullable=false)
	private String productName;
	
	
	@Column(name = "seller_id", nullable=false)
	private Long sellerId;
	

	@NotNull
	@Size(min = 3, max = 100, message = "Address size should be between 3-100")
	@Column(name = "address", nullable = false, length = 100)
	private String address;
	
	@NotNull
	@Min(value = 0)
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

}
