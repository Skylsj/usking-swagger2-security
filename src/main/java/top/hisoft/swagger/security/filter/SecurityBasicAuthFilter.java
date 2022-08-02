package top.hisoft.swagger.security.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Basic auth implementation of the {@link Filter} interface,supporting BasicAuth of HTTP.
 *
 * @author shijin.liu
 * @see Filter
 * @since 1.0.0
 */
public class SecurityBasicAuthFilter extends AbstractSecurityFilter {

    private static final int USER_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private boolean enableBasicAuth = false;
    private String userName;
    private String password;

    /**
     * Constructor with parameters.
     *
     * @param enableBasicAuth The enabled basic auth.
     * @param userName        The user name.
     * @param password        The password.
     */
    public SecurityBasicAuthFilter(boolean enableBasicAuth, String userName, String password) {
        this.enableBasicAuth = enableBasicAuth;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        Enumeration<String> enumeration = filterConfig.getInitParameterNames();
        if (enumeration.hasMoreElements()) {
            setEnableBasicAuth(Boolean.valueOf(filterConfig.getInitParameter("enableBasicAuth")));
            setUserName(filterConfig.getInitParameter("userName"));
            setPassword(filterConfig.getInitParameter("password"));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!enableBasicAuth) {
            chain.doFilter(request, response);
        } else {
            HttpServletRequest rq = (HttpServletRequest) request;
            if (!match(rq.getRequestURI())) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse rs = (HttpServletResponse) response;
                Object swaggerSessionValue = rq.getSession().getAttribute(SWAGGER_BASIC_AUTH_SESSION);
                if (Objects.nonNull(swaggerSessionValue)) {
                    chain.doFilter(request, response);
                } else {
                    String auth = rq.getHeader("Authorization");
                    if (auth == null || "".equals(auth)) {
                        writeForbiddenCode(rs);
                        return;
                    }
                    String userAndPassword = decodeBase64(auth.substring(6));
                    String[] upArrays = userAndPassword.split(":");
                    if (upArrays.length != 2) {
                        writeForbiddenCode(rs);
                        return;
                    } else {
                        String tempUser = upArrays[USER_INDEX];
                        String tempPwd = upArrays[PASSWORD_INDEX];
                        if (tempUser.equals(userName) && tempPwd.equals(password)) {
                            rq.getSession().setAttribute(SWAGGER_BASIC_AUTH_SESSION, userName);
                            chain.doFilter(request, response);
                        } else {
                            writeForbiddenCode(rs);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Write forbidden code to response.
     *
     * @param response The {@link HttpServletResponse}.
     * @throws IOException Throw {@link IOException}.
     */
    private void writeForbiddenCode(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"input Swagger Basic userName & password \"");
        response.getWriter().write("You do not have permission to access this resource");
    }

    public void setEnableBasicAuth(boolean enableBasicAuth) {
        this.enableBasicAuth = enableBasicAuth;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Add custom filters of {@link Pattern} type.
     *
     * @param urlFilters
     */
    @Override
    void addFilters(List<Pattern> urlFilters) {
        // None
    }
}
