package org.codehaus.groovy.grails.plugins.spring.ws.util

import org.springframework.security.web.util.matcher.RequestMatcher

/**
 *
 * User: roos
 * Date: 22.07.14
 * Time: 14:41
 *
 */
import javax.servlet.http.HttpServletRequest;

public class UrlRequestMatcher implements RequestMatcher {
	private UrlMatcher matcher;
	private Object compiledPattern;

	public UrlRequestMatcher(String pattern) {
		this.matcher = new AntUrlPathMatcher(false);
		this.compiledPattern = matcher.compile(pattern);
	}

	public boolean matches(HttpServletRequest request) {
		String path = request.getServletPath();
		return matcher.pathMatchesUrl(compiledPattern, path);
	}
}
