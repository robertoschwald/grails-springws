package org.codehaus.groovy.grails.plugins.spring.ws


import javax.xml.transform.Source
import org.codehaus.groovy.grails.plugins.spring.ws.DefaultEndpointAdapter
import java.beans.Introspector
import java.io.Writer;
import groovy.xml.MarkupBuilder
import org.codehaus.groovy.grails.plugins.spring.ws.tools.TransformerUtils

class PayloadRootEndpointAdapter extends AbstractEndpointAdapter {
	private TransformerUtils transformerUtils
	
	PayloadRootEndpointAdapter(){
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
		def rootNodeName = request.getNode().getNodeName()
		def rootNodeNameWithoutNS = rootNodeName.substring(rootNodeName.indexOf(":")+1)
		def methodName = rootNodeNameWithoutNS.substring (0, rootNodeNameWithoutNS.lastIndexOf("Request"))
		def normalizedMethodName = Introspector.decapitalize(methodName)
	}
}
