function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(";");
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == " ") {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function setCookie(cname, cvalue, exdays) {
  const d = new Date();
  d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
  let expires = "expires=" + d.toUTCString();
  document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function deleteCookie(cname) {
  document.cookie = `${cname}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/`;
}

// // Temporary cookie for front end development
// setCookie(
//   "user",
//   "%7B%22name%22%3A%22user2%22%2C%22username%22%3A%22Name+user2%22%7D",
//   1
// );

if (getCookie("user")) {
  const user = JSON.parse(getCookie("user"));
  $("#login").hide();
  $("#user").text("Logged in as: " + user.name);
} else {
  $("#user").text("");
  $("#user").hide();
  $("#logout").hide();
}

/** Logout */
$("#logout").on("click", () => {
  deleteCookie("user");
  deleteCookie("userToken");
  window.location.replace("index.html");
});

/* View users */
function updateUsers() {
  $.ajax({
    type: "GET",
    url: "api/users",

    success: (res) => {
      const userList = res.data;
      let userListHtml = "";
      userList.forEach((element) => {
        userListHtml += `<li> ${element} </li>`;
      });
      $("#userList").html(userListHtml);
    },

    error: (err) => {
      if (err.status === 401) {
        $("#userList").html("User Unauthorized to get user data");
      } else {
        $("#userList").html("Error getting user data>");
      }
    },
  });
}

updateUsers();

/* Add user */

$("#btnAddUser").on("click", () => {
  $.ajax({
    type: "POST",
    url: "api/users",

    data: `name=${$("#name").val()}&level=${$("#level").val()}`,

    success: (res) => {
      updateUsers();
    },

    error: (err) => {
      if (err.status === 500) {
        alert("Invalid data inserted");
      } else if (err.status === 401) {
        alert("Unauthorized to add user");
      } else {
        alert("Error adding user");
      }
    },
  });
});
