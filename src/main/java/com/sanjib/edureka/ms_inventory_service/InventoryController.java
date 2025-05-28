package com.sanjib.edureka.ms_inventory_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
			@Valid @RequestBody Inventory inventoryView) {

		if (tokenService.validateToken(token) && "seller".equalsIgnoreCase(usertype)) {
			Inventory inventoryCreated = inventoryService.addProductToInventory(token,usertype, productId,inventoryView);
			tokenService.addProductQuantity(token, usertype, productId, inventoryView.getQuantity());
			return new ResponseEntity<Inventory>(inventoryCreated, HttpStatus.CREATED);
		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}

	}
	
	@PostMapping("/inventory/reduce/{productId}/{quantity}")
	public ResponseEntity<?> reduceProductQuantity(@PathVariable("productId") Integer productId,
			@RequestHeader("Authorization") String token, @RequestHeader("Usertype") String usertype,
			@PathVariable("quantity") Integer quantity) {

		if (tokenService.validateToken(token) && "customer".equalsIgnoreCase(usertype)) {
			inventoryService.reduceQuantityInventory(token, usertype, productId, quantity);
			tokenService.reduceProductQuantity(token, usertype, productId, quantity);
			return ResponseEntity.status(200).body("Inventory-Quantity-Reduced");
		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}

	}
	
	@GetMapping("/inventory/retrieve/{productId}")
	public ResponseEntity<?> retrieveInventory(@PathVariable("productId") Integer productId,
			@RequestHeader("Authorization") String token, @RequestHeader("Usertype") String usertype) {

		if (tokenService.validateToken(token) && "seller".equalsIgnoreCase(usertype)) {
			Inventory inventory = 
					inventoryService.getInventoryByProductId(productId);
			return new ResponseEntity<Inventory>(inventory, HttpStatus.OK);
		} else {
			return ResponseEntity.status(401).body("Invalid Details");
		}

	}
}
