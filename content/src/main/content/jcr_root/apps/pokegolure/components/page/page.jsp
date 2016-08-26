<%@page session="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling" %>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<cq:defineObjects />

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com">
    
    <title>${currentPage.title} | PokeGoLure</title>

    <link rel="shortcut icon" href="${favicon}"/>

    <cq:includeClientLib css="apps.pokegolure.page"/>
</head>
<body class="coral--light">
    <coral-shell class="coral-Shell">
        <coral-shell-header class="coral--dark coral-Shell-header">
            <coral-shell-header-home aria-level="2" role="heading" class="coral-Shell-header-home" data-globalnav-navigator-main-href="">
                <a aria-level="2" role="heading" class="coral-Shell-homeAnchor" is="coral-shell-homeanchor" icon="adobeExperienceManagerColor" href="/">
                    <coral-icon aria-label="adobe experience manager color" role="img" size="M" icon="adobeExperienceManagerColor" class="coral-Icon coral-Icon--sizeM coral-Icon--adobeExperienceManagerColor"></coral-icon>
                    <coral-shell-homeanchor-label class="coral-Shell-homeAnchor-label">Adobe Experience Manager</coral-shell-homeanchor-label>
                </a>
            </coral-shell-header-home>
        </coral-shell-header>
        <coral-shell-content>
            <cq:include script="main.jsp"/>
        </coral-shell-content>
    </coral-shell>

    <cq:includeClientLib js="apps.pokegolure.page"/>
</body>
</html>