(function(){
	window.top.isThisExist = false;
	window.top.loadPdf = loadPdf;
	function loadPdf(json){
		
		if(window.top.isThisExist == false){
			window.top.isThisExist = true;
		}else{
			alert("只能同时预览一个！");
			return;
		};
		//默认参数
		var init = {
			width : 820, //弹出层的宽度
			height : 600, //弹出层的高度
			pos: window.top, //加载到顶层窗口还是当前窗口
			src : null,   //图片pdf的地址
			time : 400,  //页面的过渡时间
			cancel:function(){
				//删除完成的函数
			}
		};
		
		for(var attr in json){
			init[attr] = json[attr];
		};
		
		var ifarmeW = init.width;
		var ifarmeH = init.height;;
		var time = init.time;
		var src = init.src;
		var Thiswindow = pos.document.body;  
		
		var iwidth = (window.innerWidth-ifarmeW)/2; 
		var iheight = (window.innerHeight-ifarmeH)/2;
		
		//创建遮罩层
		var oBG = document.createElement('div');
			oBG.style.cssText="z-index:1000000;position: fixed;top: 0;right: 0;bottom: 0;left: 0;background: #000; opacity:0;transition: "+time/1000+"s;";
			Thiswindow.appendChild(oBG);
			setTimeout(function(){
				oBG.style.opacity = 0.3;
			});
		//创建pdf内容
		var oIfarme = document.createElement('iframe');
			oIfarme.width = ifarmeW;
			oIfarme.height = ifarmeH;
			oIfarme.setAttribute('frameborder',0);
			oIfarme.setAttribute('scrolling','no');
			oIfarme.style.cssText="z-index:1000000;background-color:rgba(0,0,0,0.3);border:3px solid rgba(0,0,0,0.3);position: fixed;top: "+iheight+"px;left:"+iwidth+"px;opacity:0;transition:"+time/1000+"s;";
			oIfarme.src = src;
			Thiswindow.appendChild(oIfarme);
			oIfarme.onload = function(){
				var oIfarmeBody = oIfarme.contentWindow.document.body;
				var oimg = oIfarmeBody.querySelector('img');
				if(oimg){
					var imgH = oimg.offsetHeight;
						oimg.style.cssText="margin-top: "+(ifarmeH-imgH)/2+"px;"
				};
				oIfarmeBody.style.textAlign="center";
				oIfarmeBody.style.height = ifarmeH + 'px';
				oIfarme.style.opacity = 1;
			};
		//屏幕大小改动时改变位置
		function setPos(){
			iwidth = (window.innerWidth-ifarmeW)/2;
			iheight = (window.innerHeight-ifarmeH)/2;
			oIfarme.style.top = iheight+'px';
			oIfarme.style.left = iwidth+'px';
		};
		window.addEventListener('resize',setPos);
		//删除页面
		oBG.addEventListener('click',function(){
			oBG.style.opacity = 0;
			oIfarme.style.opacity = 0;
			window.removeEventListener('resize',setPos);
			setTimeout(function(){
				Thiswindow.removeChild(oBG);
				Thiswindow.removeChild(oIfarme);
				init.cancel();
			},time);
			window.top.isThisExist = false;
		});
	};
})();