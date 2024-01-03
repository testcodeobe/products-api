package com.codeobe.integration;


import org.json.JSONObject;

class SimpleProduct {
	
	private int id;
    private String title;
    private double price;
    private String description;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    public SimpleProduct(JSONObject jsonProduct) {
        this.id = jsonProduct.getInt("id");
        this.title = jsonProduct.getString("title");
        this.price = jsonProduct.getDouble("price");
        this.description = jsonProduct.getString("description");
    }
    
    public static String getSimpleProducts(String json) {
       
        JSONObject jsonProduct = new JSONObject(json);
        SimpleProduct sp = new SimpleProduct(jsonProduct);
        return sp.toString();
    }
}