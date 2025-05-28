package com.sanjib.edureka.ms_inventory_service;

public interface InventoryService {

	public Inventory addProductToInventory(String token, String usertype,Integer productId,Inventory inventoryView);
	
	public Inventory reduceQuantityInventory(String token, String usertype, Integer productId,Integer quantity);
	
	public Inventory getInventoryByProductId(Integer productId);

}
