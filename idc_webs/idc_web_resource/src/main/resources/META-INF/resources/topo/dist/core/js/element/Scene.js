define(["tool/util","tool/brush","tool/winHandler","element/Link","JTopo"],function(e,t,n,o){function i(){r.call(this);var e=this;e.mode="drag",e.prop={name:"",id:"",pid:"",mode:"drag",eweight:1,path:[],mousemove:{target:"",x:0,y:0},offsetX:0,offsetY:0},e.exprop={},this.addEventListener("mousemove",function(t){e.prop.mousemove.target=t.target,e.prop.mousemove.x=t.x,e.prop.mousemove.y=t.y,e.prop.originX=-e.translateX,e.prop.originY=-e.translateY})}var r=JTopo.Scene;e.inherits(i,r),i.prototype.addElement=function(e){e.prop.scene=this,"container"==e.elementType&&this.add(e.prop.containerNode),this.add(e)},i.prototype.removeElement=function(e){var t=this;if(e){switch(e.prop&&(e.prop.scene="deleted"),e.elementType){case"container":e.show(),$.each(e.childs,function(e,t){t.prop.container=null}),this.remove(e.prop.containerNode);break;case"link":"child"!=e.prop.linkType&&e.prop.children&&$.each(e.prop.children,function(e,n){t.remove(n)})}this.remove(e)}},i.prototype.setMode=function(e){switch(e){case"select":this.mode=e,this.prop.mode=e;break;case"edit":this.mode=e,this.prop.mode=e;break;case"drag":this.mode=e,this.prop.mode=e;break;case"linkEdit":this.mode="select",this.prop.mode=e;break;default:this.mode="drag",this.prop.mode="drag"}},i.prototype.getAweight=function(){var e=0;switch(this.prop.mode){case"drag":e=0;break;case"select":e=1;break;case"edit":e=10;break;case"linkEdit":e=100}return e},i.prototype.findNode=function(e,t){var n=[];if(e)if($.isFunction(e))n=this.findElements(function(t){return"node"==t.elementType&&e(t)});else{var o=e.split("=");if(2==o.length){var i=o[0].trim(),r=o[1].trim();n=t?this.findElements(function(e){return"node"==e.elementType&&e.exprop[i]==r}):this.findElements(function(e){return"node"==e.elementType&&e.prop[i]==r})}}else n=this.findElements(function(e){return"node"==e.elementType&&"container"!=e.prop.nodeType});return n},i.prototype.findLink=function(e,t){var n=[];if(e)if($.isFunction(e))n=this.findElements(function(t){return"link"==t.elementType&&e(t)});else{var o=e.split("=");if(2==o.length){var i=o[0],r=o[1];n=t?this.findElements(function(e){return"link"==e.elementType&&e.exprop[i]==r}):this.findElements(function(e){return"link"==e.elementType&&e.prop[i]==r})}}else n=this.findElements(function(e){return"link"==e.elementType});return n},i.prototype.findContainer=function(e,t){var n=[];if(e)if($.isFunction(e))n=this.findElements(function(t){return"container"==t.elementType&&e(t)});else{var o=e.split("=");if(2==o.length){var i=o[0],r=o[1];n=t?this.findElements(function(e){return"container"==e.elementType&&e.exprop[i]==r}):this.findElements(function(e){return"container"==e.elementType&&e.prop[i]==r})}}else n=this.findElements(function(e){return"container"==e.elementType});return n},i.prototype.goBack=function(e){var o=this;if(this.prop.path.length>1){for(var i=this.prop.path[this.prop.path.length-2];i==this.prop.path[this.prop.path.length-1]&&this.prop.path.length-2>0;)o.prop.path.pop(),i=this.prop.path[this.prop.path.length-2];t[this.prop.config.brush].drawTopo({scene:this,id:i,type:"goBack",fn:function(t){o.prop.path.pop(),e&&$.isFunction(e)&&e(t)}})}else n.getTools().alert({content:"<h4>已到达最顶层</h4>"})},i.prototype.goDown=function(e,n){var o=this;t[this.prop.config.brush].drawTopo({scene:this,id:e,type:"goDown",fn:function(t){o.prop.path.push(e),n&&$.isFunction(n)&&n(t)}})},i.prototype.refresh=function(){t[this.prop.config.brush].drawTopo({scene:this,id:this.prop.pid,type:"refresh"})},i.prototype.saveTopo=function(){var e=this;t[e.prop.config.brush].saveTopo(e,function(e){1==e?n.getTools().alert({content:"<h4>保存成功！</h4>"}):n.getTools().alert({content:"<h4>保存失败!请登录后尝试！</h4>"})})};var p=!1;return i.prototype.initLinkEditMode=function(e){if(!p){p=!0;var t=this,n=null,i=new JTopo.Node;i.setSize(1,1);var r=new JTopo.Node;r.setSize(1,1);var s=o.directLink({start:i,end:r});t.mouseup(function(p){if("linkEdit"!=t.prop.mode||!e||!e._isClick||2==p.button)return void t.removeElement(s);if(null!=p.target&&(p.target instanceof JTopo.Node||p.target instanceof JTopo.Container))if(null==n)n=p.target,t.add(s),i.setLocation(p.x,p.y),r.setLocation(p.x,p.y);else if(n!==p.target){var a=p.target;if("node"==n.elementType&&"container"==n.prop.nodeType||"node"==a.elementType&&"container"==a.prop.nodeType);else if("node"==n.elementType&&n.prop.container&&"node"==a.elementType&&a.prop.container);else if("container"==n.elementType||"container"==a.elementType);else{var l=o.directLink({start:n,end:a});t.add(l),n=null,t.remove(s)}}else n=null;else n=null,t.remove(s)}),t.mousedown(function(o){(null==o.target||o.target===n||o.target===s)&&"linkEdit"==t.prop.mode&&e&&e._isClick&&t.remove(s)}),t.mousemove(function(n){"linkEdit"==t.prop.mode&&e&&e._isClick&&r.setLocation(n.x,n.y)})}},i});
//# sourceMappingURL=Scene.js.map