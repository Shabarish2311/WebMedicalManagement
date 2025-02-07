package model;

import java.sql.Timestamp;

public class Sale {
    private int sale_id;
    private int medicine_id;
    private int quantity;
    private double total_price;
    private Timestamp sale_date;
	public int getSale_id() {
		return sale_id;
	}
	public void setSale_id(int sale_id) {
		this.sale_id = sale_id;
	}
	public int getMedicine_id() {
		return medicine_id;
	}
	public void setMedicine_id(int medicine_id) {
		this.medicine_id = medicine_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
	public Timestamp getSale_date() {
		return sale_date;
	}
	public void setSale_date(Timestamp date) {
		this.sale_date = date;
	}

    // Getters and Setters

}
