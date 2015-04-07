function click_btn(adress)
{
    load_pla(adress, {"inputtext" : $("#inputtext").val()});
}

function load_pla(url, data)
{
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function(data){
//             alert("Data Loaded: " + data);
             $("#plaresult").html(data['text']);
        },
        dataType: "json"
    });
}