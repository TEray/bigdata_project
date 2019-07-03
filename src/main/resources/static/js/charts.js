function doAnaylze(method){
    $.ajax({
        url : "/spark/any/"+method,
        type : "POST",
        contentType:'application/json;charset=utf-8',
        dataType : "json",
        success : function(data) {
        }
    });
}