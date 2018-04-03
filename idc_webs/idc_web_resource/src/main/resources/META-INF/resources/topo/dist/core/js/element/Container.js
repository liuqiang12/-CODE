define(["tool/util","element/Node","element/Link","JTopo"],function(t,e,o){function i(t){s.call(this,t)}var n,s=JTopo.Container,r=function(t,e){if(e.length>0){for(var o=1e7,i=-1e7,n=1e7,s=-1e7,r=i-o,h=s-n,p=0;p<e.length;p++){var a=e[p];a.x<=o&&(o=a.x),a.x+a.width>=i&&(i=a.x+a.width),a.y<=n&&(n=a.y),a.y+a.height>=s&&(s=a.y+a.height),r=i-o,h=s-n}t.x=o,t.y=n,t.width=r,t.height=h}};return t.inherits(i,s),i.prototype.addNode=function(t){"container"==t.prop.nodeType||t.prop.container||(t.prop.container=this,this.setMember(t),this.add(t))},i.prototype.removeNode=function(e){e.prop.container=null,e.setTextPosition(e.textPosition),t.toggleElemnts(e.inLinks,!0),t.toggleElemnts(e.outLinks,!0),this.remove(e)},i.prototype.isInside=function(t){var e=t.x+t.width/2,o=t.y+t.height/2;return!this.isChild(t)&&e>this.x&&e<this.x+this.width&&o>this.y&&o<this.y+this.height},i.prototype.isChild=function(t){return t&&t.prop.container&&t.prop.container==this},i.prototype.setAttr=function(e){t.setAttr(this,e),t.setAttr(this.prop.containerNode,e)},i.prototype.setName=function(t){this.text=t,this.prop.name=t,this.prop.containerNode.text=t,this.prop.containerNode.prop.name=t},i.prototype.setTextPosition=function(e){switch(this.textPosition=e,this.prop.textPosition=e,this.text=this.prop.name,e){case"Hidden":this.text="";break;case"Bottom_Center":this.textOffsetX=0,this.textOffsetY=20;break;case"Top_Center":this.textOffsetX=0,this.textOffsetY=-20;break;case"Middle_Left":this.textOffsetX=-t.getStringWidth(this.prop.name,this.font.substring(0,this.font.indexOf("px"))+"px")-20,this.textOffsetY=0;break;case"Middle_Right":this.textOffsetX=t.getStringWidth(this.prop.name,this.font.substring(0,this.font.indexOf("px"))+"px")+20,this.textOffsetY=0}this.prop.containerNode.setTextPosition(e)},i.prototype.setImage=function(t){this.prop.image=t,this.prop.containerNode.setNodeImage(t)},i.prototype.setLayout=function(t){var e;if(t){switch(t.type){case"flow":e=JTopo.layout.FlowLayout(t.row,t.column);break;case"grid":e=JTopo.layout.GridLayout(t.row,t.column)}this.prop.layoutType=t.type,this.prop.layoutRow=t.row,this.prop.layoutColumn=t.column}else e=r,this.prop.layoutType="set";this.layout=e},i.prototype.hide=function(){$.each(this.childs,function(e,o){t.toggleElemnts(o.inLinks,!1),t.toggleElemnts(o.outLinks,!1),o.hide()}),t.toggleElemnts(this.inLinks,!1),t.toggleElemnts(this.outLinks,!1),this.visible=!1},i.prototype.show=function(){var e=this;$.each(this.childs,function(t,o){e.setMember(o)}),t.toggleElemnts(this.inLinks,!0),t.toggleElemnts(this.outLinks,!0),this.visible=!0},i.prototype.getChildren=function(t){var e=[];return t&&$.isFunction(t)?$.each(this.childs,function(o,i){t(i)&&e.push(i)}):e=this.childs,e},i.prototype.setMember=function(e){var o=this;this.prop.setMember.showName?e.setTextPosition("Bottom_Center"):e.setTextPosition("Hidden"),this.prop.setMember.showLink?(t.toggleElemnts(e.inLinks,!0),t.toggleElemnts(e.outLinks,!0)):(e.inLinks&&$.each(e.inLinks,function(t,e){o.isChild(e.nodeA)?e.hide():e.show()}),e.outLinks&&$.each(e.outLinks,function(t,e){o.isChild(e.nodeZ)?e.hide():e.show()})),e.show()},i.prototype.toggleExpand=function(){var t=this;t.prop.isExpand?(t.hide(),t.prop.containerNode.show(),t.prop.containerNode.setLocation(t.x+t.width/2,t.y+t.height/2),t.prop.isExpand=!1):(t.show(),t.prop.containerNode.hide(),t.setLocation(t.prop.containerNode.x-t.width/2,t.prop.containerNode.y-t.height/2),t.prop.isExpand=!0)},{create:function(o,s){var r=o||{},h=r.name||"",p=r.id||"",a=r.pid||"",d=(r.fontSize||"30px")+" "+(r.font||"微软雅黑"),l=r.fontColor?t.transHex(r.fontColor):"255,255,255",c=r.fillColor?t.transHex(r.fillColor):"10,10,100",f=r.alpha||.5,u="boolean"!=typeof r.childDragble||r.dragable,g="boolean"!=typeof r.dragable||r.dragable,x=r.zIndex||10,m=r.borderWidth||0,y=r.borderRadius||30,w=r.borderColor||"255,0,0",b=r.image||"img/mo/wlan_4.png",L=r.textPosition||"Bottom_Center",k=r.eweight||1e4,N=new i(h),C=r.layout,T="boolean"==typeof r.showLink&&r.showLink,v="boolean"!=typeof r.showName||r.showName;n=s,N.fillColor=c,N.fontColor=l,N.font=d,N.borderColor=w,N.borderWidth=m,N.borderRadius=y,N.dragable=g,N.childDragble=u,N.alpha=f,N.zIndex=x;var E=e.createNode({name:h,text:h,id:p,pid:a,image:b,nodeType:"container",eweight:k});return E.prop.expandTo=N,E.hide(),N.prop={id:p,pid:a,name:h,eweight:k,isExpand:!0,image:b,containerNode:E,setMember:{showLink:T,showName:v}},N.setLayout(C),N.setTextPosition(L),N.exprop={},N}}});
//# sourceMappingURL=Container.js.map