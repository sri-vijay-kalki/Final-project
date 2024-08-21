package com.bookexchange.project.util;

import com.bookexchange.project.exception.UnAuthorizedException;
import com.bookexchange.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CommonUtil {

    @Autowired
    UserService userService;
    public static Pair<String, String> getuserCredsFromToken(String token) throws UnAuthorizedException{
        if(token==null || !token.startsWith("Basic "))
            throw new UnAuthorizedException();
        String base64Credentials = token.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
        String[] parts = credentials.split(":", 2);
        if(parts[0].equals("") || parts[1].equals(""))
            throw new UnAuthorizedException();
        return Pair.of(parts[0], parts[1]);
    }

    public static Pair<String, String> getUser(HttpServletRequest request) throws UnAuthorizedException {
        String authHeader = (String)request.getHeader("Authorization");
        Pair<String,String> creds = getuserCredsFromToken(authHeader);
        return creds;
    }

    public static String getToken(String userName, String pass){
        String credentials = userName + ":" + pass;
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
