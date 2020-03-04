package com.fynd.warehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fynd.warehouse.exception.NotFoundException;
import com.fynd.warehouse.model.Product;
import com.fynd.warehouse.service.ProductsService;

@RestController
@RequestMapping("warehouse_api/v1/")
public class ProductsController {
	
	@Autowired	
	private ProductsService productService;
	  
	@RequestMapping(value = "findslotbyId/{productId}", method = RequestMethod.GET)
	public HttpEntity<String> findSlotByProductID(@PathVariable long productId) {
		int slotID = productService.findSlotByProductID(productId);
		if (slotID > -1)
			return new ResponseEntity<String>(String.valueOf(slotID),HttpStatus.OK);
		else
			return new HttpEntity<String>("Product Not Found");

	}
	
	@RequestMapping(value = "findproductbycolor/", method = RequestMethod.GET)
	public HttpEntity<String> findProductByColor(@RequestParam String color) {
	    List<String> products = productService.findProductByColor(color);
		if (products.size() > 0)
			return new ResponseEntity<String>(String.join(", ",products), HttpStatus.OK); 
		else
			return new HttpEntity<String>("Not Found");
	}
	
	@RequestMapping(value = "findslotbycolor/", method = RequestMethod.GET)
	public HttpEntity<String> findSlotByColor(@RequestParam String color) {
	    List<String> products = productService.findSlotByColor(color);
		if (products.size() > 0)
			return new ResponseEntity<String>(String.join(", ",products), HttpStatus.OK); 
		else
			return new HttpEntity<String>("Not Found");
	}
	
	@RequestMapping(value = "product/", method = RequestMethod.DELETE)
	public HttpEntity<String> sellProductBySlotId(@RequestParam int slotId) {
	    boolean isSold = productService.deleteProductBySlotId(slotId);
		if (isSold)
			return new ResponseEntity<String>("slot Number "+ slotId +" is free", HttpStatus.OK);
		else
			return new HttpEntity<String>("slot Number "+ slotId + " Not Found");
	}
	
	
	@RequestMapping(value = "products/", method = RequestMethod.GET)
	public String list() {
	   Product[] products = productService.getAllProducts();
	   
	   StringBuilder delimitedOp = new StringBuilder();
	   delimitedOp.append("Slot No.\t");
	   delimitedOp.append("Product Code\t");
	   delimitedOp.append("Colour \t");
	   delimitedOp.append("\n");
	   
	   for(Product product: products) {
		   delimitedOp.append(product.getSlotID()+"\t\t");
		   delimitedOp.append(product.getProductID()+"\t");
		   delimitedOp.append(product.getColor()+"\t");
		   delimitedOp.append("\n");
	   }
	   return delimitedOp.toString();
		
	}
	
	@RequestMapping(value = "product/", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestParam Long productId,@RequestParam String color) {
	    int slotID = productService.addProductToWareHouse(productId,color);
		if(slotID > 0)
			return new ResponseEntity<String>("slot Number "+ slotID +" is allocated", HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("WareHouse is Full", HttpStatus.OK);
	}
	
	@RequestMapping(value = "warehouse/", method = RequestMethod.POST)
	public HttpEntity<String> create(@RequestParam int capacity) {
	    boolean isCreated = productService.createWarhouse(capacity);
		if(isCreated)
			return new ResponseEntity<String>("created a warhouse with "+ capacity +" slot",HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public HttpEntity<String> handleNotFoundException(NotFoundException ex){
		return new HttpEntity<String>(ex.getMessage());
		
	}

}
