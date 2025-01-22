// Call the dataTables jQuery plugin
$(document).ready(function() {
    //On ready
});

const PASSWORD_NOT_MATCHED = 'Password dont match!'
const SUCCESS_REGISTER = 'The new account was created succesfully'


async function signInUsers() {
    let data = {};
    data.name = document.getElementById('Name').value;
    data.lastName = document.getElementById('LastName').value;
    data.email = document.getElementById('Email').value;
    data.password = document.getElementById('Password').value;

    let repeatPassword = document.getElementById('RepeatPassword').value;

    if (repeatPassword != data.password) {
        alert(PASSWORD_NOT_MATCHED);
        return;
    }

    const request = await fetch('api/users', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    alert(SUCCESS_REGISTER);
    window.location.href = 'login.html';
}