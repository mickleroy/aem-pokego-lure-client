var PokeGoLure = PokeGoLure || {};
PokeGoLure.Auth = (function ($) {

    'use strict';

    var ENDPOINTS = {
        AUTHENTICATE:  '/bin/pokego/auth',
        LOGIN_SUCCESS: '/etc/pokegolure/index.html'
    };

    var $googleError = $('#google-error');
    var $ptcError    = $('#ptc-error');


    $googleError.hide();
    $ptcError.hide();

    $('#google-login').on('click', function (e) {
        e.preventDefault();
        var token = $('#token').val();
        _login({token : token}, $googleError);
    });

    $('#ptc-login').on('click', function (e) {
        e.preventDefault();
        var username = $('#username').val();
        var password = $('#password').val();
        _login({username : username, password : password}, $ptcError);
    });

    function _login(data, error) {
        // Clear errors.
        $googleError.hide();
        $ptcError.hide();

        // Submit login data.
        $.ajax({
            url: ENDPOINTS.AUTHENTICATE,
            method: 'POST',
            data: data
        }).done(function () {
            console.log("login success.");
            window.location.href = ENDPOINTS.LOGIN_SUCCESS
        }).fail(function () {
            console.error("login failure.");
            error.show();
        })
    }

})(jQuery);