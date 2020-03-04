package com.fynd.warehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WarehouseApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(WarehouseApplication.class, args);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Input: ");
		String command = br.readLine();
		
		while(!"exit".equalsIgnoreCase(command)) {
			String output = executeCommand(command);
			System.out.println("Output: ");
			System.out.println(output);
			System.out.println("Input: ");
			command = br.readLine();
		};
	}

	private static String executeCommand(String command) {
		String[] commandType = command.split(" ");
		String color,resourceUrl,output = "Input is Invalid";
		
		Long productId;
		ResponseEntity<String> response = null;
		RestTemplate restTemplate = new RestTemplate();
		switch(commandType[0])
		{
		case "warehouse":
			int capacity = Integer.valueOf(commandType[1]);
		    resourceUrl = "http://localhost:8080/warehouse_api/v1/warehouse/?capacity=" + capacity ;
			response = restTemplate.postForEntity(resourceUrl,null, String.class);
			return response.getBody();
		case "store":
        	productId = Long.valueOf(commandType[1]);
        	color = commandType[2];
        	resourceUrl = "http://localhost:8080/warehouse_api/v1/product/?productId=" + productId +"&color="+ color ;
			response = restTemplate.postForEntity(resourceUrl, null, String.class);
			return response.getBody();	
        case "sell":
        	int slotId = Integer.valueOf(commandType[1]);
        	resourceUrl = "http://localhost:8080/warehouse_api/v1/product/?slotId=" + slotId ;
            response =
        			restTemplate.exchange(resourceUrl, 
        							HttpMethod.DELETE, 
        						        null,String.class,slotId);
            return response.getBody();
        case "status":
        	resourceUrl = "http://localhost:8080/warehouse_api/v1/products/" ; 
        	response = restTemplate.getForEntity(resourceUrl, String.class);
			return response.getBody();
			
        case "product_codes_for_products_with_colour":
        	color = commandType[1];
        	resourceUrl = "http://localhost:8080/warehouse_api/v1/findproductbycolor/?color="+ color ;
        	response = restTemplate.getForEntity(resourceUrl, String.class);
			return response.getBody();
			
        case "slot_numbers_for_products_with_colour":
        	color = commandType[1];
        	resourceUrl = "http://localhost:8080/warehouse_api/v1/findslotbycolor/?color="+ color ;
        	response = restTemplate.getForEntity(resourceUrl, String.class);
			return response.getBody();

        case "slot_number_for_product_code":
        	productId = Long.valueOf(commandType[1]);
        	resourceUrl = "http://localhost:8080/warehouse_api/v1/findslotbyId/"+productId ;
        	response = restTemplate.getForEntity(resourceUrl, String.class);
			return response.getBody();

		}
		return output;
	}
	

}
