package com.gengyu.msgnotification.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author Siegfried GENG
 * @date 2019/8/20 - 10:21
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("-----------这是Filter-----------");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
