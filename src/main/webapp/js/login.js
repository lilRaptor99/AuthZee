// Login
$("#btnLogin").on("click", () => {
  let rememberMe = "null";
  if ($("#remember").prop("checked") === true) {
    rememberMe = "on";
  }

  $.ajax({
    type: "POST",
    url: "api/login",

    data: `username=${$("#username").val()}&password=${$(
      "#password"
    ).val()}&remember=${rememberMe}`,

    success: (msg) => {
      console.log("Logged in. Message: ", msg);
      window.location.replace("index.html");
    },

    error: () => {
      alert("Login error! \nInvalid credentials");
    },
  });
});
