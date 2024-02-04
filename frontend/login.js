document.querySelector('.login').addEventListener('submit', async function(event) {
    event.preventDefault(); // Prevent form submission
    const loginId = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const data = { login_id: loginId, password: password };

    try {
        const response = await fetch('http://localhost:8080/customer/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            const errorResponse = await response.json();
            const errorMessage = document.getElementById('errorMessage');
            errorMessage.textContent = 'Error: ' + errorResponse.responseMessage; // Set the error message in the <p> tag
            errorMessage.style.display = 'block'; // Display the error message
            return; // Exit function to prevent further execution
        }

        const result = await response;

        // Save token in session storage
        sessionStorage.setItem("loginStatus","In");

        // Navigate to other page
        // Wait for 10 seconds (10000 milliseconds) before navigating to the other page
        setTimeout(function() {
            window.location.href = 'View.html';
        }, 1000);

    } catch (error) {
        console.error('Error:', error.message);
        // Handle error
    }
});
