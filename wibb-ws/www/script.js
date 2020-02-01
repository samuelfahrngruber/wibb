var beers = [];
var stores = [];
var offers = [];

function addOffer(o){
    offers.push(o);
    $("#list-offers").append('<li class="list-group-item list-group-item-action"><p class="text-center lead">' + o.store.name + " has " + o.beer.name + " for " + o.price + " until " + o.endDate + '</p></li>');
}

$(document).ready(function(){
    $.get("api/beers", function(data, status){
        data.forEach(e => {
            delete e._id; // muss nicht
            beers.push(e);
            $("#list-beers").append('<li class="list-group-item list-group-item-action horizontal-list-item-content"><img class="w-100" src="' + e.icon + '"><p class="text-center lead">' + e.name + '</p></li>');
            var modaloption = $("<option></option>");
            modaloption.val(JSON.stringify(e));
            modaloption.text(e.name);
            $("#input_beer").append(modaloption);
        });
    });
    $.get("api/stores", function(data, status){
        data.forEach(e => {
            delete e._id; // muss nicht
            stores.push(e);
            $("#list-stores").append('<li class="list-group-item list-group-item-action horizontal-list-item-content"><img class="w-100" src="' + e.icon + '"><p class="text-center lead">' + e.name + '</p></li>');
            var modaloption = $("<option></option>");
            modaloption.val(JSON.stringify(e));
            modaloption.text(e.name);
            $("#input_store").append(modaloption);
        });
    });
    $.get("api/offers", function(data, status){
        data.forEach(e => {
            addOffer(e);
        });
    });
    $("#button_addOffer").click(() => {
        var newOffer = {};

        newOffer.beer = JSON.parse($("#input_beer").val());
        newOffer.store = JSON.parse($("#input_store").val());
        newOffer.price = Number($("#input_price").val());
        newOffer.startDate = new Date($("#input_startDate").val()).toISOString();
        newOffer.endDate = new Date($("#input_endDate").val()).toISOString();

        $.ajax({
            url: "/api/offers",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(newOffer),
          
            complete: function() {
              //called when complete
            },
          
            success: function(newo) {
              //called when successful
              $('#exampleModal').modal('hide');
              addOffer(newo);
            },
          
            error: function(err) {
              //called when there is an error
              alert("error" + JSON.stringify(err));
            },
          });
    });
});