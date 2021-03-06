/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.groovy.grails.plugins.spring.ws.security

import org.codehaus.groovy.grails.plugins.spring.ws.util.AntUrlPathMatcher
import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityMetadataSource
import org.apache.commons.logging.*
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.security.web.util.matcher.RegexRequestMatcher


/**
 * A Spring Security ObjectDefinitionSource suitable for web services
 *
 * @author Tareq Abedrabbo (tareq.abedrabbo@gmail.com)
 */
class WebServiceInvocationDefinitionSource implements SecurityMetadataSource {

    def log = LogFactory.getLog(WebServiceInvocationDefinitionSource)

    private Map requestMap = new LinkedHashMap()

		AntUrlPathMatcher requestMatcher

    public WebServiceInvocationDefinitionSource() {}

    public WebServiceInvocationDefinitionSource(RegexRequestMatcher requestMatcher, LinkedHashMap requestMap) {
        this.requestMatcher = requestMatcher
        requestMap.each{
            addSecureUrl(it.key, it.value)
        }
    }

    public void addSecureUrl(String pattern, List<ConfigAttribute> attr) {
        requestMap[requestMatcher.compile(pattern)] =  attr
    }

    public Collection getConfigAttributeDefinitions() {
        return Collections.unmodifiableCollection(requestMap.values())
    }

    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (!object || !supports(object.class)) {
            throw new IllegalArgumentException('Object must be a GrailsWebRequest');
        }

        def request = object.currentRequest
        def url = "${request.servletPath}${request.pathInfo?:''}"

        return lookupAttributes(url)
    }

	@Override
	Collection<ConfigAttribute> getAllConfigAttributes() {
		return null
	}

	private Collection<ConfigAttribute> lookupAttributes(String url) {

        if (requestMatcher.requiresLowerCaseUrl()) {
            url = url.toLowerCase()
        }

        for(entry in requestMap.entrySet()){
            def pattern = entry.key
            def matched = requestMatcher.pathMatchesUrl(pattern, url)
            log.debug "Candidate is: '$url'; pattern is $pattern; matched= $matched; value= ${entry.value}"
            if(matched){
                return entry.value
            }
        }
        return null
    }

    public boolean supports(Class clazz) {
        return GrailsWebRequest.isAssignableFrom(clazz)
    }

    public boolean isConvertUrlToLowercaseBeforeComparison() {
        return requestMatcher.requiresLowerCaseUrl()
    }
}

