window.addEventListener('DOMContentLoaded', function() {
    const loginStatus = sessionStorage.getItem("loginStatus");
    if (!loginStatus || loginStatus !== "In") {
        window.location.href = 'Login.html'; // Redirect to login page
    }
});

let currentPage = 0; // Track the current page number
let pageSize = 10; // Set the default page size

async function populateTable(pageNo = 1, pageSize = 10,columnName,columnValue) {
    const searchCriteria = {
        pageNo: pageNo,
        pageSize: pageSize,
        columnName: columnName, // Provide column name for search criteria if needed
        columnValue: columnValue // Provide column value for search criteria if needed
    };

    try {
        const response = await fetch('http://localhost:8080/customer/viewCustomers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(searchCriteria)
        });

        const data = await response.json();
        tableDataFill(data.list)
       

        console.log(data);
        // Enable or disable "Fetch Next Page" button based on whether it's the last page
    } catch (error) {
        console.error('Error:', error);
        // Handle error
    }
}

// Function to delete a customer by UUID
async function deleteCustomer(uuid) {
    try {
        const response = await fetch(`http://localhost:8080/customer/delete/${uuid}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            // Reload the table after successful deletion
            await populateTable(currentPage, pageSize);
        } else {
            console.error('Failed to delete customer');
            // Handle deletion failure
        }
    } catch (error) {
        console.error('Error:', error);
        // Handle error
    }
}

// Populate the table with initial data when the page loads
populateTable();

// Event listener for fetching the next page
document.getElementById("fetchNextPage").addEventListener("click", async () => {
    currentPage++; // Increment the current page number
    await populateTable(currentPage);
});

// Event listener for fetching data based on the specified rows input
document.getElementById("fetchByRows").addEventListener("click", async () => {
    pageSize = parseInt(document.getElementById("rowsInput").value);
    currentPage = 0; // Reset current page number
    await populateTable(currentPage, pageSize);
});

document.getElementById("addCustomer").addEventListener("click", () => {
    window.location.href = "AddCustomer.html";
});
document.getElementById("searchButton").addEventListener("click", () => {
    // Retrieve selected field and input value
    const selectedField = document.getElementById("fieldSelect").value;
    const searchValue = document.getElementById("searchInput").value;

    // Call populateTable function with selected field and input value
    populateTable(0,10,selectedField, searchValue);
});

function tableDataFill(data){
     // Get the table body element
     const tableBody = document.getElementById("customerTableBody");

     // Clear existing rows
     tableBody.innerHTML = "";
     console.log(data);

     // Iterate through the fetched customer data and populate the table
     data.forEach(customer => {
         const row = document.createElement("tr");
         row.innerHTML = `
             <td>${customer.first_name}</td>
             <td>${customer.last_name}</td>
             <td>${customer.street}</td>
             <td>${customer.address}</td>
             <td>${customer.city}</td>
             <td>${customer.state}</td>
             <td>${customer.email}</td>
             <td>${customer.phone}</td>
             <td class="action-btns">
                 <button>Edit</button>
                 <button onclick="deleteCustomer('${customer.uuid}')">Delete</button>
             </td>
         `;
         tableBody.appendChild(row);
     });
     document.getElementById("fetchNextPage").disabled = data.isLast;

}

document.getElementById("sync").addEventListener("click",async ()=>{
    const login = {
       login_id:"",
       password:""
    };
    login.login_id="test@sunbasedata.com",
    login.password="Test@123"
    try {
        const response = await fetch('http://localhost:8080/customer/sync', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(login)
        });
        const data = await response.json();
        tableDataFill(data)
       

        // Enable or disable "Fetch Next Page" button based on whether it's the last page
    } catch (error) {
        console.error('Error:', error);
        // Handle error
    }
})
