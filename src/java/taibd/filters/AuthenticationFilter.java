/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import taibd.common.Constants;
import taibd.model.User;

/**
 *
 * @author HOME
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;
    private static final String LOGIN_PAGE = "pages/login.jsp";

    private static final String[] publicResourceEndpoints = new String[]{
        "/static",
        "/pages",};

    private static final String[] noneUserEndpoints = new String[]{
        "/login",
        "/home",
        "/register",
    };

    private FilterConfig filterConfig = null;

    public AuthenticationFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a filter that implements setParameter() on a request
        // wrapper could set parameters on the request before passing it on
        // to the filter chain.
        /*
	String [] valsOne = {"val1a", "val1b"};
	String [] valsTwo = {"val2a", "val2b", "val2c"};
	request.setParameter("name1", valsOne);
	request.setParameter("nameTwo", valsTwo);
         */
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<p><strong>This has been appended by an intrusive filter.</strong></p>");
	
	respOut.println("<p>Params (after the filter chain):<br>");
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
		String name = (String)en.nextElement();
		String values[] = request.getParameterValues(name);
		int n = values.length;
		StringBuffer buf = new StringBuffer();
		buf.append(name);
		buf.append("=");
		for(int i=0; i < n; i++) {
		    buf.append(values[i]);
		    if (i < n-1)
			buf.append(",");
		}
		log(buf.toString());
		respOut.println(buf.toString() + "<br>");
	}
        respOut.println("</p>");
         */
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        httpRequest = (HttpServletRequest) request;
        httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI().toString();

        HttpSession session = httpRequest.getSession(false);
        String role = getUserRole(session, httpRequest, httpResponse);

        String contextPath = httpRequest.getContextPath();
        System.out.println("url: " + url);

        if (url.equals(contextPath + "/")) {
            httpResponse.sendRedirect(contextPath + "/home");
        } else {
            if (isPublicUrl(url)) {
                chain.doFilter(request, response);
            } else {
                if (role == null) {
                    if (isNoneUserUrl(url)) {
                        chain.doFilter(request, response);
                    } else {
                        httpResponse.sendRedirect(LOGIN_PAGE);
//                    httpResponse.sendRedirect(contextPath + "/login");
                    }
                } else if (role.equals(Constants.USER_ROLE)) {
                    if (url.indexOf(contextPath + "/crawl") == 0) {
                        httpResponse.sendRedirect(contextPath + "/home");
                    } else {
                        if (isPublicUrl(url)) {
                            httpResponse.sendRedirect(contextPath + "/home");
                        } else {
                            chain.doFilter(request, response);
                        }
                    }
                } else if (role.equals(Constants.ADMIN_ROLE)){
                    chain.doFilter(request, response);
                }
            }
        }

    }

    public boolean isPublicUrl(String url) {
        for (String str : publicResourceEndpoints) {
            if (url.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isNoneUserUrl(String url) {
        for (String str : noneUserEndpoints) {
            if (url.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public String getUserRole(HttpSession session, HttpServletRequest req, HttpServletResponse res) {

        if (session == null) {
            return null;
        }

        if (session != null) {
            User user = ((User) session.getAttribute("user"));
            if (user == null) {
                return null;
            }
            return user.getRole();
        }

        return null;
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {

        }
    }

    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());

    }

}
