$a.page(function(){
	//search ux
	this.init = function(){
		//left menu open close
		var btnToggle = $('.Button.btn-toggle');
		$(btnToggle).click(function(e){
			e.preventDefault();

			if(btnToggle.hasClass('lnb-close')){
				$(this).parent().parent().addClass('close');
				$(this).parents().find(".lnb-wrap").addClass('close');
				$(this).removeClass('lnb-close').addClass('lnb-open');
				$(this).text('펼치기');
			}else if(btnToggle.hasClass('lnb-open')){
				$(this).parent().parent().removeClass('close');
				$(this).parents().find(".lnb-wrap").removeClass('close');
				$(this).removeClass('lnb-open').addClass('lnb-close');
				$(this).text('접기');
			};
		});
		
		//left sub menu toggle
		var lnbSub = $('nav > ul > li');
		$(lnbSub).find('.lnb-sub').parent().addClass('expandable');
		if($(lnbSub).hasClass('expandable')){
			var lnbSubExpand = $('nav > ul > li.expandable > a');
			$(lnbSubExpand).click(function(e){
				e.preventDefault();
				$(this).parent().find('.lnb-sub').slideToggle();
				$(this).parent().toggleClass('expanded');
				$(this).toggleClass('selected');
			});
		}; 
		//accordion
		$('.cp-accordion').expand(0);
		$('.alopexgrid').alopexGrid("viewUpdate");
		//tabs grid
		$('.Accordion >li').on('click', function(e, index){
    		$($(e.currentTarget).find('div:visible').find('.alopexgrid')).alopexGrid( "viewUpdate" ); 
    	 });

		//user box click event
		var user = $('.user');
		var userBox = $('.user-menus');
		userBox.hide();
		$(user).click(function(e){
			e.preventDefault();
			if(userBox.css('display') === 'none') {
				userBox.show();
				$(user).addClass('close');
			} else {
				userBox.hide();
				$(user).removeClass('close');
			}
		});
	}
});
