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

		//on off button
		var onoff = $('.onoff-wrap > button');
		$(onoff).click(function(e){
			e.preventDefault();
			if($(this).hasClass('btn-on')) {
				$(this).removeClass('btn-on').addClass('btn-off');
				$(this).text('OFF');
			} else {
				$(this).removeClass('btn-off').addClass('btn-on');
				$(this).text('ON');
			}
		})

		//popup
		$('#pop-temp1').click( function() {
		    $a.popup({
		        url: "zcp-pop01.html",
		        iframe: false,  // default 는 true
		        width: 826,
		        height: 480,
		        title : "패키지 삭제",
		    });
		});
		$('#pop-temp2').click( function() {
		    $a.popup({
		        url: "zcp-pop02.html",
		        iframe: false,  // default 는 true
		        width: 826,
		        height: 480,
		        title : "패키지 삭제",
		    });
		});
		$('#pop-temp3').click( function() {
		    $a.popup({
		        url: "zcp-pop03.html",
		        iframe: false,  // default 는 true
		        width: 826,
		        height: 480,
		        title : "오류 데이터 목록",
		    });
		});
	}
});
