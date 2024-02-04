// Create a <p> tag for displaying the UUID and error message
const pTag = document.createElement('p');
pTag.id = 'uuidDisplay';
const btnGroup = document.querySelector('.btn-group');
btnGroup.insertAdjacentElement('afterend', pTag);

document.getElementById('addCustomer').addEventListener('click', async function(event) {
    event.preventDefault(); // Prevent form submission
    
    const customerDto = {
        first_name: document.getElementById('first_name').value,
        last_name: document.getElementById('last_name').value,
        street: document.getElementById('street').value,
        address: document.getElementById('address').value,
        city: document.getElementById('city').value,
        state: document.getElementById('state').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value
    };

    if (!customerDto.first_name || !customerDto.last_name || !customerDto.email || !customerDto.phone) {
        // If any required field is missing, display the error message and return
        pTag.textContent = "All fields are required";
        return;
    }
    try {
        const response = await fetch('http://localhost:8080/customer/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(customerDto)
        });
        
        const data = await response.json();

        // Extract UUID from the response object
        const uuid = data.uuid;

        // Update the content of the <p> tag with the UUID
        pTag.textContent = `UUID: ${uuid}`;
    } catch (error) {
        console.error('Error:', error.message);
        // Display the error message in the <p> tag
        pTag.textContent = 'Failed to add customer. Please try again.';
    }
});
