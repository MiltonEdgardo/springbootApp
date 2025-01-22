// Call the dataTables jQuery plugin

$(document).ready(function() {
  loadUsers()
  $('#users').DataTable();

  updateEmaiUser();
});

function updateEmaiUser() {
  document.getElementById("email-user").outerHTML = localStorage.email;
}

const DELETE_MESSAGE = 'Do you want to delete this user?';

async function loadUsers() {
  const request = await fetch('api/users', {
    method: 'GET',
    headers: getHeaders()
  });

  const users = await request.json();


  let allUsersInHTML = '';
  for (let user of users) {
    let deleteButton = '<a href="#" onclick="deleteUser(' + user.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    let phone = user.phone == null ? ' - ' : user.phone;

    let everyUserRow = '<tr><td>' + user.id + '</td><td>' + user.name + '</td><td>' + user.lastName + '</td><td>' + user.email + '</td><td>' + phone + '</td><td>' + deleteButton + '</td></tr>';
    allUsersInHTML += everyUserRow;
  }

  console.log(users)


  document.querySelector('#usersTable tbody').outerHTML = allUsersInHTML

}

function getHeaders() {
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
  };
}

async function deleteUser(id) {
  if (confirm(DELETE_MESSAGE)) {
    const request = await fetch(`api/users/${id}`, {
      method: 'DELETE',
      headers: getHeaders()
    });

    location.reload()
  } else {
    return;
  }



}