package org.imcdermott.sample.producer;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
/**
 * This class is a pared down version of what you see here:
 * https://memorynotfound.com/spring-mvc-restful-web-service-crud-example/
 * 
 */
public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        
        res.setHeader("Access-Control-Allow-Origin", "*");
        chain.doFilter(req, res);
    }
}