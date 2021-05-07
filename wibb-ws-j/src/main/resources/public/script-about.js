var beers = [];
var stores = [];

$(document).ready(function(){
    $.get("api/beers", function(data, status){
        data.forEach(e => {
            delete e._id; // muss nicht
            beers.push(e);
            $("#list-beers").append('<li class="list-group-item list-group-item-action horizontal-list-item-content"><img class="w-100" src="' + e.icon.imgUrl + '"><p class="text-center lead">' + e.name + '</p></li>');
        });
    });
    $.get("api/stores", function(data, status){
        data.forEach(e => {
            delete e._id; // muss nicht
            stores.push(e);
            $("#list-stores").append('<li class="list-group-item list-group-item-action horizontal-list-item-content"><img class="w-100" src="' + e.icon.imgUrl + '"><p class="text-center lead">' + e.name + '</p></li>');
        });
    });
});