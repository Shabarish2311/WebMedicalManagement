document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form")
  form.addEventListener("submit", (e) => {
    e.preventDefault()
    const startDate = document.getElementById("startDate").value
    const endDate = document.getElementById("endDate").value
    fetchSalesData(startDate, endDate)
  })
})

function fetchSalesData(startDate, endDate) {
  fetch(`SalesReportServlet?startDate=${startDate}&endDate=${endDate}`)
    .then((response) => response.json())
    .then((data) => {
      updateSummary(data)
      updateCharts(data)
      updateTable(data)
    })
    .catch((error) => console.error("Error:", error))
}

function updateSummary(data) {
  const totalSales = data.reduce((sum, sale) => sum + sale.total, 0)
  const totalItems = data.reduce((sum, sale) => sum + sale.quantity, 0)
  document.getElementById("totalSales").textContent = totalSales.toFixed(2)
  document.getElementById("totalItems").textContent = totalItems
}

function updateCharts(data) {
  // Sales over time chart
  const salesChart = new Chart(document.getElementById("salesChart"), {
    type: "line",
    data: {
      labels: data.map((sale) => sale.date),
      datasets: [
        {
          label: "Sales",
          data: data.map((sale) => sale.total),
          borderColor: "rgb(75, 192, 192)",
          tension: 0.1,
        },
      ],
    },
    options: {
      responsive: true,
      plugins: {
        title: {
          display: true,
          text: "Sales Over Time",
        },
      },
    },
  })

  // Top products chart
  const productSales = data.reduce((acc, sale) => {
    acc[sale.product] = (acc[sale.product] || 0) + sale.total
    return acc
  }, {})

  const topProducts = Object.entries(productSales)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 5)

  const topProductsChart = new Chart(document.getElementById("topProductsChart"), {
    type: "bar",
    data: {
      labels: topProducts.map((product) => product[0]),
      datasets: [
        {
          label: "Sales",
          data: topProducts.map((product) => product[1]),
          backgroundColor: "rgba(75, 192, 192, 0.6)",
        },
      ],
    },
    options: {
      responsive: true,
      plugins: {
        title: {
          display: true,
          text: "Top 5 Products by Sales",
        },
      },
    },
  })
}

function updateTable(data) {
  const tableBody = document.querySelector("#salesTable tbody")
  tableBody.innerHTML = ""
  data.forEach((sale) => {
    const row = tableBody.insertRow()
    row.insertCell(0).textContent = sale.date
    row.insertCell(1).textContent = sale.product
    row.insertCell(2).textContent = sale.quantity
    row.insertCell(3).textContent = `$${sale.total.toFixed(2)}`
  })
}

