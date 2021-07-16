$(document).ready(function () {
  // Login
  $("#btnLogin").on("click", () => {
    console.log($("#username").val(), $("#password").val());

    let rememberMe = "null";
    if ($("#remember").prop("checked") === true) {
      rememberMe = "on";
    }

    // $.post("api/login", {
    //   username: $("#username").val(),
    //   password: $("#password").val(),
    //   remember: rememberMe,
    // })
    //   .done((data, status) => {
    //     console.log("Res data:", data, "Res status: ", status);
    //   })
    //   .fail((xhr, status, error) => {
    //     console.log("Error! ", "XHR:", xhr, "Status:", status, "Error:", error);
    //   });

    $.ajax({
      type: "POST",
      url: "api/login",

      data: `username=${$("#username").val()}&password=${$(
        "#password"
      ).val()}&remember=${rememberMe}`,

      success: (msg) => {
        console.log("Logged in. Message: ", msg);
        //window.location.replace("index.html");
      },

      error: (XMLHttpRequest, textStatus, errorThrown) => {
        console.log(
          "Login error: ",
          XMLHttpRequest,
          "\nText status: ",
          textStatus,
          "\nError thrown: ",
          errorThrown
        );
      },
    });
  });
});
