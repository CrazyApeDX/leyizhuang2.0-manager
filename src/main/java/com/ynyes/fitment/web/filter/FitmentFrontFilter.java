package com.ynyes.fitment.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ynyes.fitment.core.constant.LoginSign;
import com.ynyes.fitment.foundation.entity.FitEmployee;

public class FitmentFrontFilter implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		FitEmployee employee = (FitEmployee) request.getSession().getAttribute(LoginSign.EMPLOYEE_SIGN.toString());
		if (null == employee) {
			response.sendRedirect("/fit/login");
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}
}
