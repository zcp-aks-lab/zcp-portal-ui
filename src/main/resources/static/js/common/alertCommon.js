function isValidURL(url) {
	var RegExp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;

	if (RegExp.test(url)) {
		return true;
	} else {
		return false;
	}
}

function comboOptions(id, data, comboText) {
	try {
		$('#' + id).find("option:eq(0)").prop("selected", true);
		$('#' + comboText).text('Pod 선택');
		
		$('#' + id).find('option').each(function() {
			$(this).remove();
		});

		var option = [];
		option.push("<option value=\"\">Pod 선택</option>");

		if (data == '' || data == null) {
			option.push("<option value=\"\">데이타가 없습니다.</option>");
		}

		for (var i = 0; i < data.length; i++) {
			option.push("<option value=\"" + data[i] + "\"");
			option.push(">" + data[i] + "</option>");
		}
		$('#' + id).append(option.join(''));
	} catch (e) {
	}
}

var alertPopup = {
	default_opts : {
		title : '알림',
		message : '',
		width : 550,
		height : 450,
		callback : function(data) {
			check.callback(data);
		},
		alias : 'popup'
	},

	alert : function(opts) {
		this.open(opts, 'alert');
	},

	confirm : function(opts) {
		this.open(opts, 'confirm');
	},

	open : function(opts, type) {
		var _opts = $.extend({}, this.default_opts, opts);

		$a.popup({
			url : "/alert/popCommon",
			iframe : false,
			width : _opts.width,
			height : _opts.height,
			title : _opts.title,
			data : {
				message : _opts.message,
				type : type
			},
			callback : function(data) {
				_opts.callback(data);
			},
			alias : _opts.alias,
			xButtonClickCallback : function(el) {
				if (el.alias === _opts.alias) {
					return true;
				}
			}
		});
	}
};

var channelPopup = {
	default_opts : {
		title : '신규 채널',
		message : '',
		width : 550,
		height : 350,
		callback : function(data) {
			check.callback(data);
		},
		alias : 'popup'
	},

	pop : function(opts) {
		this.open(opts);
	},

	open : function(opts) {
		var _opts = $.extend({}, this.default_opts, opts);

		$a.popup({
			url : "/alert/popChannelAdd",
			iframe : false,
			width : _opts.width,
			height : _opts.height,
			title : _opts.title,
			data : null,
			callback : function(data) {
				if (data == 'ok') {
					alertPopup.alert({
						width : 400,
						height : 250,
						message : '신규 채널이 생성되었습니다.',
						callback : function() {
							_opts.callback(data);
							location.reload();
						}
					});
				} else {
				}
			},
			alias : _opts.alias,
			xButtonClickCallback : function(el) {
				if (el.alias == _opts.alias) {
					return true;
				}
			}
		});
	}
};

var notificationPopup = {
	default_opts : {
		title : '신규 Notification',
		message : '',
		width : 550,
		height : 350,
		callback : function(data) {
			check.callback(data);
		},
		alias : 'popup'
	},

	pop : function(opts) {
		this.open(opts);
	},

	open : function(opts) {
		var _opts = $.extend({}, this.default_opts, opts);

		$a.popup({
			url : _opts.url,
			iframe : false,
			width : _opts.width,
			height : _opts.height,
			title : _opts.title,
			data : _opts.data,
			callback : function(data) {
				if (data == 'ok') {
					alertPopup.alert({
						width : 400,
						height : 250,
						message : 'Notification이 저장되었습니다.',
						callback : function() {
							_opts.callback(data);
							location.reload();
						}
					});
				} else {
				}
			},
			alias : _opts.alias,
			xButtonClickCallback : function(el) {
				if (el.alias == _opts.alias) {
					return true;
				}
			}
		});
	}
};