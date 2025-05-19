package com.sanjib.edureka.ms_inventory_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
    TokenService tokenService;

	@PostMapping("/inventory/add/{productId}")
	public ResponseEntity<?> addProductToCatalogHandler(@PathVariable("productId") Integer productId,
			@RequestHeader("Authorization") String token, @RequestHeader("Usertype") String usertype,
			@Valid @RequestBody Inventory inventory) {

		if (tokenService.validateToken(token) && "seller".equalsIgnoreCase(usertype)) {
			inventory.setProductId(productId);
			Inventory inventoryCreated = inventoryService.addProductToInventory(token,usertype, inventory);
			tokenService.addProductQuantity(token, usertype, productId, inventory.getQuantity());
			return new ResponseEntity<Inventory>(inventoryCreated, HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}

	}
}
