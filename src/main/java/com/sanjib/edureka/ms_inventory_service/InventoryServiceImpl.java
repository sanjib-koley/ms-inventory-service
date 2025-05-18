package com.sanjib.edureka.ms_inventory_service ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryReository inventoryReository;
	
	
	@Autowired
    TokenService tokenService;

	@Override
	public Inventory addProductToInventory(String token, String usertype, Inventory inventory) {
		
		Long sellerId = tokenService.getUserIdFromToken(token);
		inventory.setSellerId(sellerId);
		
		String productName = tokenService.getProductNameFromId(token,usertype,inventory.getProductId());
		inventory.setProductName(productName);
		
		inventoryReository.save(inventory);

		return inventory;
	}
}
