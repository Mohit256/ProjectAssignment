package com.fynd.warehouse;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fynd.warehouse.controller.ProductsController;
import com.fynd.warehouse.model.Product;
import com.fynd.warehouse.service.ProductsService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductsController.class)
public class ProductsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductsService productService;
	

	@Test
	public void getProducts_ShouldReturnProduct() throws Exception {

		given(productService.getAllProducts())
				.willReturn(new Product[] {new Product(123456987123l, "green",1)});

		mockMvc.perform(MockMvcRequestBuilders.get("/warehouse_api/v1/products/"))
				.andExpect(status().isOk());

	}
	
	@Test
	public void findSlotByProductID_ShouldReturnSlot() throws Exception {

		given(productService.findSlotByProductID(123456987123l))
				.willReturn(1);

		mockMvc.perform(MockMvcRequestBuilders.get("/warehouse_api/v1/findslotbyId/123456987123/"))
			.andExpect(status().isOk()).andExpect(content().string("1"));

	}

	
	@Test
	public void findSlotByProductID_NotFound() throws Exception {
		given(productService.findSlotByProductID(123456987121l))
				.willReturn(-1);

        mockMvc.perform(MockMvcRequestBuilders.get("/warehouse_api/v1/findslotbyId/123456987121/"))
			.andExpect(content().string("Product Not Found"));
		
	}
	
	@Test
	public void findProductByColor_ShouldReturnProductID() throws Exception {
  
		List<String> products= new ArrayList<>();
		products.add("123456987123");
		
		given(productService.findProductByColor("green"))
				.willReturn(products);

		mockMvc.perform(MockMvcRequestBuilders.get("/warehouse_api/v1/findproductbycolor/?color=green"))
			.andExpect(status().isOk()).andExpect(content().string("123456987123"));

	}

	@Test
	public void createWareHouse_ShouldReturnTrue() throws Exception {
  
		given(productService.createWarhouse(5))
				.willReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/warehouse_api/v1/warehouse/?capacity=5"))
			.andExpect(status().isCreated()).andExpect(content().string("created a warhouse with 5 slot"));

	}
	
	@Test
	public void createProduct_ShouldReturnSlot() throws Exception {
  
		given(productService.addProductToWareHouse(123456789123l,"red"))
				.willReturn(1);

		mockMvc.perform(MockMvcRequestBuilders.post("/warehouse_api/v1/product/?productId=123456789123&color=red"))
			.andExpect(status().isCreated()).andExpect(content().string("slot Number 1 is allocated"));
	}

}