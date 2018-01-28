var Tween = {
	linear: function (t, b, c, d){
		return c*t/d + b;
	},
	easeIn: function(t, b, c, d){
		return c*(t/=d)*t + b;
	},
	easeOut: function(t, b, c, d){
		return -c *(t/=d)*(t-2) + b;
	},
	easeBoth: function(t, b, c, d){
		if ((t/=d/2) < 1) {
			return c/2*t*t + b;
		}
		return -c/2 * ((--t)*(t-2) - 1) + b;
	},
	easeInStrong: function(t, b, c, d){
		return c*(t/=d)*t*t*t + b;
	},
	easeOutStrong: function(t, b, c, d){
		return -c * ((t=t/d-1)*t*t*t - 1) + b;
	},
	easeBothStrong: function(t, b, c, d){
		if ((t/=d/2) < 1) {
			return c/2*t*t*t*t + b;
		}
		return -c/2 * ((t-=2)*t*t*t - 2) + b;
	},
	elasticIn: function(t, b, c, d, a, p){
		if (t === 0) { 
			return b; 
		}
		if ( (t /= d) == 1 ) {
			return b+c; 
		}
		if (!p) {
			p=d*0.3; 
		}
		if (!a || a < Math.abs(c)) {
			a = c; 
			var s = p/4;
		} else {
			var s = p/(2*Math.PI) * Math.asin (c/a);
		}
		return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
	},
	elasticOut: function(t, b, c, d, a, p){
		if (t === 0) {
			return b;
		}
		if ( (t /= d) == 1 ) {
			return b+c;
		}
		if (!p) {
			p=d*0.3;
		}
		if (!a || a < Math.abs(c)) {
			a = c;
			var s = p / 4;
		} else {
			var s = p/(2*Math.PI) * Math.asin (c/a);
		}
		return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
	},    
	elasticBoth: function(t, b, c, d, a, p){
		if (t === 0) {
			return b;
		}
		if ( (t /= d/2) == 2 ) {
			return b+c;
		}
		if (!p) {
			p = d*(0.3*1.5);
		}
		if ( !a || a < Math.abs(c) ) {
			a = c; 
			var s = p/4;
		}
		else {
			var s = p/(2*Math.PI) * Math.asin (c/a);
		}
		if (t < 1) {
			return - 0.5*(a*Math.pow(2,10*(t-=1)) * 
					Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
		}
		return a*Math.pow(2,-10*(t-=1)) * 
				Math.sin( (t*d-s)*(2*Math.PI)/p )*0.5 + c + b;
	},
	backIn: function(t, b, c, d, s){
		if (typeof s == 'undefined') {
		   s = 1.70158;
		}
		return c*(t/=d)*t*((s+1)*t - s) + b;
	},
	backOut: function(t, b, c, d, s){
		if (typeof s == 'undefined') {
			s = 2.70158;  //回缩的距离
		}
		return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
	}, 
	backBoth: function(t, b, c, d, s){
		if (typeof s == 'undefined') {
			s = 1.70158; 
		}
		if ((t /= d/2 ) < 1) {
			return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
		}
		return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
	},
	bounceIn: function(t, b, c, d){
		return c - Tween['bounceOut'](d-t, 0, c, d) + b;
	},       
	bounceOut: function(t, b, c, d){
		if ((t/=d) < (1/2.75)) {
			return c*(7.5625*t*t) + b;
		} else if (t < (2/2.75)) {
			return c*(7.5625*(t-=(1.5/2.75))*t + 0.75) + b;
		} else if (t < (2.5/2.75)) {
			return c*(7.5625*(t-=(2.25/2.75))*t + 0.9375) + b;
		}
		return c*(7.5625*(t-=(2.625/2.75))*t + 0.984375) + b;
	},      
	bounceBoth: function(t, b, c, d){
		if (t < d/2) {
			return Tween['bounceIn'](t*2, 0, c, d) * 0.5 + b;
		}
		return Tween['bounceOut'](t*2-d, 0, c, d) * 0.5 + c*0.5 + b;
	}
};
//获取设置css样式
function css(element, attr , val){
	if(attr == "scale"||attr == "scaleX"
	||attr == "scaleY"||attr == "scaleZ"
	||attr == "rotateX"||attr == "rotateY"
	||attr == "rotateZ"||attr == "rotate"
	||attr == "skewX"||attr == "skewY"
	||attr == "translateX"||attr == "translateY"
	||attr == "translateZ") {
		return cssTransform(element, attr, val);
	}
	if(arguments.length == 2){
		if(element.currentStyle){
			var val= element.currentStyle[attr];
		}else{
			var val= getComputedStyle(element,false)[attr];
		}
		if(attr=='opacity'){
			val = Math.round(val*100);
		}
		return parseFloat(val);
	} 
	if(attr == "opacity") {
		element.style.opacity= val/100;
		element.style.filter='alpha(opacity:'+val+')';
	} else {
		element.style[attr]= val + "px";	
	}
}
function cssTransform(element, attr, val){
	if(!element.transform){
		element.transform = {};
	}	
	if(typeof val === "undefined"){ 
		if(typeof element.transform[attr] === "undefined"){
			switch(attr){
				case "scale":
				case "scaleX":
				case "scaleY":
				case "scaleZ":
					element.transform[attr] = 100;
					break;
				default:
					element.transform[attr] = 0;	
			}
		} 
		return element.transform[attr];
	} else {
		element.transform[attr] = val;
		var transformVal  = "";
		for(var s in element.transform){
			switch(s){
				case "scale":
				case "scaleX":
				case "scaleY":
				case "scaleZ":
					transformVal += " " + s + "("+(element.transform[s]/100)+")";
					break;
				case "rotate":
				case "rotateX":
				case "rotateY":
				case "rotateZ":
				case "skewX":
				case "skewY":
					transformVal += " " + s + "("+element.transform[s]+"deg)";
					break;
				default:
					transformVal += " " + s + "("+element.transform[s]+"px)";				
			}
		}
		element.style.WebkitTransform = element.style.transform = transformVal;
	}
}
/*
 * 
 * //动画类型
	linear/easeIn/easeOut/easeBoth/
	easeInStrong/easeOutStrong/easeBothStrong/
	elasticIn/elasticOut/elasticBoth/backIn/backOut/
	backBoth/bounceIn/bounceOut/bounceBoth
	
	var div=document.getElementById('div');
	
	MTween({
		el: div,  //对象元素，只能有一个，传已经获取的元素，不然就是传document.getElementById(obj);
		time: 1000,  //动画执行结束的时间
		target: {   //变化的值，可以传多个值
			width: 200
		},
		type: "easeOut",   //动画的类型
		callBack: function(){    //动画执行完后的回调函数
			console.log("动画执行完了");
		},
		callIn: function(){  //和动画同时执行的函数
			console.log("动画正在执行");
		}
	});
	
*/
function MTween(init){
	var t = 0;
	var b = {};
	var c = {};
	var d = init.time / 20;
	for(var s in init.target){ 
		b[s] = css(init.el, s); 
		c[s] = init.target[s] - b[s];
	}
	clearInterval(init.el.timer); 
	init.el.timer = setInterval(
		function(){
			t++;
			if(t>d){
				clearInterval(init.el.timer);
				init.callBack&&init.callBack.call(init.el);
			} else {
				init.callIn&&init.callIn.call(init.el);
				for(var s in b){
					var val = Number((Tween[init.type](t,b[s],c[s],d)).toFixed(2));
					css(init.el, s, val);
				}
			}
		},
		16
	);
}


//添加绑定事件，兼容所有浏览器，修正ie8下this指向问题
 function addEvent(object,type,handler,remove){
    if(typeof object!='object'||typeof handler!='function') return;
    try{
            object[remove?'removeEventListener':'addEventListener'](type,handler,false);
    }catch(e){
        var xc='_'+type;
        object[xc]=object[xc]||[];
        if(remove){
            var l=object[xc].length;
            for(var i=0;i<l;i++){
                    if(object[xc][i].toString()===handler.toString()) object[xc].splice(i,1);
            }
        }else{
            var l=object[xc].length;
            var exists=false;
            for(var i=0;i<l;i++){                                                
                    if(object[xc][i].toString()===handler.toString()) exists=true;
            }
            if(!exists) object[xc].push(handler);
        }
        object['on'+type]=function(){
            var l=object[xc].length;
            for(var i=0;i<l;i++){
                    object[xc][i].apply(object,arguments);
            }
        }
    }
};
//删除事件
function removeEvent(object,type,handler){
    addEvent(object,type,handler,true);
};




//阻止事件冒泡
function stopPropagation(e){
    e=window.event||e;
    if(document.all){  //只有ie识别
        e.cancelBubble=true;
    }else{
        e.stopPropagation();
    }
}

//自己滚动的时候页面不会跟着滚动
function stopScroll(el){
	addEvent(el,'mousewheel',function(e){
		scrollFn(e);
	});
	addEvent(el,'DOMMouseScroll',function(e){
		scrollFn(e);
	});
	function scrollFn(e){
		var direction = scrollDirection(e);
		if(direction == false){
			if(el.scrollHeight - (el.scrollTop+el.offsetHeight) < 1){
				preventScroll();
				//el.scrollTop = el.scrollHeight - el.offsetHeight - 1;
			};
		}else{
			if(el.scrollTop < 1){
				preventScroll();
				//el.scrollTop = 1;
			};
		};
		if(Math.floor(el.scrollTop) <= 0 && direction == false){
			allowScroll();
		};
		
		if(Math.floor(el.scrollHeight - (el.scrollTop+el.offsetHeight))  <= 0 && direction == true){
			allowScroll();
		};
	};
	function preventScroll(){
		addEvent(document,'mousewheel',preventScrollfn);
		addEvent(document,'DOMMouseScroll',preventScrollfn);
		setTimeout(function(){
			allowScroll()
		},100)
	};
	function allowScroll(){
		removeEvent(document,'mousewheel',preventScrollfn);
		removeEvent(document,'DOMMouseScroll',preventScrollfn);
	};
	function preventScrollfn(ev){
		try{
			ev.preventDefault();
		}catch(e){
			//consloe.log(e)
		};
	};
};



(function(){
	//鼠标点击透明度变化，class 等于 onbtn的元素
	function template(e,value){
		var e = e || window.event;
		var target = e.target;
		try{
			if(target.className.search('onbtn') >= 0){
				target.style.opacity = value;
			}
		}catch(e){
			
		}
	}
	document.addEventListener('mousedown',function(e){
		template(e,'0.6');
	});
	document.addEventListener('mousemove',function(e){
		template(e,'1');
	});
	document.addEventListener('mouseup',function(e){
		template(e,'1');
	});
})();



(function(){
	//表单验证
	//正则
	var rep = {
		"Notempty":/^[\u4E00-\u9FA5-\w\.]+$/,  //要求内容不能为空（中文，字母，小数点，数字组成）
		"digital":/^\d{4,}$/,                            //4位数字
		"NonChinese":/^[A-Za-z0-9]+$/
	};
	var Myjson;
	var Mymsg;
	function verificationl(json){
		var msg = {};
		for(var attr in json.messages){
			msg[attr] = json.messages[attr];
		};
		Myjson = json;
		Mymsg = msg;
		//focusin
		//focusout 
		document.addEventListener('focusout', function(e) {
			var e = e || window.Event();
			var target = e.target;
			setTimeout(function() {
				for(var attr in json.rules){
					if(target.name == attr){
						if(!rep[json.rules[attr]].test(target.value)){
							layer.tips(msg[attr], target,{tipsMore: true});
						}
						return;
					};
				};
			},1000);
		});
	};
	//提交的时候调用这个方法验证
	function SubmitVerification(){
		var result = true;
			layer.closeAll("tips");
		for(var attr in Myjson.rules){
			var value;
			var el = document.querySelectorAll("[name="+attr+"]");
			for(var i = 0;i<el.length;i++){
				if(!rep[Myjson.rules[attr]].test(el[i].value)){
					layer.tips(Mymsg[attr], el[i],{tipsMore: true});
					result = false;
					el[i].focus();
					return result;
				}
			};
		};
		//return result;
	};
	window.SubmitVerification = SubmitVerification;
	window.verificationl = verificationl;
	
})();


//增加和删除表单一排
(function(){
	var tr2= '';
	function addTr(el,isAir){ //el,增加一行table的选择器，isAir增加前是否判断值为空，为空不添加
		el = document.querySelector(el);
		var tbody = el.tBodies[0];
		var tr1 = tbody.querySelector('tr');
		if(!isAir){
			for(var i = 0;i<tbody.querySelectorAll('.Notempty').length;i++){
				 if(tbody.querySelectorAll('.Notempty')[i].value == ""){
					tbody.querySelectorAll('.Notempty')[i].focus();
					parent.layer.msg("表单未填写完整",{
						time:1000
					});
					return;
				} 
			}
		};
		if(tr1 != null){
			tr2 = tr1.innerHTML;
		}
		var tr = document.createElement('tr');
		
		tr.innerHTML = "<tr>"+tr2+"</tr>";
		tbody.insertBefore(tr,tr1);
	};

	function removeTr(el){
		el = document.querySelector(el);
		var input = el.tBodies[0].querySelectorAll('input');
		tr2 = el.tBodies[0].querySelector('tr').innerHTML;
		for(var i=0;i<input.length;i++){
			if(input[i].checked == true){
				var tr = input[i].parentNode.parentNode;
				tr.parentNode.removeChild(tr);
			}
		};
	};
	
	window.addTr = addTr;
	window.removeTr = removeTr;
})();	

//調出輸入框
function inputLayer(el){
	
	var judgmentClass = "L_container";
	
	document.addEventListener('click',function(e){
		var judgment = document.querySelectorAll("."+judgmentClass);
		if(judgment != null){
			for(var i = 0;i<judgment.length;i++){
				judgment[i].parentNode.removeChild(judgment[i]);
			}
		};
		var e = e || window.event;
		var target = e.target || e.srcElement;
		//根据id,class,标签名name执行不同的代码
		if(el.charAt(0) == '.'){
			ifClass(target);
		}else if(el.charAt(0) == '#'){
			ifId(target);
		}else{
			ifName(target);
		};
		function ifId(target){
			if(target.id.toLowerCase() == el.substring(1)){
				template(target);
			};
		}
		function ifClass(target){
			if(target.className.search(el.substring(1)) >=0){
				template(target);
			};
		}
		function ifName(target){
			if(target.nodeName.toLowerCase() == el){
				template(target);
			};
		}
	});

	function template(target,el){
		if(target.querySelector(judgmentClass) != null){
			return;
		};
		
		
		var value = target.innerHTML;
		
		target.style.position = "relative";
		var div = document.createElement("div");
			div.className = judgmentClass;
			div.style.top = -85 + 'px';
			div.style.left = (-141+(target.offsetWidth/2)) + 'px';
			
		var title = target.getAttribute("data-title") || "请输入";
		var placeholder = target.getAttribute("data-placeholder") || "";
		
		var html  = '<div class="L_containerTitle">'+title+'</div>';
			html += '<div class="L_containerContent">';
			html += '<div class="L_containerInput">';
			html += '<input type="text" placeholder="'+placeholder+'" />';
			html += '</div>';
			html += '<div class="L_containerBtn">';
			html += '<button type="button" class="active L_btn1">确定</button>';
			html += '<button type="button" class="L_btn2">取消</button>';
			html += '<div style="clear: both;"></div>';
			html += '</div>';
			html += '<div style="clear: both;"></div>';
			html +='<div class="L_arrow"></div>';
			html += '</div>';	
		
			div.innerHTML = html; 
			target.appendChild(div);
		
		var This = target;
		
		function removeDiv(){
			try{
				target.removeChild(div);
			}catch(e){
				
			}
		}
		var L_input = div.querySelector("input");
			L_input.value = value;
			L_input.focus();
			L_input.addEventListener('keyup',function(e){
				var e = e || window.event;
				if(e.keyCode == 13 && L_input.value !=""){
					target.innerHTML = L_input.value;
					removeDiv();
				}
			});
		
		var L_btn1 = div.querySelector(".L_btn1");
		var L_btn2 = div.querySelector(".L_btn2");
			L_btn1.addEventListener('click',function(){
				if(L_input.value !=""){
					target.innerHTML = L_input.value;
				}
			});
			L_btn2.addEventListener('click',function(){
				removeDiv();
			});
			
		div.addEventListener('click',function(e){
			var e = e || window.event;
				e.stopPropagation();
		});
	}
}

//自定义下拉框部分
(function(){
	
	var selectIndex = 100;
	
	function template(e,fn){
		var e = e || window.event;
		var target = e.target;
		fn&&fn(target);
	};
	
	function preventKeyUp(e){
		e.preventDefault();
	}
	
	//获取数据
	function getData(target){
		/*if(!(target.getAttribute("data-isEnter") == "true")){
			target.addEventListener('keydown',preventKeyUp);
		};*/
		var myDiv = target.parentNode;
		var url = myDiv.getAttribute("data-url") || "";
		var type = myDiv.getAttribute("data-type") || "";
		if(url==""){
			alert("地址错误")
			return;
		}
		$.ajax({
			 url:url,
			 type:'POST', 
			 async:false,    //或false,是否异步
			 dataType:'json',
			 data:{
				type:type
			 },
		     success:function(data){
		    	addData(myDiv,target,data);
		    }
		}); 
	};
	
	function addData(myDiv,target,data){
		target.isDate = true;
     	var Options = data.body.Options;
     	var len = Options.length;
     	var oUl = document.createElement("ul");
     		oUl.className= "L_mySelect";
     	
     	for(var i=0;i<len;i++){
     		var value = Options[i].value;
     		var html = Options[i].label;
     		var li = document.createElement('li');
     			li.innerHTML = html;
     			li.hideValue = value;
     			li.addEventListener('click',function(e){
					target.value = this.innerHTML;
					target.hideValue = this.hideValue;
					e.preventDefault();
				});
     			oUl.appendChild(li);
     	};
     	oUl.style.top = target.offsetHeight/2 + 'px';
		oUl.style.width = target.offsetWidth + 'px';
     	myDiv.appendChild(oUl);
     	open(target);
	}
	
	function open(target){
		selectIndex++;
		target.nextElementSibling.style.zIndex = selectIndex;
		target.nextElementSibling.style.display = 'block';
		MTween({
			el: target.nextElementSibling,  
			time: 300,  
			target: {   
				top:target.offsetHeight - 1,
				opacity:100
			},
			type: "easeOut"
		});
	};
	
	function closeUl(target){
		/*if(!(target.getAttribute("data-isEnter") == "true")){
			target.removeEventListener('keydown', preventKeyUp);
		};*/
		target.blur();
		MTween({
			el: target.nextElementSibling,  
			time: 300,  
			target: {   
				top:target.offsetHeight/2,
				opacity:0
			},
			type: "easeOut",  
			callBack: function(){
				target.parentNode.removeChild(target.nextElementSibling);
			}
		});
	};
	
	document.addEventListener('focusin',function(e){
		template(e,function(target){
			if(target.getAttribute("data-for") == "select" ){
				getData(target);
			};
		});

	});
	
	document.addEventListener('focusout',function(e){
		template(e,function(target){
			if(target.getAttribute("data-for") == "select" ){
				setTimeout(function(){
					closeUl(target);
				},150);
			};
		});
	});
	
})();

//获取表格数据，第一个传表格元素，第二个传对应的key
function getTableData(tableEl,arr){
	var values = [];
	var row = tableEl.tBodies[0].rows;
	var cellslen = row[0].querySelectorAll("[name]").length;
	for(var i=0;i<row.length;i++){
		var jsonValue = {};
		for(var j = 0;j<arr.length;j++){
			if(row[i].querySelectorAll("[name]")[j].getAttribute("data-value")){
				jsonValue[arr[j]] = row[i].querySelectorAll("[name]")[j].hideValue || "";
			}else{
				jsonValue[arr[j]] = row[i].querySelectorAll("[name]")[j].value || "";
			}
		};
		values.push(jsonValue);
	};
	return values;
};

//提交表格数据列表
function subData(url,dataValue,fn){
	$.ajax({
		 headers: {'Accept': 'application/json','Content-Type': 'application/json'},
		 contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		 url:url,
		 type:'POST', 
	     async:true,
	     data:dataValue,
		 success:function(data){
	     	fn&&fn();
	     },
	     error:function(){
	     	top.layer.msg("提交失败！",{time:1000});
	     }
	}); 
};

//表格选中
function selectAll(el){
	el= document.querySelector(el);
	var input = el.tBodies[0].querySelectorAll('input');
	if(el.tHead.querySelector('input').checked == true){
		for(var i=0;i<input.length;i++){
			input[i].checked = true;
		};
	}else{
		for(var i=0;i<input.length;i++){
			input[i].checked = false;
		};
	}
}

























//获取元素的纵坐标 
function getTop(e) {
	var offset = e.offsetTop;
	if (e.offsetParent != null)
		offset += getTop(e.offsetParent);
	return offset;
}
//获取元素的横坐标 
function getLeft(e) {
	var offset = e.offsetLeft;
	if (e.offsetParent != null)
		offset += getLeft(e.offsetParent);
	return offset;
}

//获取网页地址参数值
function parameter(url){
	//如果没有传链接参数，就获取当前网页参数
	var url = url || '';
	if(url == ''){
		url = location.search;
	};
	//判断是否有？后面参数
	if(url.indexOf("?")!=-1){
		//截取？后面参数
		var str=url.substr(url.indexOf("?")+1);
		//转换为数组
		var strs=str.split("&");
		//创建一个对象
		var json = {};
		for(i=0;i<strs.length;i++){
			//间值对,保存到对象,中文解码
			var value1 = decodeURI(strs[i].split("=")[0]);
			var value2 = decodeURI(strs[i].split("=")[1]);
			json[value1] = value2;
		};
		//返回对象
		return json;
	}else{
		return null;
	}
};
//获取焦点
function getTheFocus(obj,isSelected){
	if(obj.nodeName=="INPUT"){
		var vLen = obj.value.length;
        if(obj.setSelectionRange){                    //非ie
            obj.setSelectionRange(vLen,vLen);
        }else{                                         //ie
            var a = obj.createTextRange();            //ie支持creatTextRange
            a.moveStart('character',vLen);
            a.collapse(true);
            a.select();                                //选中操作
        }
        if(isSelected){
        	 obj.setSelectionRange(0, vLen);
        }
        obj.focus();
        return;
	};
	if(obj.nodeName=="SELECT"){
		obj.focus();
	}
};


//自定义复选框
(function(){
	
	document.addEventListener('click', function(e) {
		var e = e || window.Event;
		var target = e.target;
		if(target.getAttribute("data-isCheck") || target.getAttribute("data-isCheck")==""){
			if(target.getAttribute("data-isCheck")=="true"){
				target.setAttribute("data-isCheck","false");
			}else{
				var theSame = document.querySelectorAll("[data-name="+target.getAttribute("data-name")+"]");
				for(var i =0;i<theSame.length;i++){
					theSame[i].setAttribute("data-isCheck", "false");
				};
				target.setAttribute("data-isCheck","true");
			}
		};
		
	})
	
	
})();
