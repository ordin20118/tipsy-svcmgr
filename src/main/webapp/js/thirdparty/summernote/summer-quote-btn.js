/**
 * Summernote Set Quote Plugin
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
	  'ko-KR': {
		  addQuote: {
			  title: '인용구',
			  tooltip: '인용구 추가',
		  }
	  },
	  'en-US': {  /*US English(Default Language)*/
		  addQuote: {
			  title: 'Quote',
			  tooltip: 'Add quote',
		  }
	  }
  });
  
  
  // 플러그인 기능 구현 
  $.extend($.summernote.plugins, {
	  
	    /**
	     *  @param {Object} context - context object has status of editor.
	     */
	    'addQuote': function (context) {
		      var ui        = $.summernote.ui;
		      var $editable = context.layoutInfo.editable;
		      var options   = context.options;
		      var lang      = options.langInfo.addQuote.title;
		      var tooltip   = options.langInfo.addQuote.tooltip;
		      var items		= options.addQuote.items;		      
		      
		      context.memo('button.addQuote', function () {
			      		    	  	
		    	    var contents = options.title;
			        var button = ui.buttonGroup([
			            ui.button({
			                className: 'dropdown-toggle',
			                contents: '<span class="note-fontsize-10">' + lang + '</span>',
			                tooltip: tooltip,
			                data: {
			                    toggle: 'dropdown'
			                },
	                        click: function () {
	                            context.invoke('editor.saveRange');
	                        }
			            }),
			            ui.dropdown({
			                className: 'drop-default summernote-list',
			                items: items,
			                click: function(event) {
			                	console.log("[quote list click]");
			                },
			                template: function(item) {
			                	console.log(item);
			                	var content = item.content;
			                	var value = item.value;
			                	
			                	if(value == "quote1") {
			                		content = "<div class='blockquote1-1'/></br><div class='blockquote1-2'/>";
			                	} else if (value == "quote2") {
			                		content = "<div class='blockquote2-1'/><div class='blockquote2-2'/>";
			                	} else if (value == "quote3") {
			                		
			                	}
			                	
			                	return content;
			                }
			            })
			        ]);        

			        return button.render();    
		      
		      });
		      
		      
		      // This methods will be called when editor is destroyed by $('..').summernote('destroy');
	          // You should remove elements on `initialize`.
	          this.destroy = function() {

	          };
		      
	          function addQuote() {
	        	  
	          }
		     
	     }
  });
  
}));