package com.opl.cbdc.utils.pdf.support;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import com.opl.cbdc.utils.pdf.IPdfGenerator;
import com.opl.cbdc.utils.pdf.PdfRequest;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.events.Event;
import org.apache.fop.events.EventListener;
import org.apache.fop.events.model.EventSeverity;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.xmlgraphics.image.loader.ImageManager;




/**
 * @author Khyati Desai
 */

public class DefaultPdfGenerator implements IPdfGenerator {

	private VelocityEngine vengine;
	private FopFactory     fopFactory;

	public DefaultPdfGenerator() throws Exception {
		vengine = new VelocityEngine();
		vengine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		vengine.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());
		vengine.init();

		fopFactory = FopFactory.newInstance();
		ClassLoader cl = getClass().getClassLoader();
		InputStream cfgStream = cl.getResourceAsStream("pdfgen/apache-fop-config.xml");
		DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
		Configuration cfg = cfgBuilder.build(cfgStream);
		fopFactory.setUserConfig(cfg);
		ImageManager imgMgr = fopFactory.getImageManager();
	}

	////////////////////////////////////////////////////////////////////////////
	// Methods of interface IPdfGenerator

	@Override
	public byte[] generate(PdfRequest request) throws Exception {
		String foContent = generateFO(request.getTemplate(), request.parameters());

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
				createFOUserAgent(request), buffer);

		//Setup JAXP using identity transformer
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(); // identity transformer
		//Setup input and output for XSLT transformation
		//Setup input stream
		Source src = new StreamSource(new StringReader(foContent));
		//Resulting SAX events (the generated FO) must be piped through to FOP
		Result res = new SAXResult(fop.getDefaultHandler());
		//Start XSLT transformation and FOP processing
		transformer.transform(src, res);

		buffer.flush();
		buffer.close();
		return buffer.toByteArray();
	}

	@Override
	public byte[] generateWord(PdfRequest request) throws Exception {
		String foContent = generateFO(request.getTemplate(), request.parameters());

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		Fop fop = fopFactory.newFop(MimeConstants.MIME_RTF,
				createFOUserAgent(request), buffer);

		//Setup JAXP using identity transformer
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(); // identity transformer
		//Setup input and output for XSLT transformation
		//Setup input stream
		Source src = new StreamSource(new StringReader(foContent));
		//Resulting SAX events (the generated FO) must be piped through to FOP
		Result res = new SAXResult(fop.getDefaultHandler());
		//Start XSLT transformation and FOP processing
		transformer.transform(src, res);

		buffer.flush();
		buffer.close();
		return buffer.toByteArray();
	}
	////////////////////////////////////////////////////////////////////////////
	// Helper methods

	private static class InvalidPropertyEventListener implements EventListener {

		/**
		 * Continues processing even if an <code>invalidProperty</code> runtimeException was thrown
		 * during FO to PDF transformation.
		 * <p>
		 * Descends the event's severity level to WARN to avoid the exception throwing.
		 *
		 * @param event The event to be processed.
		 */
		public void processEvent(Event event) {
			if ("org.apache.fop.fo.FOValidationEventProducer.invalidProperty".equals(event.getEventID())) {
				event.setSeverity(EventSeverity.WARN);
			}
		}
	}

	private String generateFO(String tplPath, Map<String, Object> parameters) throws Exception {
		VelocityContext vc = new VelocityContext(parameters);
		
		Template tpl = vengine.getTemplate(tplPath);
		StringWriter writer = new StringWriter();
		tpl.merge(vc, writer);
		return writer.toString();
	}

	private FOUserAgent createFOUserAgent(PdfRequest request) {
		FOUserAgent fua = fopFactory.newFOUserAgent();
		 
		if(StringUtils.isNotBlank(request.getAuthor())) {
			fua.setAuthor(request.getAuthor());
		}
		if(StringUtils.isNotBlank(request.getCreator())) {
			fua.setCreator(request.getCreator());
		}
		if(StringUtils.isNotBlank(request.getProducer())) {
			fua.setProducer(request.getProducer());
		}
		if(StringUtils.isNotBlank(request.getSubject())) {
			fua.setSubject(request.getSubject());
		}
		if(StringUtils.isNotBlank(request.getTitle())) {
			fua.setTitle(request.getTitle());
		}
		if(StringUtils.isNotBlank(request.getBaseUrl())) {
			fua.setBaseURL(request.getBaseUrl());
		}
		fua.getEventBroadcaster().addEventListener(new InvalidPropertyEventListener());
		return fua;
	}
}
