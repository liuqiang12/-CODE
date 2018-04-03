$(function(){
    $(".checkbackgrd").bind("click",function(){
        if(!$(this).hasClass("disabled_check")){
            $(this).hasClass("on_check")?$(this).removeClass("on_check"):$(this).addClass("on_check");
            var catalog = $(this).find("input:checkbox").val();

            if($(this).hasClass("on_check")){
                /*自动添加界面*/
                if(catalog == 100){
                    ticketCategoryRack()
                }else if(catalog == 200){
                    ticketCategoryPort();
                }else if(catalog == 300){
                    ticketCategoryIp();
                }else if(catalog == 400){
                    ticketCategoryServer();
                }else if(catalog == 500){
                    ticketCategoryAdd_();
                }
            }else{
                /*去掉情况*/
                if(catalog == 100){
                    $("#rack_fieldset_content_Id").empty();
                }else if(catalog == 200){
                    $("#port_fieldset_content_Id").empty();
                }else if(catalog == 300){
                    $("#ip_fieldset_content_Id").empty();
                }else if(catalog == 400){
                    $("#server_fieldset_content_Id").empty();
                }else if(catalog == 500){
                    $("#add_fieldset_content_Id").empty();
                }
            }
        }
    });
});