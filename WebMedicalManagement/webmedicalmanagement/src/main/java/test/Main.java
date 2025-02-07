package test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import dao.BillDAO;
import dao.BillItemDAO;
import dao.MedicineDAO;
import dao.SalesDAO;
import dao.UserDAO;
import daoImpl.BillDAOImpl;
import daoImpl.BillItemDAOImpl;
import daoImpl.MedicineDAOImpl;
import daoImpl.SalesDAOImpl;
import daoImpl.UserDAOImpl;
import model.Bill;
import model.BillItem;
import model.Medicine;
import model.Sale;
import model.User;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the DAO implementation to test:");
        System.out.println("1. MedicineDAO");
        System.out.println("2. UserDAO");
        System.out.println("3. BillDAO");
        System.out.println("4. BillItemDAO");
        System.out.println("5. SalesDAO");
        System.out.print("Enter your choice (1-5): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                testMedicineDAO();
                break;
            case 2:
            	testUserDAO();
                break;
            case 3:
            	testBillDAO();
                break;
            case 4:
            	testBillItemDAO();
                break;
            case 5:
            	 testSalesDAO();
                break;
            default:
                System.out.println("Invalid choice!");
        }

        scanner.close();
    }

    private static void testMedicineDAO() {
        MedicineDAO medicineDAO = new MedicineDAOImpl();

        // Example: Add a new medicine
        Medicine medicine = new Medicine();
        medicine.setName("Paracetamol");
        medicine.setDescription("Pain reliever and fever reducer");
        medicine.setPrice(2.5);
        medicine.setStock(100);

        boolean result = medicineDAO.addMedicine(medicine);
        System.out.println("Medicine added: " + result);
    }


    private static void testUserDAO() {
        UserDAO userDAO = new UserDAOImpl();

        // Example: Add a new user
        User user = new User();
        user.setUsername("pharamacist2");
        user.setPassword("123");
        user.setRole("Pharmacist");

        int result = userDAO.addUser(user);
        System.out.println("User added: " + result);

        // Fetch user by username
        User fetchedUser = userDAO.getUserByUsername("admin");
        if (fetchedUser != null) {
            System.out.println("Fetched User: " + fetchedUser.getUsername());
        } else {
            System.out.println("User not found!");
        }
    }

    private static void testBillDAO() {
        BillDAO billDAO = new BillDAOImpl();

        // Example: Create a new bill
        Bill bill = new Bill();
        bill.setPharmacistId(1);
        bill.setTotalAmount(100.0);
        bill.setBillDate(new Timestamp(System.currentTimeMillis()));
        bill.setPdfPath("bill_1.pdf");

        int billId = billDAO.createBill(bill);
        System.out.println("Bill created with ID: " + billId);
    }

    private static void testBillItemDAO() {
        BillItemDAO billItemDAO = new BillItemDAOImpl();

        // Example: Add a new bill item
        BillItem billItem = new BillItem();
        billItem.setBillId(1);
        billItem.setMedicineId(1);
        billItem.setQuantity(2);
        billItem.setPricePerUnit(10.0);

        boolean result = billItemDAO.addBillItem(billItem);
        System.out.println("Bill item added: " + result);
    }

    private static void testSalesDAO() {
        SalesDAO salesDAO = new SalesDAOImpl();

        //Add a new sale
        Sale sale = new Sale();
        sale.setMedicine_id(1);
        sale.setQuantity(5);
        sale.setTotal_price(50.0);
        sale.setSale_date(new Timestamp(System.currentTimeMillis()));

        boolean result = salesDAO.addSale(sale);
        System.out.println("Sale added: " + result);

        // Get all sales
        List<Sale> sales = salesDAO.getAllSales();
        System.out.println("All Sales:");
        for (Sale s : sales) {
            System.out.println("Sale ID: " + s.getSale_id() + ", Total Amount: " + s.getTotal_price());
        }
    }
}
