package com.fynd.warehouse.service;

import java.util.List;

import com.fynd.warehouse.model.Product;

public interface ProductsService {

	Product[] getAllProducts();

	int findSlotByProductID(long l);

	List<String> findProductByColor(String string);

	boolean deleteProductBySlotId(int i);

	int addProductToWareHouse(long l, String string);

	List<String> findSlotByColor(String string);

	boolean createWarhouse(int i);

}
