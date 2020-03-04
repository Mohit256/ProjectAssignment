package com.fynd.warehouse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fynd.warehouse.model.Product;
import com.fynd.warehouse.repository.ProductRepository;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	private List<Product> products;
	

	@Autowired
	private ProductRepository productRep;

	public int findSlotByProductID(long productID) {
		if(products.size()==0) {
			products = productRep.getAllProducts();
		}
		for (int i=0; i< products.size(); i++) {
			if(products.get(i)!=null && products.get(i).getProductID() == productID) {
			  return products.get(i).getSlotID();	
			}
		}
		return -1;
	}

	public List<String> findProductByColor(String color) {
		if(products.size()==0) {
			products = productRep.getAllProducts();
		}
		List<String> prodList = new ArrayList<>();
		for (int i=0; i< products.size(); i++) {
			if(products.get(i)!=null && products.get(i).getColor().equalsIgnoreCase(color)) {
			  prodList.add(String.valueOf(products.get(i).getProductID()));	
			}
		}
		return prodList;
	}

	public boolean deleteProductBySlotId(int slotID) {
		if(products.size()==0) {
			products = productRep.getAllProducts();
		}
		for (int i=0; i< products.size(); i++) {
			if(products.get(i)!=null && products.get(i).getSlotID() == slotID) {
			   productRep.deleteProduct(products.get(i).getProductID());
			   products.set(i,new Product());
			   return true;
			 }
			}
		return false;
	}

	public Product[] getAllProducts() {
		products = productRep.getAllProducts();
		
		Product prod[] = new Product[products.size()];
		return products.toArray(prod);
	}

	public int addProductToWareHouse(long productId,String color) {
		if(products.size()==0) {
			products = productRep.getAllProducts();
		}
		for (int i=0; i< products.size(); i++) {
			System.out.println(products.get(i).toString());
			if(products.get(i)!=null && products.get(i).getSlotID() == -1) {
				products.get(i).setProductID(productId);
				products.get(i).setColor(color);
				products.get(i).setSlotId(i+1);
				productRep.persistProduct(products.get(i));
				return i+1;
			}
		}
		return -1;
	}

	public boolean createWarhouse(int capacity) {
	   if(capacity>0) {
       products = new ArrayList<Product>(capacity);
       for (int i=0; i< capacity; i++) {
    	   products.add(i,new Product());
       }
       return true;
	   }
	   return false;
	}

	public List<String> findSlotByColor(String color) {
		if(products.size()==0) {
			products = productRep.getAllProducts();
		}
		List<String> prodList = new ArrayList<>();
		for (int i=0; i< products.size(); i++) {
			if(products.get(i)!=null && products.get(i).getColor().equalsIgnoreCase(color)) {
			  prodList.add(String.valueOf(products.get(i).getSlotID()));	
			}
		}
		return prodList;
	}

}
