/**
 * Charcoal by @attacomsian (https://twitter.com)
 * Copyright 2017-2018
 */
$(function () {

    // init tooltip & popovers
    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="popover"]').popover();

    //page scroll
    $('a.page-scroll').bind('click', function (event) {
        var $anchor = $(this);
        $('html, body').stop().animate({
            scrollTop: $($anchor.attr('href')).offset().top - 100
        }, 1500);
        event.preventDefault();
    });

    //toggle scroll menu
    $(window).scroll(function () {
        var scroll = $(window).scrollTop();
        if (scroll >= 400) {
            $('.sticky-navigation').addClass('bg-primary');
        } else {
            $('.sticky-navigation').removeClass('bg-primary');
        }
        return false;
    });
    
});