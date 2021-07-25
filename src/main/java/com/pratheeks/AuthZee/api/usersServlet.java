package com.pratheeks.AuthZee.api;

import com.pratheeks.AuthZee.auth.AuthService;
import com.pratheeks.AuthZee.auth.Authorization;
import com.pratheeks.AuthZee.dao.UserDao;
import com.pratheeks.AuthZee.model.User;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "usersServlet", value = "/api/users")
public class usersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject res = new JSONObject();
        Cookie requestTokenCookie = AuthService.getUserTokenCookie(request);

        // Admin Level request - return all users including admins
        if(Authorization.isAuthorized(requestTokenCookie, 100)){
            response.setStatus(200);
            res.put("status", 200);
            res.put("message", "Success");
            UserDao userDao = new UserDao();
            res.put("data", getNames(userDao.getAll()));

        }else if(Authorization.isAuthorized(requestTokenCookie, 10)){ // User level request - return all users without admins
            response.setStatus(200);
            res.put("status", 200);
            res.put("message", "Success");
            UserDao userDao = new UserDao();
            res.put("data",
                    getNames(
                            userDao.getAll()
                                .stream()
                                .filter(c-> c.getLevel() <= 20)
                                .collect(Collectors.toList())
                    )
            );

        } else{ // public unauthorized
            response.setStatus(401);
            res.put("status", 401);
            res.put("message", "Unauthorized request");
        }
        out.write(res.toString());
    }

    private static ArrayList<String> getNames(List<User> users){
        ArrayList<String> names = new ArrayList<>();
        for(User user : users){
            names.add(user.getName());
        }
        return names;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject res = new JSONObject();
        Cookie requestTokenCookie = AuthService.getUserTokenCookie(request);

        // Admin Level request - return all users including admins
        if(Authorization.isAuthorized(requestTokenCookie, 100)){
            response.setStatus(200);
            res.put("status", 200);
            res.put("message", "Success");
            User user = new User(0, Long.parseLong(request.getParameter("level")));
            user.setName(request.getParameter("name"));
            UserDao userDao = new UserDao();
            userDao.save(user);

        } else{ // All other users unauthorized
            response.setStatus(401);
            res.put("status", 401);
            res.put("message", "Unauthorized request");
        }
        out.write(res.toString());
    }
}
