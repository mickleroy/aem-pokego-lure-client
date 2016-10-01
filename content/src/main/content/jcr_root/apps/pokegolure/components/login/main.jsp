<%@page session="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling" %>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="pokego" uri="http://aem.pokego.lure/pokegolure/taglib/1.0" %>
<cq:defineObjects />
    
<div class="pokego-login">
    <div class="pokego-login__container">
        <p>PokeGoLure allows you to manage PokeStops at any given location. You must have an existing Pokemon Go account setup against the Google account that will be used to manage lures.</p>
        <p>Simply login to your Google account to get started.</p>
        <div>
            <a class="coral-Button coral-Button--large coral-Button--primary" id="google-auth" target="_blank" href="${pokego:getGoogleLoginUrl()}">Get Authorisation Token</a>
        </div>
        <div>
            <div id="google-error" class="coral3-Alert coral3-Alert--error coral3-Alert--large">
                <strong class="coral3-Alert-header">
                    <i class="coral-Alert-typeIcon coral-Icon coral-Icon--sizeS coral-Icon--alert"></i>
                    Authentication Failed
                </strong>
                <div class="coral3-Alert-content">Please check the token is correct, then try again.</div>
            </div>
            <form class="coral-Form coral-Form--vertical" id="google-auth--form">
                <section class="coral-Form-fieldset">
                    <label class="coral-Form-fieldlabel">Paste your authorisation token here</label>
                    <input name="token" id="token" placeholder="Google Token" type="text" class="coral-Form-field coral-Textfield" />
                    <button class="coral-Button coral-Button--primary" id="google-login">Authorise</button>
                </section>
            </form>
        </div>
        <div>
            <p>Or login with your Pokemon Trainer Club account.</p>
            <div id="ptc-error" class="coral3-Alert coral3-Alert--error coral3-Alert--large">
                <strong class="coral3-Alert-header">
                    <i class="coral-Alert-typeIcon coral-Icon coral-Icon--sizeS coral-Icon--alert"></i>
                    Login Failed
                </strong>
                <div class="coral3-Alert-content">Please check your username and password, then try again.</div>
            </div>
            <form class="coral-Form coral-Form--vertical">
                <section class="coral-Form-fieldset">

                    <input name="username" id="username" placeholder="Username" type="text" class="coral-Form-field coral-Textfield" />

                    <input name="password" id="password" placeholder="Password" type="password" class="coral-Form-field coral-Textfield" />
                    <button class="coral-Button coral-Button--primary" id="ptc-login">Login</button>
                </section>
            </form>
        </div>
        <small>Disclaimer: This software should be used at your own risk as it may be against the Terms of Use.</small>
    </div>
</div>

<cq:includeClientLib categories="apps.pokegolure.login"/>
<script src="https://apis.google.com/js/platform.js" async defer></script>
