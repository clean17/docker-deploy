package com.example.springbootserver.core.util;


import com.example.springbootserver.core.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilterResponseUtil {
    public static void unAuthorized(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(401);
        resp.setContentType("application/json; charset=utf-8");
        ResponseDTO<?> responseDTO = new ResponseDTO<>(401, "unAuthorized", e.getMessage());
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDTO);
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(403);
        resp.setContentType("application/json; charset=utf-8");
        ResponseDTO<?> responseDTO = new ResponseDTO<>(403, "forbidden", e.getMessage());
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDTO);
        resp.getWriter().println(responseBody);
    }
}
