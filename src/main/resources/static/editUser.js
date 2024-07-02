async function getRoles() {
    const response = await fetch(`/api/admin/roles`);
    return await response.json();
}

async function sendDataEditUser(user, id) {
    const response = await fetch(`/api/admin/users/${id}`, {
        method: "PUT",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(user)
    });

}

const modalEdit = document.getElementById("editModal");
let userIdToEdit = null;


modalEdit.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    userIdToEdit = button.getAttribute('data-user-id');
    console.log('Modal opened for user with ID:', userIdToEdit); // Отладочное сообщение
});

async function EditModalHandler() {
    await fillModal(modalEdit);
}

modalEdit.addEventListener("submit", async function (event) {
    event.preventDefault();

    const rolesSelected = document.getElementById("allRolesEdit");

    let allRoles = await getRoles();
    let AllRoles = {};
    for (let role of allRoles) {
        AllRoles[role.name] = role.id;
    }
    let roles = [];
    for (let option of rolesSelected.selectedOptions) {
        if (Object.keys(AllRoles).indexOf(option.value) != -1) {
            roles.push({roleId: AllRoles[option.value], name: option.value});
        }
    }

    let user = {
        id: userIdToEdit,
        username: document.getElementById("usernameEdit").value,
        usersurname: document.getElementById("usersurnameEdit").value,
        department: document.getElementById("departmentEdit").value,
        salary: document.getElementById("salaryEdit").value,
        password: document.getElementById("passwordEdit").value,
        roles: roles
    };

    await sendDataEditUser(user, userIdToEdit);
    await fillTableOfAllUsers();

    const modalBootstrap = bootstrap.Modal.getInstance(modalEdit);
    modalBootstrap.hide();
});