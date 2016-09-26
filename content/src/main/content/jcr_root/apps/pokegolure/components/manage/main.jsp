<%@page session="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling" %>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="pokego" uri="http://aem.pokego.lure/pokegolure/taglib/1.0" %>
<cq:defineObjects />

<script id="pokego-manage_tmpl--lure" type="application/x-type-handlebars">
    <coral-masonry-item data-lure-id="{{id}}" style="" tabindex="0" class="pokego-manage__lures__item {{#isLureActive this}}active{{/isLureActive}} foundation-collection-item coral-Masonry-item">
        <coral-card class="coral-Card">
            <coral-card-asset class="coral-Card-asset">
                <img src="//maps.googleapis.com/maps/api/staticmap?key=${pokego:mapsApiKey(sling)}&center={{latitude}},{{longitude}}&zoom=14&size=300x175">
            </coral-card-asset>
            <div class="coral-Card-wrapper">
                <coral-card-content class="coral-Card-content">
                    <coral-card-title class="coral-Card-title">{{address}}</coral-card-title>
                </coral-card-content>
            </div>
        </coral-card>
        <coral-quick-actions class="coral-QuickActions">
            <button class="coral-Button coral-Button--square coral-QuickActions-button pokego-manage__lures__item__delete" role="menuitem" aria-label="Delete" title="Delete" type="button" size="M" is="coral-button">
                <coral-icon aria-label="delete" role="img" size="S" icon="delete" class="coral-Icon coral-Icon--sizeS coral-Icon--delete"></coral-icon>
                <coral-button-label class="coral-Button-label"></coral-button-label>
            </button>
        </coral-quick-actions>
    </coral-masonry-item>
</script>

<script id="pokego-pokestop_tmpl" type="application/x-type-handlebars">
    <div id="content">
            <h1>{{name}}</h1>
            <img class="pokego-manage__map__image" src="{{imageUrl}}"/>
            <div>
                <button id="pokego-unmanage" data-pokestop-id="{{id}}" class="coral-Button coral-Button--warning {{#unless managed}}hidden{{/unless}}">Unmanage Pokestop</button>
                <button id="pokego-manage" data-pokestop-id="{{id}}" class="coral-Button coral-Button--primary {{#if managed}}hidden{{/if}}">Manage Pokestop</button>
            </div>
    </div>
</script>
    
<div class="foundation-layout-panel">
    <div class="foundation-layout-panel-header">
        <div id="granite-shell-actionbar" class="granite-actionbar foundation-collection-actionbar" data-foundation-collection-actionbar-target=".granite-health-reports-collection">
            <div class="granite-actionbar-left">
                <div class="granite-actionbar-item">
                    <span class="granite-title" role="heading" aria-level="1">
                        <div>User: <span id="pokego-user-id">n/a</span></div>
                    </span>
                </div>
                <div class="granite-actionbar-item">
                    <span class="granite-title" role="heading" aria-level="1">
                        <div>Remaining Lures: <span id="pokego-lures-num">n/a</span></div>
                    </span>
                </div>
            </div>
            <div class="granite-actionbar-right">
                <div class="granite-actionbar-item">
                    <button id="pokego-signout" class="coral-Button coral-Button--primary">Sign Out</button>
                </div>
            </div>
        </div>
    </div>

    <div class="foundation-layout-panel-bodywrapper">
        <div class="foundation-layout-panel-body">
            <div class="foundation-layout-panel-content">
                <div class="pokego-manage__map">
                    <div id="gmap"></div>
                    <div class="pokego-manage__map__input coral-Form-fieldwrapper">
                        <input aria-invalid="false" class="coral-Form-field coral-Textfield" name="location" value="" aria-required="true" data-foundation-validation="" data-validation="" is="coral-textfield" type="text" placeholder="Search for location" disabled="">
                    </div>
                </div>
                <div class="pokego-manage__lures">
                    <coral-masonry id="pokego-lures-list" style="height: 509px;" class="foundation-advancedselect-collection aria-skiphandling foundation-collection foundation-layout-masonry coral-Masonry is-loaded" data-foundation-selections-mode="single" layout="fixed-spread" columnwidth="242" spacing="10" data-foundation-layout="{&quot;name&quot;:&quot;foundation-layout-masonry&quot;,&quot;selectionMode&quot;:true,&quot;limit&quot;:null,&quot;layoutId&quot;:&quot;field&quot;,&quot;autoDefaultMode&quot;:false}">
                    </coral-masonry>
                </div>
            </div>
        </div>
    </div>
</div>
    
<cq:includeClientLib categories="apps.pokegolure.manage"/>
<script src="https://maps.googleapis.com/maps/api/js?v=3.25&key=${pokego:mapsApiKey(sling)}&callback=PokeGoLure.Manage.initMap&libraries=places" async defer></script>
