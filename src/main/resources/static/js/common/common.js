function log(str) {
	if (typeof console === 'undefined') {
		return false;
	}
	;

	console.log(str);
}

/**
 * desc : form의 데이터를 json 형태로 변환해 준다. return : 성공시에는 객체(JSON)을 리턴한다. 실패시에는 null을
 * 리턴한다.
 */
jQuery.fn.serializeObject = function() {
	var obj = null;
	try {
		if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
			var arr = this.serializeArray();
			if (arr) {
				obj = {};
				jQuery.each(arr, function() {
					obj[this.name] = this.value;
				});
			}// if ( arr ) {
		}
	} catch (e) {
		alert(e.message);
	} finally {
	}

	return obj;
};

function createOptions(id, data, selected, disabled) {
    try {
    	if(data.length > 0) {
    		
    		if(disabled == null) {
    			$('#'+id).removeAttr("disabled");	
    		} 
    		
    		$('#'+id).find('option').each(function() {
                $(this).remove();
            });	
    	}

    	var option = [];
    	for(var i = 0; i < data.length; i++) {
    		option.push("<option value=\"" + data[i].value + "\"");
			if (selected == data[i].value) {
    			option.push(" selected ");
			}
			option.push(">" + data[i].text + "</option>");
		}
    	$('#' + id).append(option.join(''));
    } catch(e) {}
}

var commonPopup = {
	default_opts: {
        title: '알림',
        message: '',
        width: 550,
        height: 450,
        callback: function() {},
        alias: 'popup'
    },
		
	alert: function(opts) {
		this.open(opts, 'alert');
		
	},
	
	confirm: function(opts) {
		this.open(opts, 'confirm');
	},
	
	open: function(opts, type) {
		var _opts = $.extend({}, this.default_opts, opts);
		
		$a.popup({
	        url: "/common/popup/popup",
	        iframe: false,  // default 는 true
	        width: _opts.width,
	        height: _opts.height,
	        title: _opts.title,
	        data: {message : _opts.message, type: type},
	        callback: function(data) {
	            if (data === 'ok' && typeof(_opts.callback) === 'function') {
	                _opts.callback();
	            }
	        },
	        alias: _opts.alias,
	        xButtonClickCallback : function(el) {
	            if (el.alias === _opts.alias) {
	                return true;
	            }
	        }
	    });
	}
};

function goUrl(url) {
	 var openNewWindow = window.open("about:blank");
	 var pReg = /^(http|https)/i
	 var isHttp =  pReg.test(url);
	 url = (!isHttp)?"http://"+url:url;
	 openNewWindow.location.href = url;
}

function getCookie(key){
    var val = $.cookie(key);
    return val;
}

function setCookie(name, value, expiredays) {
    $.cookie(name, value, { expires: expiredays, path: '/' });
}

function chkPwd(str) {
	var pw = str;
	var num = pw.search(/[0-9]/g);
	var eng = pw.search(/[a-z]/ig);
	var spe = pw.search(/[!@#$%^*_-]/gi);

	if (pw.length < 8 || pw.length > 20) {
		//alert("8자리 ~ 20자리 이내로 입력해주세요.");
		return false;
	}

	if(pw.search(/₩s/) != -1){
	  //alert("비밀번호는 공백업이 입력해주세요.");
	  return false;
	} 
	
	if(num < 0 || eng < 0 || spe < 0 ){
	  //alert("영문,숫자, 특수문자를 혼합하여 입력해주세요.");
	  return false;
	}
	 
	return true;
}

function moveMainNamespace(namespace) {
	location.href = '?namespace=' + namespace;
}

// constants
var constants = {
	result: {
		SUCCESS: '0000',
		ERROR: '9999'
	}
}

