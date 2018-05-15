package com.crm.application.configuration;

import com.crm.application.utilModels.auth.TokenUser;
import com.crm.application.utilModels.auth.TokenUtil;
import com.crm.application.utilModels.response.OperationResponse;
import com.crm.application.utilModels.session.SessionItem;
import com.crm.application.utilModels.session.SessionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class GenerateTokenForUserFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(GenerateTokenForUserFilter.class);

    private TokenUtil tokenUtil;

    protected GenerateTokenForUserFilter(String urlMapping, AuthenticationManager authenticationManager, TokenUtil tokenUtil) {
        super(new AntPathRequestMatcher(urlMapping));
        setAuthenticationManager(authenticationManager);
        this.tokenUtil = tokenUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, JSONException {
        try {
            String jsonString = IOUtils.toString(request.getInputStream(), "UTF-8");
            JSONObject userJSON = new JSONObject(jsonString);
            String username = userJSON.getString("username");
            String password = userJSON.getString("password");
            String browser = request.getHeader("User-Agent") != null ? request.getHeader("User-Agent") : "";
            String ip = request.getRemoteAddr();
            log.info("\nip:{} \nbrowser:{} \n----", ip, browser);
            final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            return getAuthenticationManager().authenticate(authToken);
        } catch (JSONException | AuthenticationException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authToken) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authToken);

        TokenUser tokenUser = (TokenUser) authToken.getPrincipal();
        SessionResponse resp = new SessionResponse();
        SessionItem respItem = new SessionItem();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String tokenString = this.tokenUtil.createTokenForUser(tokenUser);

        respItem.setFirstName(tokenUser.getUser().getName());
        respItem.setLastName(tokenUser.getUser().getSurname());
        respItem.setUserId(String.valueOf(tokenUser.getUser().getId()));
        respItem.setEmail(tokenUser.getUser().getEmail());
        respItem.setToken(tokenString);
        respItem.setRole(tokenUser.getRole());

        resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        resp.setOperationMessage("200");
        resp.setItem(respItem);
        String jsonRespString = ow.writeValueAsString(resp);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(jsonRespString);
        res.getWriter().flush();
        res.getWriter().close();

    }
}
