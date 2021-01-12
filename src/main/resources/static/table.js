$(document).ready(getAllUsers());

// USER TABLE
function getAllUsers() {
    $("#table").empty();
    $.ajax({
        type: 'POST',
        url: '/api/admin/users',
        timeout: 3000,
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                $("#table").append($('<tr>').append(
                    $('<td>').append($('<span>')).text(user.id),
                    $('<td>').append($('<span>')).text(user.username),
                    $('<td>').append($('<span>')).text(user.lastname),
                    $('<td>').append($('<span>')).text(user.age),
                    $('<td>').append($('<span>')).text(user.email),
                    $('<td>').append($('<span>')).text(user.role),
                    $('<td>').append($('<button>').text("Edit").attr({
                        "type": "button",
                        "class": "btn btn-primary edit",
                        "data-toggle": "modal",
                        "data-target": "#myModal",

                    })
                        .data("user", user)),
                    $('<td>').append($('<button>').text("Delete").attr({
                        "type": "button",
                        "class": "btn btn-danger delete",
                        "data-toggle": "modal",
                        "data-target": "#myModalDelete",
                    })
                        .data("user", user))
                    )
                );
            });
        }
    });
}

// EDIT USER MODAL
$(document).on("click", ".edit", function () {
    let user = $(this).data("user");

    $('#idInput').val(user.id).hide();
    $('#usernameInput').val(user.username);
    $('#lastnameInput').val(user.lastname);
    $('#ageInput').val(user.age);
    $('#emailInput').val(user.email);
    $('#passwordInput').val(null);
    $('#roleInput').val(user.role);

});

$(document).on("click", ".editUser", function () {
    let formData = $(".myForm").serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/update',
        data: formData,
        timeout: 3000,
        success: function () {
            getAllUsers();
        }
    });
});
// DELETE USER MODAL
$(document).on("click", ".delete", function () {
    let user = $(this).data('user');
    $('#delId').val(user.id).hide();
    $(document).on("click", ".deleteUser", function () {
        $.ajax({
            type: 'POST',
            url: '/api/admin/remove',
            data: {id: $('#delId').val()},
            timeout: 500,
            success: function () {
                getAllUsers();
            }
        });
    });
})
// ADD USER FORM
$('.addUser').click(function () {
    $('#usersTable').trigger('click');
    let user = $(".formAddUser").serializeArray();

    $('#id').val(user.id).val(0).hide();
    $('#username').val('');
    $('#lastname').val('');
    $('#age').val('');
    $('#email').val('');
    $('#password').val('');
    $('#role').val('');

    $.ajax({
        type: 'POST',
        url: '/api/admin/addUser',
        data: user,
        timeout: 100,
        success: function () {

            $('.formAddUser')[0].reset();
            getAllUsers()
        }
    })
});
// USER FORM
$(document).ready(getUser());
function getUser() {
    $("#userTable").empty();
    $.ajax({
        type: 'POST',
        url: '/api/user/getUser',
        timeout: 3000,
        error: function() {
            $('#forUser').hide();
        },
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                if(user.role === "ROLE_USER") {
                    $('#v-pills-profile-tab').trigger('click');
                    $('#v-pills-profile').trigger('click');
                    $('#v-pills-home-tab').hide();
                }
                $("#userTable").append($('<tr>').append(
                    $('<td>').append($('<span>')).text(user.id),
                    $('<td>').append($('<span>')).text(user.username),
                    $('<td>').append($('<span>')).text(user.lastname),
                    $('<td>').append($('<span>')).text(user.age),
                    $('<td>').append($('<span>')).text(user.email),
                    $('<td>').append($('<span>')).text(user.role),
                    )
                );
            });
        }
    });
}

