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

package org.codehaus.groovy.grails.plugins.spring.ws

import javax.xml.transform.Source
import org.springframework.ws.server.endpoint.PayloadEndpoint
import org.springframework.xml.transform.StringSource
import org.codehaus.groovy.grails.commons.GrailsClassUtils


/**
 * Abstract Implementation of   {@link PayloadEndpoint}   that delegates to an endpoint artefact
 * This class provides the hooks for different request and response type strategies
 *
 * @author Russ Miles (russ@russmiles.com)
 * @author Ivo Houbrechts (ivo@houbrechts-it.be)
 *
 */
public abstract class AbstractEndpointAdapter implements PayloadEndpoint {
    def endpointImpl
    def name

    public Source invoke(Source request) throws Exception {
        Writer responseWriter = createResponseWriter()
		def methodName = getMethodToInvoke(request)
		endpointImpl."${methodName}"(createRequest(request), createResponse(responseWriter))
        return new StringSource(responseWriter.toString())
    }

    protected abstract Object createRequest(Source request) throws Exception

    protected abstract Writer createResponseWriter()

    protected abstract Object createResponse(Writer writer)

	protected abstract String getMethodToInvoke(Source request)
}