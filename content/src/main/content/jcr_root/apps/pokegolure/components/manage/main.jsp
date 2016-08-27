<%@page session="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling" %>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<cq:defineObjects />
    
<div class="foundation-layout-panel">
    <div class="foundation-layout-panel-header">
        <div id="granite-shell-actionbar" class="granite-actionbar foundation-collection-actionbar" data-foundation-collection-actionbar-target=".granite-health-reports-collection">
            <div class="granite-actionbar-left">
                <div class="granite-actionbar-item">
                    <span class="granite-title" role="heading" aria-level="1">
                        <div>User: pokegomick0066@gmail.com</div>
                    </span>
                </div>
                <div class="granite-actionbar-item">
                    <span class="granite-title" role="heading" aria-level="1">
                        <div>Remaining Lures: 6</div>
                    </span>
                </div>
            </div>
            <div class="granite-actionbar-right">
                <div class="granite-actionbar-item">
                    <button class="coral-Button coral-Button--primary">Sign Out</button>
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
                        <input aria-invalid="false" class="coral-Form-field coral-Textfield" name="location" value="" aria-required="true" data-foundation-validation="" data-validation="" is="coral-textfield" type="text" placeholder="Search for location">
                    </div>
                </div>
                <div class="pokego-manage__lures">
                    <coral-masonry style="height: 509px;" class="foundation-advancedselect-collection aria-skiphandling foundation-collection foundation-layout-masonry coral-Masonry is-loaded" data-foundation-selections-mode="single" layout="fixed-spread" columnwidth="242" spacing="10" data-foundation-layout="{&quot;name&quot;:&quot;foundation-layout-masonry&quot;,&quot;selectionMode&quot;:true,&quot;limit&quot;:null,&quot;layoutId&quot;:&quot;field&quot;,&quot;autoDefaultMode&quot;:false}">
                        <coral-masonry-item style="" tabindex="0" class="foundation-collection-item coral-Masonry-item">
                            <coral-card class="coral-Card">
                                <coral-card-asset class="coral-Card-asset">
                                    <img src="/etc/pokegolure/assets/map-melbourne.png">
                                </coral-card-asset>
                                <div class="coral-Card-wrapper">
                                    <coral-card-content class="coral-Card-content">
                                        <coral-card-title class="coral-Card-title">Flagstaff Gardens</coral-card-title>
                                    </coral-card-content>
                                </div>
                            </coral-card>
                        </coral-masonry-item>
                        <coral-masonry-item style="" tabindex="-1" class="foundation-collection-item coral-Masonry-item">
                            <coral-card class="coral-Card">
                                <coral-card-asset class="coral-Card-asset">
                                    <img src="/etc/pokegolure/assets/map-melbourne.png">
                                </coral-card-asset>
                                <div class="coral-Card-wrapper">
                                    <coral-card-content class="coral-Card-content">
                                        <coral-card-title class="coral-Card-title">Chadstone Shopping Center</coral-card-title>
                                    </coral-card-content>
                                </div>
                            </coral-card>
                        </coral-masonry-item>
                        <coral-masonry-item style="" tabindex="-1" class="foundation-collection-item coral-Masonry-item">
                            <coral-card class="coral-Card">
                                <coral-card-asset class="coral-Card-asset">
                                    <img src="/etc/pokegolure/assets/map-melbourne.png">
                                </coral-card-asset>
                                <div class="coral-Card-wrapper">
                                    <coral-card-content class="coral-Card-content">
                                        <coral-card-title class="coral-Card-title">Harbour Town, Docklands</coral-card-title>
                                    </coral-card-content>
                                </div>
                            </coral-card>
                        </coral-masonry-item>
                    </coral-masonry>
                </div>
            </div>
        </div>
    </div>
</div>
    
<cq:includeClientLib categories="apps.pokegolure.manage"/>
<script src="https://maps.googleapis.com/maps/api/js?v=3.25&key=API_KEY&callback=PokeGoLure.Manage.initMap" async defer></script>
