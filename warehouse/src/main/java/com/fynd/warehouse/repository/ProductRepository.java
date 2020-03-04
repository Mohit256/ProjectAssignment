package com.fynd.warehouse.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fynd.warehouse.model.Product;

@Repository
public class ProductRepository {

	@Autowired
	NamedParameterJdbcTemplate template;

	public boolean deleteProduct(long productID) {
		Map<String, Long> map = new HashMap<>();
		map.put("id", productID);
		int updates = template.update("DELETE FROM PRODUCTS WHERE PRODUCT_ID = :id", map);
		if (updates > 0)
			return true;

		return false;

	}

	public boolean persistProduct(Product prod) {
		String query = "INSERT INTO PRODUCTS(PRODUCT_ID,COLOR,SLOT_ID) values (:productID, :color, :slotID)";

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("productID", prod.getProductID());
		map.addValue("color", prod.getColor());
		map.addValue("slotID", prod.getSlotID());

		int updates = template.update(query, map);

		if (updates > 0)
			return true;

		return false;
	}

	public List<Product> getAllProducts() {

		List<Product> productList = template.query("SELECT * FROM PRODUCTS ", new RowMapper<Product>() {

			@Override
			public Product mapRow(ResultSet rs, int arg1) throws SQLException {
				Product prod = new Product(rs.getLong(1), rs.getString(2), rs.getInt(3));
				return prod;
			}

		});
		return productList;
	}

}
