package com.service1.interceptors;

import com.Service1.exceptions.BadAuthException;
import com.Service1.utils.UserAccessUtils;
import com.service1.dao.UserDAOImpl;
import com.service1.mongo.documents.User;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Ravindra
 * @since 2020-10-17
 */
@Component
public class UserCheckInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserAccessUtils userAccessutils;
    @Autowired
    UserDAOImpl usersDao;
    Logger logger = Logger.getLogger("myLogger");

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        try {
            String[] parts = userAccessutils.parseAuthHeader(request);
            User user = usersDao.getUserByUsername(parts[0]);
            if (user != null && user.getPassword().equals(parts[1])) {
                return true;
            } else {
                throw new BadAuthException("Not Authorized");
            }
        } catch (Exception ex) {
            sendUnauthorizedResponse(response);
            ex.printStackTrace();
            return false;
        }
    }
    
    void sendUnauthorizedResponse(HttpServletResponse response) {
        response.setStatus(401);
        response.setHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
    }
}
