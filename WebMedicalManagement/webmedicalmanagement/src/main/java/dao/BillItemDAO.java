package dao;

import java.util.List;

import model.BillItem;

public interface BillItemDAO {
    boolean addBillItem(BillItem billItem);
    List<BillItem> getBillItemsByBillId(int billId);
    public List<BillItem> getBillItems();
}
