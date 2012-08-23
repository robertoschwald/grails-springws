package org.codehaus.groovy.grails.plugins.spring.ws.tools

import javax.xml.transform.TransformerFactory
import org.springframework.xml.transform.StringResult
import javax.xml.transform.Source
class TransformerUtils {
	private final TransformerFactory transformerFactory;
	
	private TransformerUtils(){
		this.transformerFactory = TransformerFactory.newInstance();
	}
	
	def transformSource(Source request){
		def transformer = transformerFactory.newTransformer()
		StringResult result = new StringResult()
		transformer.transform(request, result)
		return new XmlSlurper().parseText(result.toString())
	}
	
}
