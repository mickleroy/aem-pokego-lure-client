var PokeGoLure = PokeGoLure || {}
PokeGoLure.Manage = (function ($) {
    'use strict';
    
    var map,
        DEFAULT_LAT = -37.8150085,
        DEFAULT_LONG = 144.9658801;
    
    function _initMap() {
        map = new google.maps.Map(document.getElementById('gmap'), {
            center: {lat: DEFAULT_LAT, lng: DEFAULT_LONG},
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