/*

Template:  Theme Name
Author: author name
Version: 1
Design and Developed by: HasTech
NOTE: If you have any note put here. 

*/

(function($) {
    "use strict";

  $(window).on('scroll',function() {    
   var scroll = $(window).scrollTop();
   if (scroll < 245) {
    $(".header-middle").removeClass("scroll-header");
   }else{
    $(".header-middle").addClass("scroll-header");
   }
  });
  
 


	
   /* jQuery MeanMenu */
    $('#mobile-menu-active').meanmenu({
        meanScreenWidth: "991",
        meanMenuContainer: ".mobile-menu-area .mobile-menu",
    });	
	
	

	$(".icon-search,.icon-nav ").on("click", function(){
		$(this).parent().find('.toogle-content').slideToggle('medium');
	})
	
	
	$('.popup-link').magnificPopup({
	  type: 'image',
	  gallery:{
			enabled:true
		}
	});	
	
	/* product-carousel */ 
	$('.product-carousel').owlCarousel({
		loop:false,
		nav:true,
		navText:['<i class="fa fa-long-arrow-left"></i>','<i class="fa fa-long-arrow-right"></i>'],
		responsive:{
			0:{
				items:1
			},
			767:{
				items:2
			},
			1000:{
				items:3
			},
			1200:{
				items:4
			}
		}
	})
	
	/* blog-active */ 
	$('.blog-active').owlCarousel({
		loop:false,
		nav:true,
		navText:['<i class="fa fa-long-arrow-left"></i>','<i class="fa fa-long-arrow-right"></i>'],
		responsive:{
			0:{
				items:1
			},
			767:{
				items:2
			},
			1000:{
				items:2
			}
		}
	})
	
	/* brand-active */ 
	$('.brand-active').owlCarousel({
		loop:true,
		nav:true,
		navText:['<i class="fa fa-long-arrow-left"></i>','<i class="fa fa-long-arrow-right"></i>'],
		responsive:{
			0:{
				items:2
			},
			767:{
				items:3
			},
			1000:{
				items:5
			}
		}
	})	
	
	/* testimonial-active */ 
	$('.testimonial-active').owlCarousel({
		loop:false,
		nav:false,
		navText:['<i class="fa fa-long-arrow-left"></i>','<i class="fa fa-long-arrow-right"></i>'],
		responsive:{
			0:{
				items:1
			},
			767:{
				items:1
			},
			1000:{
				items:1
			}
		}
	})
	
	/* testimonial-active */ 
	$('.details-tab').owlCarousel({
		loop:false,
		nav:true,
		margin:10,
		navText:['<i class="fa fa-long-arrow-left"></i>','<i class="fa fa-long-arrow-right"></i>'],
		responsive:{
			0:{
				items:2
			},
			767:{
				items:3
			},
			1000:{
				items:4
			}
		}
	})	


$('#mainSlider').nivoSlider({
	directionNav: true,
	animSpeed: 2000,
	slices: 18,
	pauseTime: 50000,
	pauseOnHover: false,
	controlNav: true,
	manualAdvance: true,
	prevText: '<i class="fa fa-long-arrow-left nivo-prev-icon"></i>',
	nextText: '<i class="fa fa-long-arrow-right nivo-next-icon"></i>'
});	
	
	

/*-------------------------
  showlogin toggle function
--------------------------*/
	 $( '#showlogin' ).on('click', function() {
        $( '#checkout-login' ).slideToggle(900);
     }); 
	
/*-------------------------
  showcoupon toggle function
--------------------------*/
	 $( '#showcoupon' ).on('click', function() {
        $( '#checkout_coupon' ).slideToggle(900);
     });
	 
/*-------------------------
  Create an account toggle function
--------------------------*/
	 $( '#cbox' ).on('click', function() {
        $( '#cbox_info' ).slideToggle(900);
     });
	 
/*-------------------------
  Create an account toggle function
--------------------------*/
	 $( '#ship-box' ).on('click', function() {
        $( '#ship-box-info' ).slideToggle(1000);
     });	



    /*-------------------------------------------
    	01. jQuery MeanMenu
    --------------------------------------------- */
    jQuery('nav#dropdown').meanmenu();


    /*-------------------------------------------
    	02. wow js active
    --------------------------------------------- */
    new WOW().init();


    /*-------------------------------------------
    	03. scrollUp jquery active
    --------------------------------------------- */
    $.scrollUp({
        scrollText: '<i class="fa fa-angle-up"></i>',
        easingType: 'linear',
        scrollSpeed: 900,
        animation: 'fade'
    });


    /*************************
          tooltip
    *************************/
    $('[data-toggle="tooltip"]').tooltip();


  $( function() {
    $( "#slider-range" ).slider({
      range: true,
      min: 0,
      max: 500,
      values: [ 75, 300 ],
      slide: function( event, ui ) {
        $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
      }
    });
    $( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) +
      " - $" + $( "#slider-range" ).slider( "values", 1 ) );
  } );

    
    /*--
     MailChimp
    ------------------------*/
    $('#mc-form').ajaxChimp({
     language: 'en',
     callback: mailChimpResponse,
     // ADD YOUR MAILCHIMP URL BELOW HERE!
     url: 'http://themeshaven.us8.list-manage.com/subscribe/post?u=759ce8a8f4f1037e021ba2922&amp;id=a2452237f8'
    });
    function mailChimpResponse(resp) {
     if (resp.result === 'success') {
      $('.mailchimp-success').html('' + resp.msg).fadeIn(900);
      $('.mailchimp-error').fadeOut(400);
     } else if(resp.result === 'error') {
      $('.mailchimp-error').html('' + resp.msg).fadeIn(900);
     }  
    }


})(jQuery);

