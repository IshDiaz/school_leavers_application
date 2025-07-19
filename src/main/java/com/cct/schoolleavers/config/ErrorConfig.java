package com.cct.schoolleavers.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Error Configuration class
 * 
 * This class configures custom error pages and error handling
 * for the application.
 * 
 * @author CCT Student
 * @version 1.0
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {
    
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        // Register custom error pages
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
        ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403");
        
        registry.addErrorPages(error404Page, error500Page, error403Page);
    }
} 