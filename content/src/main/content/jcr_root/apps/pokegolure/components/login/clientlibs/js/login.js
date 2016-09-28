var PokeGoLure = PokeGoLure || {};
PokeGoLure.Auth = (function ($) {

    'use strict';

    var ENDPOINTS = {
        AUTHENTICATE:  '/bin/pokego/auth',
        LOGIN_SUCCESS: '/etc/pokegolure/index.html'
    };


    $('#google-login').on('click', function (e) {
        e.preventDefault();
        var token = $('#token').val();
        _login({token : token});
    });

    $('#ptc-login').on('click', function (e) {
        e.preventDefault();
        var username = $('#username').val();
        var password = $('#password').val();
        _login({username : username, password : password});
    });

    function _login(data) {
        $.ajax({
            url: ENDPOINTS.AUTHENTICATE,
            method: 'POST',
            data: data
        }).done(function () {
            console.log("login success.");
            window.location.href = ENDPOINTS.LOGIN_SUCCESS
        }).fail(function () {
            console.error("login failure.");
        })
    }

})(jQuery);