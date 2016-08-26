<%@page session="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling" %>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<cq:defineObjects />
    
<div class="pokego-login">
    <div class="pokego-login__container">
        <p>PokeGoLure allows you to manage PokeStops at any given location. You must have an existing Pokemon Go account setup against the Google account that will be used to manage lures.</p>
        <p>Simply login to your Google account to get started.</p>
        <div class="pokego-login__container__btn">
            <div class="g-signin2" data-onsuccess="onSignIn"></div>
        </div>
        <small>Disclaimer: This software should be used at your own risk as it may be against the Terms of Use.</small>
    </div>
</div>

<cq:includeClientLib css="apps.pokegolure.login"/>
<script src="https://apis.google.com/js/platform.js" async defer></script>
