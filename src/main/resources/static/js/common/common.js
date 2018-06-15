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