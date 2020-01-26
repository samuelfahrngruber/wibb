$(document).ready(function(){
    $.get("http://localhost:8080/api/beers", function(data, status){
        data.forEach(e => {
            $("#list-beers").append('<li class="list-group-item list-group-item-action horizontal-list-item-content"><img class="w-100" src="' + e.icon + '"><p class="text-center lead">' + e.name + '</p></li>');
        });
    });
    $.get("http://localhost:8080/api/stores", function(data, status){
        data.forEach(e => {
            $("#list-stores").append('<li class="list-group-item list-group-item-action horizontal-list-item-content"><img class="w-100" src="' + e.icon + '"><p class="text-center lead">' + e.name + '</p></li>');
        });
    });
});