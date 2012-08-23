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
import org.codehaus.groovy.grails.plugins.spring.ws.AbstractEndpointAdapter
import org.codehaus.groovy.grails.plugins.spring.ws.DefaultEndpointAdapter;
import org.codehaus.groovy.grails.plugins.spring.ws.tools.TransformerUtils
import groovy.xml.MarkupBuilder


/**
 * Default endpoint adapter that transforms the request into an 
 * {@link XmlSlurper} and the response into a {@link MarkupBuilder}
 *
 * @author Russ Miles (russ@russmiles.com)
 * @author Ivo Houbrechts (ivo@houbrechts-it.be)
 * @author Okke Tijhuis (o.tijhuis@gmail.com)
 *
 */
public class DefaultEndpointAdapter extends AbstractEndpointAdapter {
	private final String ENDPOINT_DEFAULT_METHOD = "invoke"
	private TransformerUtils transformerUtils

	DefaultEndpointAdapter(){
		transformerUtils = new TransformerUtils()
	}
	
    protected Object createRequest(Source request) throws Exception {
		transformerUtils.transformSource(request)
    }

    protected Writer createResponseWriter() {
        return new StringWriter()
    }

    protected Object createResponse(Writer writer) {
        return new MarkupBuilder(writer)
    }
	
	protected String getMethodToInvoke(Source request) {
		ENDPOINT_DEFAULT_METHOD
	}
}