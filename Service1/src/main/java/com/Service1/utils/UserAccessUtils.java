package com.Service1.utils;

import com.Service1.exceptions.BadAuthException;
import java.util.Base64;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ravindra
 */
@Component
public class UserAccessUtils {

    Logger logger = Logger.getLogger("myLogger");

    /**
     * Parses Basic Authorization Header and returns credentials
     *
     * @param request
     * @return
     * @throws BadAuthException
     */
    public String[] parseAuthHeader(HttpServletRequest request) throws BadAuthException {
        logger.info(Integer.toString(request.getContentLength()));
        String baseAuth = request.getHeader("Authorization");
        logger.info(baseAuth);
        if (baseAuth != null && !baseAuth.isEmpty()) {
            String authString = new String(Base64.getDecoder().decode(baseAuth.substring(6)));
            logger.info(authString);
            return authString.split(":");
        } else {
            throw new BadAuthException("No Auth Found with the Request");
        }
    }
}
