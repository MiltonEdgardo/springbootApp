// Call the dataTables jQuery plugin
$(document).ready(function() {
    //On ready
});

const ERROR_CREDENTIALS_MESSAGE = 'Email or Password are incorrect. Please, try again.'

async function loginUsers() {
    let data = {};
    data.email = document.getElementById('Email').value;
    data.password = document.getElementById('Password').value;

    const request = await fetch('api/login', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)

    });

    const response = await request.text();
    if (response != 'FAIL') {
        localStorage.token = response;
        localStorage.email = data.email;
        window.location.href = 'users.html';
    } else {
        alert(ERROR_CREDENTIALS_MESSAGE)
    }

}