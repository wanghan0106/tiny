package com.roy.tiny.base.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.roy.tiny.base.web.annotation.Auth;

/**
 * Prevent anonymous user visiting some pages which should be visited by signing in.
 * @author Roy Wang
 *
 */
public class AuthInterceptor implements HandlerInterceptor  {

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView view) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("user")==null && handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			if(method.isAnnotationPresent(Auth.class)) {
				/* It cann't be visited by an anonymous user if this method is marked by @Auth.
				 * This request will be redirected to login page.
				 */
				response.sendRedirect(request.getContextPath()+"/login");
			}
		}
		return true;
	}
	
}
