var PokeGoLure = PokeGoLure || {}
PokeGoLure.Manage = (function ($) {
    'use strict';
    
    var map,
        lureItemTemplate,
        $luresList        = $('#pokego-lures-list'),
        $lureNumber       = $('#pokego-lures-num'),
        $userId           = $('#pokego-user-id'),
        $signOutBtn       = $('#pokego-signout'),
        DEFAULT_LAT       = -37.8150085,
        DEFAULT_LONG      = 144.9658801;
    
    var SERVLET_URLS = {
        ADD: '/bin/pokego/add-pokestop',
        REMOVE: '/bin/pokego/remove-pokestop'
    }
    
    /**
     * This function initialises the component.
     */
    function _init() {
        // compile Handlebars templates
        lureItemTemplate = Handlebars.compile($('#pokego-manage_tmpl--lure').html());
        
        // register handlebars helper
        Handlebars.registerHelper('isLureActive', function(item, opts) {
            return item.status == 'active' ? opts.fn(this) : opts.inverse(this);
        });
        
        // register event handlers
        $signOutBtn.on('click', _handleSignOut);
        $luresList.on('click', '.pokego-manage__lures__item__delete', _handleLureDeleteClick);
        $luresList.on('mouseenter mouseleave', '.pokego-manage__lures__item', _handleLureMouseMovement);
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
            disableDefaultUI: true
        });
        
        // once map has finished loading, show all lures
        google.maps.events.addListenerOnce('idle', _fetchAllLures);
    }
    
    function _fetchAllLures() {
        // TODO: servlet call to grab all configured lures
    }
    
    /**
     * This function adds a new lure to be managed by the application
     * into the JCR as well as update the UI.
     */
    function _addLure(lure) {
        // persist lure to JCR
        $.post(SERVLET_URLS.ADD, lure, function(data) {
            // add lure to list
            $luresList.append(lureItemTemplate(lure));
        })
        .fail(function() {
            console.error('[ERROR] Could not save lure to JCR');
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
     * This function is responsible for removing a lure from the list of managed lures.
     */
    function _handleLureDeleteClick(evt) {
        var lureId = $(this).closest('.pokego-manage__lures__item').data('lure-id');
        var that = this;
        
        // delete lure from JCR
        $.post(SERVLET_URLS.REMOVE, {id: lureId}, function(data) {
            // remove lure to list
            $(that).closest('.pokego-manage__lures__item').remove();
        })
        .fail(function() {
            console.error('[ERROR] Could not save lure to JCR');
        });
    }
    
    /**
     * This function is used to show the quick actions menu on mouse over.
     */
    function _handleLureMouseMovement() {
        $(this).find('coral-quick-actions').toggleClass('is-open');
    }
    
    function _populateSampleData() {
        _setUserId("pokegomick@gmail.com");
        _setRemainingLures(5);
        
        _addLure({
            id: 123,
            location: "Test Lure 1, Melbourne",
            status: "active"
         });
        _addLure({
            id: 456,
            location: "Test Lure 2, Brisbane",
            status: "inactive"
         });
    }
    
    // initialise the component
    _init();
    
    // TEMPORARY
    _populateSampleData();

    return {
        initMap: _initMap,
        addLure: _addLure
    }
})(jQuery);