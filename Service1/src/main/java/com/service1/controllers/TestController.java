
package com.service1.controllers;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ravindra
 * @since 2020-10-17
 */
@CrossOrigin
@RestController
public class TestController  extends BaseController{
    
    Logger logger = Logger.getLogger("myLogger");
    
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String testController(HttpServletRequest request){
        logger.info(request.getHeader("Authorization"));
        return "Congrats Test Successfull!";
    }
}
