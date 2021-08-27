//Common Ready Script
jQuery(document).ready(function () {

	//스크롤탑 버튼 제어
	jQuery(".btn_top").click(function(){jQuery("html, body").animate({scrollTop:0},'fast');});
	jQuery(".bottom_wrap").hide();
	jQuery(window).scroll(function () { offsetTop(); });

	//offsetTop Bottom
	function offsetTop() {
		var offsetT1 = jQuery(window).scrollTop();
		if (offsetT1 <= 10) jQuery(".bottom_wrap").hide();
		else if (offsetT1 > 10) jQuery(".bottom_wrap").show();
	}

	//레이어 스크롤 위치에 맞춰 노출/제거
	var posY;
    
	jQuery('.layer_on').click(function(e){
		posY = $(window).scrollTop();

		jQuery("html, body").addClass("not_scroll");
		jQuery(".wrap_layer").css("display","block");
		jQuery(".wrap_total").css("top",-posY);
	});

	jQuery('.layer_off').click(function(){            
		jQuery("html, body").removeClass("not_scroll");
		jQuery(".wrap_layer").css("display","none");
		posY = $(window).scrollTop(posY);
	});

	//레이어 스크롤 위치에 맞춰 노출/제거    
	jQuery('.evalue_show').click(function(e){
		posY = $(window).scrollTop();
		wHeight = window.innerHeight;	

		jQuery("html, body").addClass("not_scroll");
		jQuery(".wrap_layer_f").css({position:"absolute",display:"block",overflow:"auto",height:wHeight,top:"posY"});
	});

	jQuery('.evalue_hide').click(function(){            
		jQuery("html, body").removeClass("not_scroll");
		jQuery(".wrap_layer_f").css("display","none");
		posY = $(window).scrollTop(posY);
	});

	//좌우 스와이핑 제어
	$(".regular").slick({
		dots: true,
		infinite: true,
		slidesToShow: 2,
		slidesToScroll: 2
	});

	//화면 축소/확대
	jQuery('.div_red_1').hide();
    jQuery('.div_exp_1').click(function(){  
        jQuery('.p_evalue').addClass("div_box_exp1");          
        jQuery('.div_red_1').show();
        jQuery('.div_exp_1').hide();
    });
    jQuery('.div_red_1').click(function(){  
        jQuery('.p_evalue').removeClass("div_box_exp1");          
        jQuery('.div_red_1').hide();
        jQuery('.div_exp_1').show();
	});
    jQuery('.div_red_2').hide();
    jQuery('.div_exp_2').click(function(){  
        jQuery('.p_intro').addClass("div_box_exp2");          
        jQuery('.div_red_2').show();
        jQuery('.div_exp_2').hide();
    });
    jQuery('.div_red_2').click(function(){  
        jQuery('.p_intro').removeClass("div_box_exp2");          
        jQuery('.div_red_2').hide();
        jQuery('.div_exp_2').show();
	});

	//별점 제어
	jQuery('.starRev span').mouseover(function(){
		jQuery(this).parent().children('span').removeClass('on');
		jQuery(this).addClass('on').prevAll('span').addClass('on');
		return false;
	});

	//Gage 제어
	jQuery('.gageRev span').mouseover(function(){
		jQuery(this).parent().children('span').removeClass('on');
		jQuery(this).addClass('on').prevAll('span').addClass('on');
		return false;
	});
	
});

jQuery(window).resize(function () {


});