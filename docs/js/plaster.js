$( document ).ready(function() {

    var iframe = $("#documentation-iframe")[0];

    $(".doc-link-entry a").each(function(index, elem) {
        var link = "documentation/" + $(elem).data("rel") + ".html";

        $(elem).click(function (){
            $(iframe).attr('src', link);
        });
    })

});