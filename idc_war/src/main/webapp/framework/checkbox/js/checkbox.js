$(function(){

    $(".checkbackgrd").bind("click",function(){
        if(!$(this).hasClass("disabled_check")){
            //获取name属性
            var nameAttr = $(this).find("input:checkbox").attr("name");
            console.log((nameAttr));
            console.log((this.id));
            if(nameAttr == 'domainStatus'){
                $("input[name='"+nameAttr+"']").parents(".checkbackgrd").removeClass("on_check");

                $(this).hasClass("on_check")?$(this).removeClass("on_check"):$(this).addClass("on_check");

                var status = $(".checkbackgrd.on_check input[name='domainStatus']:checkbox").val();

                try {
                    $("#idcNetServiceinfo_domainStatus").val(status);
                    //$("input#idcRunProcCment_status_stand").val(status);
                }catch (e){}
            }
            if(this.id == 'dns_check'){
                console.log(this.id);
                dnsAdd()
            }else if(this.id == 'other_check'){
                otherAdd();
            }
        }
    });
});