var PokeGoLure = PokeGoLure || {};
PokeGoLure.Manage = (function ($) {
    'use strict';
    
    var map,
        lureItemTemplate,
        infowindowTemplate,
        autocomplete,
        infowindow,
        markers           = {},
        $luresList        = $('#pokego-lures-list'),
        $lureNumber       = $('#pokego-lures-num'),
        $userId           = $('#pokego-user-id'),
        $signOutBtn       = $('#pokego-signout'),
        $locationInput    = $('input[name="location"]'),
        $loadingMask      = $('.foundation-ui-mask'),
        DEFAULT_LAT       = -37.8150085,
        DEFAULT_LONG      = 144.9658801,
        POKESTOP_ICON     = '/etc/pokegolure/assets/blue-marker.png';
    
    var SERVLET_URLS = {
        ADD: '/bin/pokego/pokestop/add',
        REMOVE: '/bin/pokego/pokestop/remove',
        FIND_ALL: '/bin/pokego/pokestops',
        POKE_DATA: '/bin/pokego/pokedata',
        NEARBY_POKESTOPS: '/bin/pokego/nearbypokestops',
        AUTH: '/bin/pokego/auth'
    };
    
    /**
     * This function initialises the component.
     */
    function _init() {
        // compile Handlebars templates
        lureItemTemplate = Handlebars.compile($('#pokego-manage_tmpl--lure').html());
        infowindowTemplate = Handlebars.compile($('#pokego-pokestop_tmpl').html());
        
        // register handlebars helper
        Handlebars.registerHelper('isLureActive', function(item, opts) {
            return item.status == 'active' ? opts.fn(this) : opts.inverse(this);
        });
        
        // register event handlers
        $signOutBtn.on('click', _handleSignOut);
        $luresList.on('click', '.pokego-manage__lures__item__delete', _handleLureDeleteClick);
        $luresList.on('mouseenter mouseleave', '.pokego-manage__lures__item', _handleLureMouseMovement);
        $(document).on("click", "#pokego-manage", _managePokestopClicked);
        $(document).on("click", "#pokego-unmanage", _unmanagePokestopClicked);

        _fetchPokeData();

        setInterval(_fetchPokeData, 1000 * 60); // Fetch data every 60 seconds
    }
    
    /**
     * This function is a callback executed by the Google Maps API once it has been fetched.
     * Any initialisation of Google services should be done here.
     */
    function _initMap() {
        map = new google.maps.Map(document.getElementById('gmap'), {
            center: {lat: DEFAULT_LAT, lng: DEFAULT_LONG},
            scrollwheel: false,
            zoom: 8,
            clickableIcons: false,
            disableDefaultUI: true,
            zoomControl: true
        });

        // once map has finished loading, create autocomplete and infowindow
        autocomplete = new google.maps.places.Autocomplete(document.getElementsByName('location')[0]);
        autocomplete.bindTo('bounds', map);
        autocomplete.addListener('place_changed', _handleAutoComplete);
        $locationInput.removeAttr('disabled');
        infowindow = new google.maps.InfoWindow({content: ""});

        // once map has finished loading, show all lures
        google.maps.event.addListenerOnce(map, 'idle', _fetchAllLures);
    }
    
    /**
     * This function fetches all the managed lures once the map has loaded.
     */
    function _fetchAllLures() {
        // backend call to grab all configured lures
        $.get(SERVLET_URLS.FIND_ALL, function(data) {
            // add lures to list
            data.stops.forEach(function(pokestop){
                pokestop.managed = true;
                // add to list
                $luresList.append(lureItemTemplate(pokestop));
                
                // add marker to map
                var marker = new google.maps.Marker({
                    map: map,
                    position: {
                        lat: Number(pokestop.latitude),
                        lng: Number(pokestop.longitude)
                    },
                    pokestop: pokestop,
                    icon: POKESTOP_ICON
                });

                marker.addListener('click', _markerListener);
                markers[pokestop.id] = marker;
            });
        })
        .fail(function() {
            console.error('[ERROR] Could not fetch pokestops from JCR');
        });
    }

    /**
     * This function fetches trainer username and total of lures in the inventory.
     */
    function _fetchPokeData() {
        // backend call to grab all trainer name and lures left
        $.get(SERVLET_URLS.POKE_DATA, function(data) {
            _setUserId(data.username);
            _setRemainingLures(data.luresLeft);
        })
        .fail(function() {
            console.error('[ERROR] Could not fetch pokedata');
        });
    }

    /**
     * Click Handler for Manage Pokestop
     */
    function _managePokestopClicked() {
        var id = $(this).data("pokestopId");
        var marker = markers[id];
        if(marker){
            _addLureViaMarker(marker);
        }
    }

    /**
     * Click Handler for Unmanage Pokestop
     */
    function _unmanagePokestopClicked() {
        var id = $(this).data("pokestopId");
        var marker = markers[id];
        if(marker){
            _removeLureViaMarker(marker);
        }
    }

    /**
     * If Manage Pokestop is done via Google Maps Marker
     */
    function _addLureViaMarker(marker) {
        // persist lure to JCR
        $.post(SERVLET_URLS.ADD, marker.pokestop, function(data) {
                // add lure to list
                marker.setIcon(POKESTOP_ICON);
                marker.pokestop.managed = true;
                $luresList.append(lureItemTemplate(marker.pokestop));
                infowindow.setContent(infowindowTemplate(marker.pokestop));
            })
            .fail(function() {
                console.error('[ERROR] Could not save lure to JCR');
            });
    }

    /**
     * If Unmanage Pokestop is done via Google Maps Marker
     */
    function _removeLureViaMarker(marker) {
        // delete lure from JCR
        $.post(SERVLET_URLS.REMOVE, {id: marker.pokestop.id}, function(data) {
                marker.setIcon(null);
                marker.pokestop.managed = false;
                $luresList.find("[data-lure-id='" + marker.pokestop.id + "']").remove();
                infowindow.setContent(infowindowTemplate(marker.pokestop));
            })
            .fail(function() {
                console.error('[ERROR] Could not delete lure from JCR');
            });
    }

    /**
     * This function logins in the user.
     */
    function _login(username, password) {
        // persist lure to JCR
        $.post(SERVLET_URLS.AUTH, {username: username, password: password}, function(data) {
            console.log("login result", data);
        })
        .fail(function() {
            console.error('[ERROR] Could not login');
        });
    }
    
    /**
     * This function is responsible for removing a lure from the list of managed lures.
     */
    function _handleLureDeleteClick(evt) {
        var lureId = $(this).closest('.pokego-manage__lures__item').data('lure-id');
        
        // delete lure from JCR
        $.post(SERVLET_URLS.REMOVE, {id: lureId}, function(data) {
            // remove lure from list
            $luresList.find("[data-lure-id='" + lureId + "']").remove();
            var marker = markers[lureId];
            if(marker){
                marker.setIcon(null);
                marker.pokestop.managed = false;
                infowindow.close(); //just in case
            }
        })
        .fail(function() {
            console.error('[ERROR] Could not delete lure from JCR');
        });
    }
    
    /**
     * This function sets the number of lures remaining.
     */
    function _setRemainingLures(number) {
        $lureNumber.text(number);
    }
    
    /**
     * This function sets the Pokemon Go username.
     */
    function _setUserId(userId) {
        $userId.text(userId);
    }
    
    /**
     * This function signs a user out of the application.
     */
    function _handleSignOut() {
        // TODO delete token and redirect to login page
        
        alert("You've been signed out.");
    }

    /**
     * This function is called when the google maps "place_changed" listener is called.
     */
    function _handleAutoComplete() {
        var place = autocomplete.getPlace();
        if (place && place.geometry) {
            map.setCenter(place.geometry.location);
            map.setZoom(18);
            _populateNearbyPokestops(place)
        }
    }

    /**
     * This function is used to add pokestops to the new location the user has selected
     */
    function _populateNearbyPokestops(place) {
        if(place.geometry.location){
            _getPokestopsInArea(place.geometry.location.lat(), place.geometry.location.lng());
        }
    }

    /**
     * This function is used to create and add _new_ pokestops as markers to the google maps.
     */
    function _addPokestopMarkers(pokestops){
        if(pokestops){
            // add new markers
            pokestops.forEach(function(pokestop){
                // add if new
                if(!markers[pokestop.id]) {

                    var marker = new google.maps.Marker({
                        map: map,
                        position: {
                            lat: pokestop.latitude,
                            lng: pokestop.longitude
                        },
                        pokestop: pokestop
                    });

                    marker.addListener('click', _markerListener);
                    markers[pokestop.id] = marker;

                }
            })
        }
    }

    /**
     * This function populates the content of the info windows with a pokestops info when a marker is clicked
     */
    function _markerListener(){
        infowindow.setContent(infowindowTemplate(this.pokestop));
        infowindow.open(map, this);
    }

    /**
     * This function uses a lat and lng position to query the pokemon go api for pokestops in that area
     */
    function _getPokestopsInArea(lat, lng) {
        $loadingMask.removeClass('hidden');
        
        $.get(SERVLET_URLS.NEARBY_POKESTOPS, {latitude: lat, longitude: lng})
            .done(function(data){
                _addPokestopMarkers(data.nearbyStops);
            })
            .fail(function() {
                console.error('[ERROR] Could not fetch nearby poke stops');
            })
            .always(function() {
                $loadingMask.addClass('hidden');
            });
    }
    
    /**
     * This function is used to show the quick actions menu on mouse over.
     */
    function _handleLureMouseMovement() {
        $(this).find('coral-quick-actions').toggleClass('is-open');
    }

    // initialise the component
    _init();

    return {
        initMap: _initMap,
        login: _login
    }
})(jQuery);
