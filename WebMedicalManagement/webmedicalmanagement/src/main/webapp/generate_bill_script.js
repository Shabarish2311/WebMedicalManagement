document.addEventListener('DOMContentLoaded', function() {
    const addMedicineBtn = document.getElementById('addMedicine');
    const medicineSelect = document.getElementById('medicineId');
    const billTable = document.getElementById('billTable').getElementsByTagName('tbody')[0];
    const totalAmountSpan = document.getElementById('totalAmount');

    addMedicineBtn.addEventListener('click', function() {
        const selectedOption = medicineSelect.options[medicineSelect.selectedIndex];
        if (selectedOption.value) {
            const medicineId = selectedOption.value;
            const medicineName = selectedOption.text.split(' - ')[0];
            const price = parseFloat(selectedOption.dataset.price);
            addMedicineToTable(medicineId, medicineName, price);
        }
    });

    function addMedicineToTable(medicineId, medicineName, price) {
        const row = billTable.insertRow();
        row.innerHTML = `
            <td>${medicineName}</td>
            <td>₹${price.toFixed(2)}</td>
            <td>
                <button type="button" class="quantity-btn minus">-</button>
                <span class="quantity">1</span>
                <button type="button" class="quantity-btn plus">+</button>
                <input type="hidden" name="medicineId" value="${medicineId}">
                <input type="hidden" name="quantity" value="1">
            </td>
            <td class="item-total">₹${price.toFixed(2)}</td>
            <td>
                <button type="button" class="remove-btn">Remove</button>
            </td>
        `;

        updateTotalAmount();

        row.querySelector('.minus').addEventListener('click', function() {
            updateQuantity(row, -1);
        });

        row.querySelector('.plus').addEventListener('click', function() {
            updateQuantity(row, 1);
        });

        row.querySelector('.remove-btn').addEventListener('click', function() {
            billTable.removeChild(row);
            updateTotalAmount();
        });
    }

    function updateQuantity(row, change) {
        const quantitySpan = row.querySelector('.quantity');
        const quantityInput = row.querySelector('input[name="quantity"]');
        const priceCell = row.cells[1];
        const totalCell = row.querySelector('.item-total');

        let quantity = parseInt(quantitySpan.textContent) + change;
        if (quantity > 0) {
            quantitySpan.textContent = quantity;
            quantityInput.value = quantity;

            const price = parseFloat(priceCell.textContent.replace('₹', ''));
            const total = price * quantity;
            totalCell.textContent = `₹${total.toFixed(2)}`;

            updateTotalAmount();
        } else if (quantity === 0) {
            billTable.removeChild(row);
            updateTotalAmount();
        }
    }

    function updateTotalAmount() {
        let total = 0;
        const totalCells = billTable.querySelectorAll('.item-total');
        totalCells.forEach(cell => {
            total += parseFloat(cell.textContent.replace('₹', ''));
        });
        totalAmountSpan.textContent = total.toFixed(2);
    }
});