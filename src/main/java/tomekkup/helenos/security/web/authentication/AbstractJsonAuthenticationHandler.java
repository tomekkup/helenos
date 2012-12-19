package tomekkup.helenos.security.web.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public abstract class AbstractJsonAuthenticationHandler {
    
    protected Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    protected final void writeResult(HttpServletResponse response, Authentication authentication) throws IOException {
        response.setHeader("Content-Type", "application/json");
        PrintWriter writer = response.getWriter();
        objectMapper.writeValue(writer, (authentication != null ? authentication.getAuthorities() : null));
        writer.flush();
    }

    @Required
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
