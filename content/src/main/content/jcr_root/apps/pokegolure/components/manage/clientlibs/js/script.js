var PokeGoLure = PokeGoLure || {}
PokeGoLure.Manage = (function ($) {
    'use strict';
    
    var map;
    
    function _initMap() {
        map = new google.maps.Map(document.getElementById('gmap'), {
            center: {lat: -34.397, lng: 150.644},
            scrollwheel: false,
            zoom: 8,
            clickableIcons: false,
            disableDefaultUI: true
        });
    
    }
    
    return {
        initMap: _initMap
    }
})(jQuery);