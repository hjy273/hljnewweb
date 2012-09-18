/**
 * jmpopups
 * Copyright (c) 2009 Otavio Avila (http://otavioavila.com)
 * Licensed under GNU Lesser General Public License
 * 
 * @docs http://jmpopups.googlecode.com/
 * @version 0.5.1
 * 
 */


(function(jQuery) {
	var openedPopups = [];
	var popupLayerScreenLocker = false;
    var focusableElement = [];
	var setupJqueryMPopups = {
		screenLockerBackground: "#000",
		screenLockerOpacity: "0.5"
	};

	jQuery.setupJMPopups = function(settings) {
		setupJqueryMPopups = jQuery.extend(setupJqueryMPopups, settings);
		return this;
	};

	jQuery.openPopupLayer = function(settings) {
		if (typeof(settings.name) != "undefined" && !checkIfItExists(settings.name)) {
			settings = jQuery.extend({
				width: "auto",
				height: "auto",
				parameters: {},
				target: "",
				success: function() {},
				error: function() {},
				beforeClose: function() {},
				afterClose: function() {},
				reloadSuccess: null,
				cache: false
			}, settings);
			loadPopupLayerContent(settings, true);
			return this;
		}
	};
	
	jQuery.closePopupLayer = function(name) {
		if (name) {
			for (var i = 0; i < openedPopups.length; i++) {
				if (openedPopups[i].name == name) {
					var thisPopup = openedPopups[i];
					
					openedPopups.splice(i,1);
					
					thisPopup.beforeClose();
					
					jQuery("#popupLayer_" + name).fadeOut(function(){
						jQuery("#popupLayer_" + name).remove();
					
						focusableElement.pop();
	
						if (focusableElement.length > 0) {
							jQuery(focusableElement[focusableElement.length-1]).focus();
						}
	
						thisPopup.afterClose();
						hideScreenLocker(name);
					});
					
					
   
					break;
				}
			}
		} else {
			if (openedPopups.length > 0) {
				jQuery.closePopupLayer(openedPopups[openedPopups.length-1].name);
			}
		}
		
		return this;
	};
	
	jQuery.reloadPopupLayer = function(name, callback) {
		if (name) {
			for (var i = 0; i < openedPopups.length; i++) {
				if (openedPopups[i].name == name) {
					if (callback) {
						openedPopups[i].reloadSuccess = callback;
					}
					
					loadPopupLayerContent(openedPopups[i], false);
					break;
				}
			}
		} else {
			if (openedPopups.length > 0) {
				jQuery.reloadPopupLayer(openedPopups[openedPopups.length-1].name);
			}
		}
		
		return this;
	};

	function setScreenLockerSize() {
		if (popupLayerScreenLocker) {
			jQuery('#popupLayerScreenLocker').height(jQuery(document).height() + "px");
			jQuery('#popupLayerScreenLocker').width(jQuery(document.body).outerWidth(true) + "px");
		}
	};
	
	function checkIfItExists(name) {
		if (name) {
			for (var i = 0; i < openedPopups.length; i++) {
				if (openedPopups[i].name == name) {
					return true;
				}
			}
		}
		return false;
	};
	
	function showScreenLocker() {
		if (jQuery("#popupLayerScreenLocker").length) {
			if (openedPopups.length == 1) {
				popupLayerScreenLocker = true;
				setScreenLockerSize();
				jQuery('#popupLayerScreenLocker').fadeIn();
			}
   
			if (jQuery.browser.msie && jQuery.browser.version < 7) {
				jQuery("select:not(.hidden-by-jmp)").addClass("hidden-by-jmp hidden-by-" + openedPopups[openedPopups.length-1].name).css("visibility","hidden");
			}
   			
			jQuery('#popupLayerScreenLocker').css("z-index",parseInt(openedPopups.length == 1 ? 999 : jQuery("#popupLayer_" + openedPopups[openedPopups.length - 2].name).css("z-index")) + 1);
		} else {
			jQuery("body").append("<div id='popupLayerScreenLocker'><!-- --></div>");
			jQuery("#popupLayerScreenLocker").css({
				position: "absolute",
				background: setupJqueryMPopups.screenLockerBackground,
				left: "0",
				top: "0",
				opacity: setupJqueryMPopups.screenLockerOpacity,
				display: "none"
			});
			showScreenLocker();

            jQuery("#popupLayerScreenLocker").click(function() {
               jQuery.closePopupLayer();
            });
		}
	};
	
	function hideScreenLocker(popupName) {
		if (openedPopups.length == 0) {
			screenlocker = false;
			jQuery('#popupLayerScreenLocker').fadeOut();
		} else {
			jQuery('#popupLayerScreenLocker').css("z-index",parseInt(jQuery("#popupLayer_" + openedPopups[openedPopups.length - 1].name).css("z-index")) - 1);
		}
   
		if (jQuery.browser.msie && jQuery.browser.version < 7) {
			jQuery("select.hidden-by-" + popupName).removeClass("hidden-by-jmp hidden-by-" + popupName).css("visibility","visible");
		}
	};
	
	function setPopupLayersPosition(popupElement, animate) {
		if (popupElement) {
            if (popupElement.width() < jQuery(window).width()) {
				var leftPosition = (document.documentElement.offsetWidth - popupElement.width()) / 2;
			} else {
				var leftPosition = document.documentElement.scrollLeft + 5;
			}

			if (popupElement.height() < jQuery(window).height()) {
				var topPosition = document.documentElement.scrollTop + (jQuery(window).height() - popupElement.height()) / 2;
			} else {
				var topPosition = document.documentElement.scrollTop + 5;
			}
			
			var positions = {
				left: leftPosition + "px",
				top: topPosition + "px"
			};
			
			if (!animate) {
				popupElement.css(positions);
			} else {
				popupElement.animate(positions, "slow");
			}
                        
            setScreenLockerSize();
		} else {
			for (var i = 0; i < openedPopups.length; i++) {
				setPopupLayersPosition(jQuery("#popupLayer_" + openedPopups[i].name), true);
			}
		}
	};

    function showPopupLayerContent(popupObject, newElement, data) {
        var idElement = "popupLayer_" + popupObject.name;

        if (newElement) {
			showScreenLocker();
			
			jQuery("body").append("<div id='" + idElement + "'><!-- --></div>");
			
			var zIndex = parseInt(openedPopups.length == 1 ? 1000 : jQuery("#popupLayer_" + openedPopups[openedPopups.length - 2].name).css("z-index")) + 2;
		}  else {
			var zIndex = jQuery("#" + idElement).css("z-index");
		}

        var popupElement = jQuery("#" + idElement);
		
		popupElement.css({
			visibility: "hidden",
			width: popupObject.width == "auto" ? "" : popupObject.width + "px",
			height: popupObject.height == "auto" ? "" : popupObject.height + "px",
			position: "absolute",
			"z-index": zIndex
		});
		
		var linkAtTop = "<a href='#' class='jmp-link-at-top' style='position:absolute; left:-9999px; top:-1px;'>&nbsp;</a><input class='jmp-link-at-top' style='position:absolute; left:-9999px; top:-1px;' />";
		var linkAtBottom = "<a href='#' class='jmp-link-at-bottom' style='position:absolute; left:-9999px; bottom:-1px;'>&nbsp;</a><input class='jmp-link-at-bottom' style='position:absolute; left:-9999px; top:-1px;' />";

		popupElement.html(linkAtTop + data + linkAtBottom);
		
		setPopupLayersPosition(popupElement);

        popupElement.css("display","none");
        popupElement.css("visibility","visible");
		
		if (newElement) {
        	popupElement.fadeIn();
		} else {
			popupElement.show();
		}

        jQuery("#" + idElement + " .jmp-link-at-top, " +
		  "#" + idElement + " .jmp-link-at-bottom").focus(function(){
			jQuery(focusableElement[focusableElement.length-1]).focus();
		});
		
		var jFocusableElements = jQuery("#" + idElement + " a:visible:not(.jmp-link-at-top, .jmp-link-at-bottom), " +
								   "#" + idElement + " *:input:visible:not(.jmp-link-at-top, .jmp-link-at-bottom)");
						   
		if (jFocusableElements.length == 0) {
			var linkInsidePopup = "<a href='#' class='jmp-link-inside-popup' style='position:absolute; left:-9999px;'>&nbsp;</a>";
			popupElement.find(".jmp-link-at-top").after(linkInsidePopup);
			focusableElement.push(jQuery(popupElement).find(".jmp-link-inside-popup")[0]);
		} else {
			jFocusableElements.each(function(){
				if (!jQuery(this).hasClass("jmp-link-at-top") && !jQuery(this).hasClass("jmp-link-at-bottom")) {
					focusableElement.push(this);
					return false;
				}
			});
		}
		
		jQuery(focusableElement[focusableElement.length-1]).focus();

		popupObject.success();
		
		if (popupObject.reloadSuccess) {
			popupObject.reloadSuccess();
			popupObject.reloadSuccess = null;
		}
    };
	
	function loadPopupLayerContent(popupObject, newElement) {
		if (newElement) {
			openedPopups.push(popupObject);
		}
		
		if (popupObject.target != "") {
            showPopupLayerContent(popupObject, newElement, jQuery("#" + popupObject.target).html());
        } else {
            jQuery.ajax({
                url: popupObject.url,
                data: popupObject.parameters,
				cache: popupObject.cache,
                dataType: "html",
                method: "GET",
                success: function(data) {
                    showPopupLayerContent(popupObject, newElement, data);
                },
				error: popupObject.error
            });
		}
	};
	
	jQuery(window).resize(function(){
		setScreenLockerSize();
		setPopupLayersPosition();
	});
	
//	jQuery(document).keydown(function(e){
//		if (e.keyCode == 27) {
//			jQuery.closePopupLayer();
//		}
//	});
})(jQuery);