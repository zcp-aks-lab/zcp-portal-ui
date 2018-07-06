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
						message : '저장되었습니다.',
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
			
			/*var data = {
					id : id
				};*/

			$a.popup({
				url : "/alert/popNotificationAdd",
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
							message : '저장되었습니다.',
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