package com.key.dwsurvey.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  
public class MainsiteErrorController implements ErrorController {  
	 private static final String ERROR_PATH = "/error";  
	 
	    @RequestMapping(value=ERROR_PATH)  
	    public String handleError(){  
	        return "page/common/error";  
	    }  
	    @Override  
	    public String getErrorPath() {  
	        return ERROR_PATH;  
	    }  
} 