
$('#google-login').on('click', function (e) {
    e.preventDefault();
    var token = $('#token').val();
    login({token : token});
});

$('#ptc-login').on('click', function (e) {
    e.preventDefault();
    var username = $('#username').val();
    var password = $('#password').val();
    login({username : username, password : password});
});

function login(data) {
    $.ajax({
        url: '/bin/pokego/auth',
        method: 'POST',
        data: data
    }).done(function () {
        console.log("login success.");
        window.location.href = '/etc/pokegolure/index.html'
    }).fail(function (e) {
        console.error("login failure.", e);
    })
}