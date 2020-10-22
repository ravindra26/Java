
package com.service1.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 * @author Ravindra
 */
public class BaseController {
        
    @InitBinder
    public void registerControllerBinder(WebDataBinder binder){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,true));
        
        StringTrimmerEditor stringEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringEditor);
    }
}
