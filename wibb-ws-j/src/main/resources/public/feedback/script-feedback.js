$(document).ready(function(){
    $("#btn_submit").click(function(){
        let txtval = $("#txt").val();
        $.ajax("/api/requests", {
            data : JSON.stringify({
                text: txtval
            }),
            contentType : 'application/json',
            type : 'POST',
            success: function(result){
                $("#txt").val("");
                alert("Successfully submitted feedback!");
            }
        });
    });
});