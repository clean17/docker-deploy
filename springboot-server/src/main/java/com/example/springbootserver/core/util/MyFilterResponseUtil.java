package com.example.springbootserver.core.util;


import com.example.springbootserver.core.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilterResponseUtil {
    public static void unAuthorized(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(401);
        resp.setContentType("application/json; charset=utf-8");
        ResponseDTO<?> responseDTO = new ResponseDTO<>(401, e.getMessage(), null); // 필터체인에서 걸리면 어드바이스까지 못간다.
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDTO);
        resp.getWriter().println(responseBody);
    }

    public static void forbidden(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(403);
        resp.setContentType("application/json; charset=utf-8");
        ResponseDTO<?> responseDTO = new ResponseDTO<>(403, e.getMessage(), null);
        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(responseDTO);
        resp.getWriter().println(responseBody);
    }
}
