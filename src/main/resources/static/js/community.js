function post() {
    var question_id = $("#question_id").val();
    var question_content = $("#question_content").val();
    console.log(question_id)
    console.log(question_content)
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId":question_id,
            "content":question_content,
            "type":1
        }),
        success: function (response) {
            if (response.code == 200){
                $("#comment_section").hide();
            }else {
                alert(response.message())
            }
            console.log(response);
            
        },
        dataType: "json"
    });



}