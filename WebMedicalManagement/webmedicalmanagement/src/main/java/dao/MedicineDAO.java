package dao;

import java.util.List;

import model.Medicine;

public interface MedicineDAO {
	boolean addMedicine(Medicine medicine);
	boolean updateMedicine(Medicine medicine);
	boolean deleteMedicine(int medicineId);
	Medicine getMedicineById(int medicineId);
	List<Medicine> getAllMedicines();
	boolean updateStock(int medicineId, int quantityToRemove);
}
