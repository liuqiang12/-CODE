define(["text!template/toolWindow.html"],function(o){var n=$(o),d=n.find("div[name=topo_confirm]"),i=n.find("div[name=topo_alert]"),t=n.find("div[name=topo_tips]").hide(),e=n.find("div[name=topo_progress]");$("body").append(n);var a=e.find(".progress-bar");e.modal({keyboard:!1,backdrop:"static",show:!0});var l=d.find(".modal-body"),r=d.find("[name=title]"),s=d.find("[name=ok]"),f=d.find("[name=cancel]"),c=d.find(".modal-content");s.on("click",function(){d.modal("hide")}),f.on("click",function(){d.modal("hide")});var h=i.find(".modal-body"),m=i.find("[name=title]"),p=i.find("[name=ok]"),w=i.find(".modal-content");p.on("click",function(){i.modal("hide")});var b=t.find(".panel-heading"),k=t.find(".panel-body"),v=t.find(".panel-footer");return{confirm:function(o){function n(o){i.handler(o.data.flag),s.off("click",n),f.off("click",n)}var i=o||{},t=i.title||"操作框",e=i.content||"",a=i.width||"500",h=(500-a)/2;r.html(t),l.html(e),d.modal({keyboard:!1,backdrop:"static",show:!0}),i.handler&&$.isFunction(i.handler)&&(s.on("click",{flag:!0},n),f.on("click",{flag:!1},n)),c.css({width:a,left:$(window).width()>500?h:0})},alert:function(o){var n=o||{},d=n.title||"提示框",t=n.content||"",e=n.width||"500",a=(500-e)/2;m.html(d),h.html(t),i.modal({show:!0}),w.css({width:e,left:$(window).width()>500?a:0})},tips:function(o){var n=o||{},d=n.title,i=n.foot,e=n.x,a=n.y,l=n.width||200,r=n.content||"";d?(b.show(),b.html(d)):b.hide(),i?(v.show(),v.html(i)):v.hide(),r?(k.show(),k.html(r),t.css({left:e,top:a,width:l}).show()):(k.hide(),t.hide())},progress:function(o){if(o){var n=o.now,d=o.text,i=o.error,t=1e3;n>=0&&n<=100?(i?(a.removeClass("progress-bar-info").addClass("progress-bar-danger"),t=1e3):a.removeClass("progress-bar-danger").addClass("progress-bar-info"),a.css({width:n+"%"}),a.html(d),e.modal({keyboard:!1,backdrop:"static",show:!0}),100==n&&setTimeout(function(){e.modal("hide")},t)):e.modal("hide")}else e.modal("hide")}}});
//# sourceMappingURL=toolWindow.js.map