/**自己封装的js*/

//获取当前激活的iframe（只限tab页生成的iframe，弹窗除外，弹窗请使用layer.parent）
function getFrmRight(){
   return top.layer.getFrmRight();
}

//获取当前激活的iframe（只限tab页生成的iframe，弹窗除外，弹窗请使用layer.parent）
layer.getFrmRight=function(){
		var selector="[role='tabpanel'][class*='active']";//查找所有对应的iframe
		var  tabpanels= $(selector);
		var iframe=tabpanels.find("iframe")[0];
		var currWin=iframe;
		while(iframe){//往下层查找，直到找不到为止
			currWin=iframe;
			var ifrdoc=iframe.contentWindow.document;
			var tabpanelsIfr=$(selector,ifrdoc);
			iframe=tabpanelsIfr.find("iframe")[0];
		}
	return currWin;//返回最后找到的iframe，也就是当前激活的iframe
};

//获取当前弹窗（根据index获取所对应的弹窗iframe）		   
layer.innerFrame=function(index){
	if(!index)return undefined;
	return $("iframe[id=layui-layer-iframe"+index+"]")[0];				
};

//刷新
layer.refresh=function(iframe){
	iframe?iframe.src?iframe.src=iframe.src:0:0;
};

//获取父弹窗（index为当前弹窗的下标；parentIndex为要获取那一层级的父弹窗，index不写时获取top.layer.getFrmRight()，parentIndex不写时默认为1）
//也可以直接传入window获取直接父窗体
layer.parent=function(index,parentIndex){
	if($.isWindow(index)){
		index=top.layer.getFrameIndex(index.name);
	}
	if(isNaN(index))return top.layer.getFrmRight();//如果index没有或者为undefined时返回顶层激活的iframe
	if(isNaN(parentIndex)){
		parentIndex=1;
	}else{
		parentIndex=parseInt(parentIndex);
		if(parentIndex<=0)parentIndex=1;
	}
	index=parseInt(index);
	var indexs=[];var objs=[];
	var strId="layui-layer-iframe";
	var iframes=$("iframe[id^="+strId+"]");
	var currentIndex=0;
	iframes.each(function(i){
		var id=$(this).attr("id").replace(strId,"");
		indexs.push(parseInt(id));
		objs.push(this);
		if(index==parseInt(id)){
			currentIndex=i;
		}
	});
	var curparentIndex=currentIndex-parentIndex;
	
	if(curparentIndex<0){
		return top.getFrmRight();
	}
	return objs[curparentIndex];//返回父iframe
};	

//关闭当前窗体后面的窗体
layer.closeAfter=function(iframe){
	if(iframe){
		if(iframe.src){
			var indexs=[];
			var strId="layui-layer-iframe";
			var index=$(iframe).attr("id").replace(strId,"");
			if(!isNaN(index)){
				index=parseInt(index);
				var iframes=$("iframe[id^="+strId+"]");
				var currentIndex=0;
				iframes.each(function(i){
					var id=$(this).attr("id").replace(strId,"");
					indexs.push(parseInt(id));
					if(index==parseInt(id)){//得到当前iframe在数组中的下标
						currentIndex=i;
					};
				});
				$.each(indexs,function(i){
					if(i>currentIndex){//比当前iframe的id大的都关闭
						top.layer.close(indexs[i]);
					}
				});
			}//if(!isNaN(index))
		}//if(iframe.src)
	}//if(iframe)
	
};//closeAfter()

layer.oldClose=layer.close;


//关闭窗体：如果是通过iframe弹出的弹窗，在弹窗内可以通过传入window对象关闭
layer.close=function(obj){
	if($.isWindow(obj)){
	  var index = top.layer.getFrameIndex(obj.name); //获取窗口索引
	  if(!isNaN(index)){
		  layer.oldClose(index);
	  }
	}else{
		layer.oldClose(obj);
	}
}

layer.oldLayer={
	alert:layer.alert,
	confirm:layer.confirm
};

layer.changeNum=0;
layer.change=function(){//新旧窗体交替
	if(layer.changeNum==0){
		layer.alert=layer.oldLayer.alert;
		layer.confirm=layer.oldLayer.confirm;
		layer.changeNum=1;
	}else{
		layer.alert=layer.newLayer.alert;
		layer.confirm=layer.newLayer.confirm;		
		layer.changeNum=0;
	}
};


layer.alert=function(a, b, d){
	var e = "function" == typeof b;
	var options={icon:0,title:"系统提示"};
	if(!e){
		options=jQuery.extend(options,b);
	}else{
		d=b;
	}
	return layer.oldLayer.alert(a,options,d);
};


layer.confirm=function(a, b, d, g){
	var e = "function" == typeof b;
	var options={icon:3,title:"信息确认",btn: ['确定','取消']};
	if(!e){
		options=jQuery.extend(options,b);
	}else{
		g = d, d = b;
	}
	return layer.oldLayer.confirm(a,options,d,g);
};


layer.newLayer={
		alert:layer.alert,
		confirm:layer.confirm
};