package dao;

import java.util.List;

import model.Bill;
import model.BillItem;

public interface BillDAO {
    int createBill(Bill bill); // Returns generated Bill ID
    boolean addBillItem(BillItem billItem);
    List<Bill> getAllBills();
    Bill getBillById(int billId);
    List<BillItem> getBillItemsByBillId(int billId);
}
