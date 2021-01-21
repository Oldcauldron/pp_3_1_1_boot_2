package jm.homework.pp_3_1_1_boot_2.config.securityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
//    private ObjectMapper objectMapper = new ObjectMapper();
//
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        Map<String, Object> data = new HashMap<>();
//        data.put(
//                "timestamp",
//                Calendar.getInstance().getTime());
//        data.put(
//                "exception",
//                exception.getMessage());
//
//        response.getOutputStream()
//                .println(objectMapper.writeValueAsString(data));
//        setDefaultFailureUrl("/login.html?error=fail");
        redirectStrategy.sendRedirect(request, response, "/logincustom?error");
    }
}
