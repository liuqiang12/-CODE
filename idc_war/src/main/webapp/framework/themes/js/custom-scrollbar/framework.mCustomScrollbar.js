(function($){
			$(window).on("load",function(){
				
				//$.mCustomScrollbar.defaults.scrollButtons.enable=true; //enable scrolling buttons by default
				//$.mCustomScrollbar.defaults.axis="yx"; //enable 2 axis scrollbars by default
				
				$("#bs_left").mCustomScrollbar({theme:"dark-thin"});
				//$("#bs_right").mCustomScrollbar({theme:"dark-thin"});
				 $("#bs_left").mCustomScrollbar({
				   horizontalScroll:true
				 }); 
			});
		})(jQuery);