package com.fynd.warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.fynd.warehouse.model.Product;
import com.fynd.warehouse.repository.ProductRepository;
import com.fynd.warehouse.service.ProductsService;
import com.fynd.warehouse.service.ProductsServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class ProductsServiceTest {
	@Mock
	private ProductRepository productRep;
	
	@Spy
	List<Product> products;

	@InjectMocks
	private ProductsService prodService= new ProductsServiceImpl();


	@Test
	public void getAllProducts_returnproducts() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		given(productRep.getAllProducts()).willReturn(products);

		Product prod[] = prodService.getAllProducts();
		Product prods[] = new Product[] {new Product(123456789123l,"Green",1)};
		assertEquals(prods[0].getColor(), prod[0].getColor());	
		assertEquals(prods[0].getProductID(), prod[0].getProductID());
		assertEquals(prods[0].getSlotID(), prod[0].getSlotID());
		
	}
    
	@Test
	public void findSlotByProductID_returnproducts() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		given(productRep.getAllProducts()).willReturn(products);
		int slotId = prodService.findSlotByProductID(123456789123l);
		assertEquals(1, slotId);				

	}
	
	@Test
	public void findSlotByProductID_notmatchingproducts() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		given(productRep.getAllProducts()).willReturn(products);
		
		int slotId = prodService.findSlotByProductID(123456789121l);
		assertEquals(-1, slotId);				

	}
	
	@Test
	public void findProductByColor_matchingproducts() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		given(productRep.getAllProducts()).willReturn(products);
		
	   List<String> prods = prodService.findProductByColor("Green");
		assertEquals("123456789123", prods.get(0));				

	}
	
	@Test
	public void findProductByColor_notmatchingproducts() {
	
	   List<String> prods = prodService.findProductByColor("Red");
	   assertEquals(0, prods.size());				

	}
	
	
	@Test
	public void deleteProductBySlotId_matchingproducts() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		given(productRep.getAllProducts()).willReturn(products);
		
		given(productRep.deleteProduct(123456789123l)).willReturn(true);
		
	   boolean isDone = prodService.deleteProductBySlotId(1);
		assertTrue(isDone);				

	}
	
	@Test
	public void deleteProductBySlotId_notmatchingproducts() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		given(productRep.getAllProducts()).willReturn(products);
		
	   boolean isDone = prodService.deleteProductBySlotId(2);
		assertTrue(!isDone);				

	}
	
	@Test
	public void addProductToWareHouse_withinCapacity() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		products.add(new Product(123456789124l,"Green",-1));
		given(productRep.getAllProducts()).willReturn(products);
		
	   int slotId = prodService.addProductToWareHouse(123456789120l,"Blue");
	   assertEquals(2, slotId);					

	}
	
	@Test
	public void findSlotByColor_matchingproducts() {
		List<Product> products= new ArrayList<Product>();
		products.add(new Product(123456789123l,"Green",1));
		given(productRep.getAllProducts()).willReturn(products);
		
	   List<String> slots = prodService.findSlotByColor("Green");
	   assertEquals("1", slots.get(0));						

	}
	
	
}