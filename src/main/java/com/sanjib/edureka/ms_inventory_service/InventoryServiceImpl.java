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
	public Inventory addProductToInventory(String token, String usertype, Integer productId, Inventory inventoryView) {

		Inventory iDb = inventoryReository.findByProductId(productId);

		if (iDb == null) {
			iDb = new Inventory();
			iDb.setQuantity(inventoryView.getQuantity());
			iDb.setAddress(inventoryView.getAddress());
		} else {
			iDb.setQuantity(iDb.getQuantity() + inventoryView.getQuantity());
		}

		iDb.setProductId(productId);
		Long sellerId = tokenService.getUserIdFromToken(token);
		iDb.setSellerId(sellerId);

		String productName = tokenService.getProductNameFromId(token, usertype, productId);
		iDb.setProductName(productName);

		inventoryReository.save(iDb);

		return iDb;
	}

	@Override
	public Inventory reduceQuantityInventory(String token, String usertype, Integer productId,Integer quantity) {
		
		Inventory inventory = inventoryReository.findByProductId(productId);
		inventory.setQuantity(inventory.getQuantity()-quantity);
		inventoryReository.save(inventory);
		return inventory;
		
	}
	
	public Inventory getInventoryByProductId(Integer productId) {
		return inventoryReository.findByProductId(productId);
	}
	
}
