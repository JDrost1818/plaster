$( document ).ready(function() {

    const iframe = $("#documentation-iframe")[0];

    $(".doc-link-entry a").each(function(index, elem) {
        $(elem).click(function (){
            changeIFrame(iframe, $(elem).data("rel"));
        });
    });

    var selectedUrl = getUrlParameter("selected");
    if (selectedUrl) {
        changeIFrame(iframe, selectedUrl);
    }

    const drawerIcon = $("#drawer-icon")[0];
    $(drawerIcon).click(function (){
        if ($(this).hasClass("expanded")) {
            $(this).removeClass("expanded");
            $("#doc-links-wrapper").removeClass("visible");
            $("#doc-content").removeClass("l8 offset-l3 m7 offset-m4")
        } else {
            $(this).addClass("expanded");
            $("#doc-links-wrapper").addClass("visible");
            $("#doc-content").addClass("l8 offset-l3 m7 offset-m4")
        }
    })

});

const supportedPages = ["general", "mode", "configuration", "scope", "class-info"];

/**
 * Changes the iframe's source to the one provided as long as it is
 * a supported page.
 * @param iframe
 *          iframe for which to change source
 * @param relLink
 *          the link to which to change
 */
function changeIFrame(iframe, relLink) {
    if ($.inArray(relLink, supportedPages) === -1) {
        relLink = "general";
    }
    $(iframe).attr('src', "documentation/" + relLink + ".html");
}

/**
 * Useful utility function that allows a programmer to extract a
 * url parameter from the current url listing.
 *
 * Found here: https://stackoverflow.com/questions/19491336/get-url-parameter-jquery-or-how-to-get-query-string-values-in-js
 */
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}
