Date.prototype.customFormat = function(formatString){
  var YYYY,YY,MMMM,MMM,MM,M,DDDD,DDD,DD,D,hhhh,hhh,hh,h,mm,m,ss,s,ampm,AMPM,dMod,th;
  YY = ((YYYY=this.getFullYear())+"").slice(-2);
  MM = (M=this.getMonth()+1)<10?('0'+M):M;
  MMM = (MMMM=["January","February","March","April","May","June","July","August","September","October","November","December"][M-1]).substring(0,3);
  DD = (D=this.getDate())<10?('0'+D):D;
  DDD = (DDDD=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"][this.getDay()]).substring(0,3);
  th=(D>=10&&D<=20)?'th':((dMod=D%10)==1)?'st':(dMod==2)?'nd':(dMod==3)?'rd':'th';
  formatString = formatString.replace("#YYYY#",YYYY).replace("#YY#",YY).replace("#MMMM#",MMMM).replace("#MMM#",MMM).replace("#MM#",MM).replace("#M#",M).replace("#DDDD#",DDDD).replace("#DDD#",DDD).replace("#DD#",DD).replace("#D#",D).replace("#th#",th);
  h=(hhh=this.getHours());
  if (h==0) h=24;
  if (h>12) h-=12;
  hh = h<10?('0'+h):h;
  hhhh = hhh<10?('0'+hhh):hhh;
  AMPM=(ampm=hhh<12?'am':'pm').toUpperCase();
  mm=(m=this.getMinutes())<10?('0'+m):m;
  ss=(s=this.getSeconds())<10?('0'+s):s;
  return formatString.replace("#hhhh#",hhhh).replace("#hhh#",hhh).replace("#hh#",hh).replace("#h#",h).replace("#mm#",mm).replace("#m#",m).replace("#ss#",ss).replace("#s#",s).replace("#ampm#",ampm).replace("#AMPM#",AMPM);
};



function ajaxCall(url, data){
	return $.ajax({
		method: 'post',
		contentType : 'application/json;charset=UTF-8',
		url: url,
		data: JSON.stringify(data)
    }).fail(function(jqXHR, textStatus, errorThrown) {
    	console.log(jqXHR);
        //alert('처리 중 오류가 발생하였습니다. 잠시 후 다시 시도하세요.');
    });
}


var secretPopup = {
		default_opts : {
			title : '신규 Secret',
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
							message : 'Secret 저장되었습니다.',
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