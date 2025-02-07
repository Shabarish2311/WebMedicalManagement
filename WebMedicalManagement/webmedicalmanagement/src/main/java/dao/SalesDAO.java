package dao;

import java.util.List;

import model.Sale;

public interface SalesDAO {
    boolean addSale(Sale sale);
    List<Sale> getAllSales();
    List<Sale> getSalesByMedicineId(int medicineId);
    double getTotalSalesAmount();
}
