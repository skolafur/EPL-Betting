function sendBet() {
    var betData = {
        'betAmount': $("#bet").val(),
        'number': $("#number").val()
    };

    $.ajax({
        type: "POST",
        url: "/bet", // Replace this with your server endpoint URL ("/bet" based on your server-side @RequestMapping)
        contentType: "application/json",
        data: JSON.stringify(betData),
        success: function(result) {
            showResults(result);
        },
        error: function(error) {
            console.error("Error:", error);
            // Handle errors here if needed
        }
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
        sendBet();
    });
});


function showResults(result) {
    $("#results").append("<tr><td>" + "Win = " + result.win + ", Payout: " + result.winAmount + ", Generated number: "
        + result.generatedNumber + "</td></tr>");
}
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#send").click(function () {
        sendBet();
    });
});
