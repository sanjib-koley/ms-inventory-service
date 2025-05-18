package com.sanjib.edureka.ms_inventory_service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryReository extends JpaRepository<Inventory, Integer> {
	
}
