package com.fynd.warehouse.model;

public final class Product {

	private  long productID;
	private  String color;
	private int slotID = -1;
	
	public void setProductID(long productID) {
		this.productID = productID;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public Product(long productID, String color, int slotID) {
		super();
		this.productID = productID;
		this.color = color;
		this.slotID = slotID;
	}

	public Product() {
		super();
	}


	public long getProductID() {
		return productID;
	}

	public String getColor() {
		return color;
	}
	
	public int getSlotID() {
		return slotID;
	}


	public void setSlotId(int i) {
		this.slotID = i;
	}


	@Override
	public String toString() {
		return "Product [productID=" + productID + ", color=" + color + ", slotID=" + slotID + "]";
	}
	
	

}
