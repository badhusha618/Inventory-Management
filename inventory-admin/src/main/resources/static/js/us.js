var US = window.US || {};

(function(window, document, $, US) {
    "use strict";
	US.globalScript = (function() {

        var globalScripts = function() {

			//bootstrap filestyle
		/* 	$("#UploadImage").filestyle({
				buttonName: "btn-primary",
				buttonText: "Upload",
				size: "sm",
				iconName: "fa fa-upload",
				placeholder: "Upload image here"
			}); */

        };

        /* Return public properties/methods */
        return {
            init: globalScripts()
        };

    }());

}(window, document, jQuery, US));

/* Init utilities function to document load */
jQuery(US.globalScript.init);
