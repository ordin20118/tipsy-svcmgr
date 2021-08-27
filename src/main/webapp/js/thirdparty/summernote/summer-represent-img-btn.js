/**
 * Summernote Set Represent Image Plugin
 *
 * copyright 2018 [blood72]
 * email: blood72@naver.com
 * license: MIT License.
 */
(function (factory) {
  if (typeof define === 'function' && define.amd) {
    define(['jquery'], factory);
  } else if (typeof module === 'object' && module.exports) {
    module.exports = factory(require('jquery'));
  } else {
    factory(window.jQuery);
  }
}(function ($) {
	
  // Language에 대한 설정 
  $.extend(true,$.summernote.lang, {
	  'en-US': {  /*US English(Default Language)*/
		  representImage: {
			  title: '대표',
			  tooltip: '대표 이미지로 설정',
		  }
	  },
	  'ko-KR': {
		  representImage: {
			  title: '대표',
			  tooltip: '대표 이미지로 설정',
		  }
	  }
  });
  
  
  // 옵션에 대한 설정 
  $.extend($.summernote.options, {
	  representImage: {
	      customTitle: undefined,
	      customTooltip: undefined,
	      relativeRatio: true,
	  }
  });
  
  // 플러그인 기능 구현 
  $.extend($.summernote.plugins, {
	  
	    /**
	     *  @param {Object} context - context object has status of editor.
	     */
	    'representImage': function (context) {
		      var ui        = $.summernote.ui;
		      var $editable = context.layoutInfo.editable;
		      var options   = context.options;
		      var lang      = options.langInfo;
		      
		      context.memo('button.representImage', function () {
			        var contents = options.representImage.customTitle ? options.representImage.customTitle : lang.representImage.title;
			        var tooltip  = options.representImage.customTooltip ? options.representImage.customTooltip : lang.representImage.tooltip;
			        var button = ui.button({
			          contents: '<span class="note-fontsize-10">' + contents + '</span>',
			          tooltip: tooltip,
			          click: function () {
			            context.invoke('representImage.represent');
			          }
			        });
			        return button.render();
		      });
		      
		      this.represent = function () {
		    	  console.log("[represent image]");
		    	  
		    	  // class 값을 변경하여 대표 이미지로 설정 
		    	  const $target = $($editable.data('target'));
		    	  const representCnt = $($editable).children('p').children('.represent_image').length;
		    	  
		    	  if(representCnt > 0) {
		    		  for(var i=0; i<representCnt; i++) {
		    			  $($editable).children('p').children('.represent_image').eq(i).removeAttr('data-represent');
		    			  $($editable).children('p').children('.represent_image').eq(i).attr('class', '.editor_image');
		    		  }
		    	  }
		    	  
		    	  $target.attr('class', 'represent_image');
		    	  $target.attr('data-represent', 'true');
		    	  context.invoke('editor.afterCommand');
		    	 
		      }
	     }
  });
  
}));