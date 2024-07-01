async function getRoles() {
    const response = await fetch("/api/admin/roles");
    return await response.json();
}


async function createNewUser(user) {
    await fetch("/api/admin",
        {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(user)})

}

async function addNewUserForm() {
    const newUserForm = document.getElementById("addNewUser");

    newUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const name = newUserForm.querySelector("#usernameNew").value.trim();
        const surname = newUserForm.querySelector("#usersurnameNew").value.trim();
        const department = newUserForm.querySelector("#departmentNew").value.trim();
        const salary = newUserForm.querySelector("#salaryNew").value.trim();
        const password = newUserForm.querySelector("#passwordNew").value.trim();

        const rolesSelected = document.getElementById("rolesNew");


        let allRole = await getRoles();
        let AllRoles = {};
        for (let role of allRole) {
            AllRoles[role.name] = role.id;
        }
        let roles = [];
        for (let option of rolesSelected.selectedOptions) {
            if (Object.keys(AllRoles).indexOf(option.value) != -1) {
                roles.push({roleId: AllRoles[option.value], name: option.value});
            }
        }


        const newUserData = {
            username: name,
            usersurname: surname,
            department: department,
            salary: salary,
            password: password,
            roles: roles
        };

        await createNewUser(newUserData);
        newUserForm.reset();

        document.querySelector('#admin-users-table-tab').click();
        await fillTableOfAllUsers();
    });
}