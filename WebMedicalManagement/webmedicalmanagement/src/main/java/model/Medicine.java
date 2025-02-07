package model;

public class Medicine
{
	private int medicineId;
	private String name;
	private String description;
	private double price;
	private int stock;

	//Constructors

	//Getters and Setters
	public int getMedicineId() {
		return medicineId;
	}
	public Medicine(int medicineId, String name, String description, double price, int stock) {
		super();
		this.medicineId = medicineId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
	public Medicine() {
	}


	public Medicine(String name, String description, double price, int stock) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
	public void setMedicineId(int medicineId) {
		this.medicineId = medicineId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
}
