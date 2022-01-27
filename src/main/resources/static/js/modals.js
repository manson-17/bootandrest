$(async function () {
    await getTableWithUsers()
    await addNewUser()
    await getRoles()
    await  getAuthorityUser();

})


const userFetchService = {
    head: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Referer': null
    },
    findAllUsers: async () => await fetch('api/users'),
    findOneUser: async (id) => await fetch(`api/users/${id}`),
    addNewUser: async (user) => await fetch('api/users', {
        method: 'POST',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    updateUser: async (user, id) => await fetch(`api/users/${id}`, {
        method: 'PATCH',
        headers: userFetchService.head,
        body: JSON.stringify(user)
    }),
    deleteUser: async (id) => await fetch(`api/users/${id}`, {method: 'DELETE', headers: userFetchService.head}),
    findAllRoles: async () => await fetch('api/users/roles'),
    findAuthorityUser: async () => await fetch(`api/users/authority`)

}

async function getAuthorityUser() {
    let table = $('#tableWithUser');
    table.empty();

    await userFetchService.findAuthorityUser()
        .then(res => res.json())
        .then(user =>{
            let tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.lastname}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(s => (s.name != 'ROLE_ADMIN' ? 'USER ' : 'ADMIN '))}</td>
                        </tr>
                )`;
            table.append(tableFilling);
        })

}

async function getTableWithUsers() {
    let table = $('#mainTableWithUsers');
    table.empty();

    await userFetchService.findAllUsers()
        .then(res => res.json())
        .then(users => {
            users.forEach(user => {
                let tableFilling = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.lastname}</td>
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(s => (s.name != 'ROLE_ADMIN' ? 'USER ' : 'ADMIN '))}</td>
                            <td>
                                <button type="button" onclick="editUserModal(${user.id})" data-action="edit" 
                                class="btn btn-info" data-toggle="modal" data-target="#editModal">Edit</button>
                            </td>
                            <td>
                                <button type="button" onclick="deleteUserModal(${user.id})" data-action="delete" 
                                class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">Delete</button>
                            </td>
                        </tr>
                )`;
                table.append(tableFilling);
            })
        })
}

async function editUserModal(id) {
    await userFetchService.findOneUser(id)
        .then(res => {
            res.json()
                .then(user => {
                    $('#id').val(user.id)
                    $('#name').val(user.name)
                    $('#lastname').val(user.lastname)
                    $('#age').val(user.age)
                    $('#email').val(user.email)
                    document.getElementById('roles').innerHTML = ''
                    userFetchService.findAllRoles()
                        .then(res => res.json())
                        .then(roles => {
                            roles.map(role => {
                                $('#roles')
                                    .append(`<option id=${role.id} value=${role.name}>${role.name}</option>`)
                            })
                        })

                })
        })
}

$("#editButton").on('click', async () => {
    let id = document.getElementById('id').value
    let name = document.getElementById('name').value
    let lastname = document.getElementById('lastname').value
    let age = document.getElementById('age').value
    let email = document.getElementById('email').value
    let password = document.getElementById('password').value
    let roles = getUserRoles(Array.from(document.getElementById('roles').selectedOptions)
        .map(role => role.id))
    let data = {
        id: id,
        name: name,
        lastname: lastname,
        age: age,
        email: email,
        password: password,
        roles: roles
    }
    console.log(roles)
    await userFetchService.updateUser(data, id)
        .then(() => {
            $("#editModal .close").click();
            document.getElementById("editForm").reset()
            getTableWithUsers()
        })
})

async function deleteUserModal(id) {
    await userFetchService.findOneUser(id)
        .then(res => {
            res.json()
                .then(user => {
                    $('#idD').val(user.id)
                    $('#nameD').val(user.name)
                    $('#lastnameD').val(user.lastname)
                    $('#ageD').val(user.age)
                    $('#emailD').val(user.email)
                    document.getElementById('rolesD').innerHTML = ''
                    user.roles.map(role => {
                        $('#rolesD')
                            .append(`<option>${role.name}</option>`)
                    })
                })
        })
}

$("#deleteButton").on('click', async () => {
    let id = document.getElementById('idD').value
    await userFetchService.deleteUser(id)
        .then(() => {
            $("#deleteModal .close").click();
            document.getElementById("deleteForm").reset()
            getTableWithUsers()
        })
})

async function getRoles() {
    $('#getRolesForNewUser').on('click', async () => {
        document.getElementById('rolesNew').innerHTML = ''
        userFetchService.findAllRoles()
            .then(res => res.json())
            .then(roles => {
                roles.map(role => {
                    $('#rolesNew')
                        .append(`<option id="${role.id}" value="${role.name}">${role.name}</option>`)
                })
            })
    })
}

async function addNewUser() {
    $('#addNewUserButton').click(async () => {
        let name = document.getElementById('nameNew').value
        let lastname = document.getElementById('lastnameNew').value
        let age = document.getElementById('ageNew').value
        let email = document.getElementById('emailNew').value
        let password = document.getElementById('passwordNew').value
        let roles = getUserRoles(Array.from(document.getElementById('rolesNew').selectedOptions)
             .map(role => role.id))
        let data = {
            name: name,
            lastname: lastname,
            age: age,
            email: email,
            password: password,
            roles: roles
        }
        await userFetchService.addNewUser(data)
            .then(() => {
                $('#myTab a[href="#allUsers"]').tab('show');
                document.getElementById("newUserForm").reset()
                getTableWithUsers()
            })
    })
}

function getUserRoles(list) {
    let roles = [];

    list.forEach(id => {
        if (id == 1) {
            roles.push({id: 1, name: 'ROLE_ADMIN'})

        } else if (id == 2) {
            roles.push({id: 2, name: 'ROLE_USER'})
        }
    })
    return roles;
}