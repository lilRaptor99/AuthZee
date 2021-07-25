package com.pratheeks.AuthZee.api;

import com.pratheeks.AuthZee.auth.AuthService;
import com.pratheeks.AuthZee.auth.Authentication;
import com.pratheeks.AuthZee.auth.Authorization;
import com.pratheeks.AuthZee.dao.UserDao;
import com.pratheeks.AuthZee.model.User;
import org.json.JSONObject;
import java.net.URLEncoder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import static com.pratheeks.AuthZee.auth.Hasher.base64Encode;

@WebServlet(name = "loginServlet", value = "/api/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(405); // not allowed
    }

    /**
     * Create and set the userToken cookie if the post credentials is valid.
     * @param request POST request should contain username, password, and optionally remember parameters
     * @param response 200 - user credentials are valid and userToken cookie is created
     *                 409 - user is already logged in.
     *                 401 - request credentials are invalid.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject res = new JSONObject();

        // If the login request already has a userToken cookie: validate the token
        Cookie requestTokenCookie = AuthService.getUserTokenCookie(request);
        // If the cookie is valid then user is already logged in
        if(requestTokenCookie != null && Authorization.isValidTokenCookie(requestTokenCookie)){
            response.setStatus(409); // conflict - user is already logged in
            res.put("status", 409);
            res.put("message", "User already logged in");
            out.write(res.toString());
            return;
        }



        // Validate login credentials and set the userToken cookie
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        int expireInDays = 7;
        if(remember == null || remember.equals("null")) expireInDays = 1;

        // authenticate user and get userToken
        JSONObject userToken = Authentication.getUserToken(username, password, LocalDateTime.now().plusDays(expireInDays));
        //System.out.println(userToken);

        if(userToken != null) {
            res.put("status", 200);
            res.put("userToken", userToken);
            res.put("message", "Login success");
            Cookie authTokenCookie = new Cookie("userToken", base64Encode(userToken.toString()));

           // authTokenCookie.setHttpOnly(true);
           // authTokenCookie.setSecure(true);  // disabled to unit test the api

            authTokenCookie.setMaxAge(expireInDays*24*60*60);
            authTokenCookie.setPath("/");
            response.addCookie(authTokenCookie);


            // Adding user cookie
            UserDao userDao = new UserDao();
            User user = userDao.get(username);
            JSONObject jsonUser = new JSONObject();
            jsonUser.put("name", user.getName());
            jsonUser.put("username", user.getUsername());

            Cookie userCookie = new Cookie("user", URLEncoder.encode(jsonUser.toString(), "UTF-8"));
            userCookie.setMaxAge(expireInDays*24*60*60);
            userCookie.setPath("/");
            response.addCookie(userCookie);
        } else{
            // Invalid credentials
            response.setStatus(401);
            res.put("status", 401);
            res.put("userToken", "null");
            res.put("message", "Incorrect Username or Password");
        }
        out.write(res.toString());

    }
}
