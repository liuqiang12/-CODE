define(["text!template/global.html","window/base","tool/util"],function(n,e,t){var i=$(n),o=i.find("[name=topo_exprop]").hide(),a=i.find("[name=topo_auto_layout]").hide(),r=i.find("[name=alarm_config]").hide();return $("body").append(i),{initAlarmConfig:function(n){var t=r.find(".panel-heading"),i=r.find("button[name='ok']"),o=r.find("button[name='cancel']"),a=r.find(".size_group"),c=r.find("[name=size_num]"),l=n.prop.config.alarm?n.prop.config.alarm:n.prop.config.alarm={};r.find(".close").click(function(){o.click()}),e.moveable(t,r),e.resizeNum(a,{bottom:10,step:2}),r.css(e.defaultPosition),r.on("resetWindow",function(){r.css(e.defaultPosition),r.hide()});var s=[];if(l.color&&l.color instanceof Array){c.val(l.size||12);var d,p;$.each(l.color,function(n,t){d=$("<div class='form-group'></div>").append("<label class='col-sm-4 control-label'>告警等级:"+(n+1)+"</label>"),r.find(".form-horizontal").append(d),d.append("<div class=' col-sm-7'><div class='input-group'></div></div>"),p=$("<input name='selected_color' type='text' class='form-control' readonly='true'>"),p.val(t),s.push(p),d.find(".input-group").append(p).append("<span class='input-group-btn'><button class='btn btn-primary' type='button' data-toggle='dropdown'><span class='glyphicon glyphicon-search'></span></button><ul class='dropdown-menu color_selected' ><li><div name='color_palette'></div></li></ul></span>"),e.colorSelect(p,d.find("[name=color_palette]"))})}return i.click(function(){var e=[];$.each(s,function(n,t){e.push(t.val())}),l.color=e,$.each(n.findElements(function(n){return"node"==n.elementType&&n.alarm&&n.prop.alarmLevel}),function(n,e){e.drawAlarm({size:c.val()})})}),{win:r,okBtn:i,cancelBtn:o}},initExProp:function(n){function t(){var n=$(f),e=n.find("input:eq(0)"),t=n.find("input:eq(1)");r.append(n),n.find("button[name='remove']").click(function(){n.remove(),o.item.splice(o.item.indexOf(n))}),o.item.push({row:n,key:e,value:t})}function i(n){o.target=n,a.find("span:first").html('[业务属性配置]   Name: <span name="prop">'+n.prop.name+'</span>  id: <span name="prop">'+n.prop.id+"</span>"),$.each(n.exprop,function(n,e){var i=o.item[o.item.length-1];i.key.val(n),i.value.val(e),t()}),o.show()}var a=o.find(".panel-heading"),r=o.find("form"),c=o.find(".form-group"),l=o.find("input:eq(0)"),s=o.find("input:eq(1)"),d=o.find("button[name='add']"),p=(o.find('div[name="group"]'),o.find("button[name='ok']")),u=o.find("button[name='cancel']");o.find(".close").click(function(){u.click()}),e.moveable(a,o),o.css(e.defaultPosition),o.item=[],o.item.push({row:c,key:l,value:s});var f=" <div class='form-group' ><div class='col-sm-5'><input type='text' class='form-control' ></div><div class='col-sm-6'><input type='text' class='form-control'></div><div class='col-sm-1'><button type='button' class='btn btn-primary'name='remove'><span class='glyphicon glyphicon-minus-sign'></span></button></div></div>";return d.click(function(){t()}),o.on("resetWindow",function(){$.each(o.item,function(n,e){n>0&&e.row.remove()}),l.val(""),s.val(""),o.item.length=1,o.css(e.defaultPosition),o.hide()}),u.click(function(){o.trigger("resetWindow")}),p.click(function(){$.each(o.item,function(n,e){e.key.val()&&(o.target.exprop[e.key.val().trim()]=e.value.val().trim())}),o.trigger("resetWindow")}),{win:o,addBtn:d,okBtn:p,cancelBtn:u,edit:i}},initAutoLayout:function(n){function i(n,t){n.percent=t,e.setPercent(n,t)}function o(n){switch(n){case"default":f.show(),g.show(),b.show(),_.hide(),M.hide(),p.val("default");break;case"star":f.hide(),g.hide(),b.hide(),_.show(),M.show(),I.click(),p.val("star");break;default:f.hide(),g.hide(),b.hide(),_.hide(),M.hide()}}function r(n){return(n.inLinks?n.inLinks.length:0)+(n.outLinks?n.outLinks.length:0)}function c(n,e,t){e=parseInt(e),t=parseInt(t);var i=parseInt(n.x),o=parseInt(n.y),a=parseInt(e-i)/10,r=parseInt(t-o)/10;n.prop._tempX=e,n.prop._tempY=t;var c=setInterval(function(){Math.abs(e-i)>1&&(i+=a),Math.abs(t-o)>1&&(o+=r),n.setLocation(parseInt(i),parseInt(o)),Math.abs(e-i)<=1&&Math.abs(t-o)<=1&&(clearInterval(c),delete n.prop._tempX,delete n.prop._tempY)},100)}function l(n,e,t,i,o){$.each(n.sort(function(n,e){return r(e)-r(n)}),function(n,a){c(a,o.x+n%e*i,o.y),(n+1)%e==0&&(o.y+=t)})}function s(e){function i(n,e){e.length>0&&(o(n,e),$.each(e,function(n,e){i(e,s(e))}))}function o(n,e){var t="asc"==M.mode,i=1;t||$.each(e,function(n,e){r(e)>i&&(i=r(e))});var o=0,c=[],l=[],s=[],d=e;do{if(c=[],l=[],$.each(d,function(n,e){t?r(e)>i?l.push(e):c.push(e):r(e)>i?c.push(e):l.push(e)}),c.length>=3)o+=p,s.length>0&&(c=c.concat(s),s=[]),a(n,o,c),o>u&&(u=o);else{if(l.length<3){o+=2*p/3,s.length>0&&(c=c.concat(s),s=[]),a(n,o||p/2,c.concat(l)),o>u&&(u=o);break}s=c.concat(s)}t?i++:i--,d=l}while(d.length>0)}function a(n,e,t,i){var o=Math.PI*Math.random();i&&(o=i);for(var a=0;a<t.length;a++){var r=parseInt((n.prop._tempX||n.x)+e*Math.cos(o+2*Math.PI*a/t.length)),l=parseInt((n.prop._tempY||n.y)+e*Math.sin(o+2*Math.PI*a/t.length));c(t[a],r,l)}}function s(n){var i=[];return n.outLinks&&$.each(n.outLinks,function(n,o){t.arrayDelete(e,o.nodeZ)&&i.push(o.nodeZ)}),n.inLinks&&$.each(n.inLinks,function(n,o){t.arrayDelete(e,o.nodeA)&&i.push(o.nodeA)}),i.sort(function(n,e){return r(e)-r(n)})}if(console.info(M.mode),0!=e.length){var d=e.slice().sort(function(n,e){return r(e)-r(n)}),p=25*(r(d[0])>2?r(d[0]):d.length)*(_.percent/100),u=0,f=t.getCenterPosition(n),h={x:f.x,y:f.y},m=0;$.each(d,function(n,o){if(r(o)<3)return!0;if(t.arrayDelete(e,o)){var a=s(o);c(o,h.x,h.y),i(o,a,p),m++,h.x+=2*u,m%2==0&&(h.y+=2*u,h.x=f.x)}}),e.length>0&&l(e,Math.ceil(Math.sqrt(e.length)),3*e[0].width/2,3*e[0].height/2,{x:f.x-u*Math.cos(Math.PI/4)-Math.ceil(Math.sqrt(e.length))*e[0].width*3/2,y:f.y-u*Math.cos(Math.PI/4)-Math.ceil(Math.sqrt(e.length))*e[0].height*3/2})}}var d=a.find(".panel-heading"),p=a.find("[name=layout]"),u=a.find("[name=each_row_group]"),f=u.find("[name=each_row_num]"),h=a.find("[name=row_space_group]"),m=h.find(".progress"),g=h.find(".progress-bar"),v=a.find("[name=column_space_group]"),y=v.find(".progress"),b=v.find(".progress-bar"),k=a.find("[name=radius_group]"),w=k.find(".progress"),_=k.find(".progress-bar"),x=a.find("[name=order_group]"),M=x.find("[name=order]"),I=x.find("[name=order][value=asc]"),P=(x.find("[name=order][value=des]"),a.find("[name=ok]")),L=a.find("[name=cancel]");return a.find(".close").click(function(){L.click()}),a.css(e.defaultPosition),e.resetHide([f,g,b,_,M],[u,h,v,k,x]),e.moveable(d,a),e.resizeNum(u,{bottom:1,step:1}),e.progressClick(m,g),e.progressClick(y,b),e.progressClick(w,_),i(g,100),i(b,100),i(_,100),M.click(function(){M.mode=$(this).val()}),e.initSelect(p,n),p.baseChose=o,o("default"),a.on("resetWindow",function(){a.css(e.defaultPosition),o("default"),f.val(5),i(g,100),i(b,100),i(_,100),a.hide()}),L.click(function(){a.triggerHandler("resetWindow")}),p.triggerBtn(P),p.addItems([{value:"default",name:"自定义",do:function(n){l(n.findElements(function(n){return"node"==n.elementType&&!n.prop.container}),f.val(),1.5*g.percent,1.5*b.percent,{x:0-n.translateX,y:0-n.translateY})}},{value:"star",name:"群星",do:function(n){s(n.findElements(function(n){return"node"==n.elementType&&!n.prop.container}))}},{value:"ring",name:"圆环",do:function(n){JTopo.layout.circleLayoutNodes(n.stage.find("node"),{animate:{time:1e3}})}}]),{win:a,layout:p,okBtn:P,cancelBtn:L}}}});
//# sourceMappingURL=global.js.map