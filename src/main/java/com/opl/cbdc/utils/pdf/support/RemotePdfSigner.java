package com.opl.cbdc.utils.pdf.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import com.opl.cbdc.utils.pdf.IPdfSigner;
import com.opl.cbdc.utils.pdf.RemoteSignatureDetails;
import com.opl.cbdc.utils.pdf.SignatureDetails;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Khyati Desai
 */

public class RemotePdfSigner implements IPdfSigner {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemotePdfSigner.class);
	private static final String JCE_PROVIDER = "BC";
	private static final String CERTIFICATE_TYPE = "X.509";

//	@Value("${pdf-signer/remote/url:none}")
	private String remoteUrl;
	
//	@Value("${pdf-signer/remote/verifyurl:none}")
	private String remoteVerifyUrl;
	
	private boolean configured = false;


	////////////////////////////////////////////////////////////////////////////
	// Methods of interface IPdfGenerator

	@Override
	public byte[] sign(byte[] pdfContent, String reason) throws Exception {
		/*
		if(!configured) {
			throw new RuntimeException("Remote PDF Signer not configured.");
		}

		HttpClientBuilder hcb = HttpClients.custom();
		CloseableHttpClient hc = hcb.build();
		ByteArrayEntity entity
				= new ByteArrayEntity(pdfContent, ContentType.APPLICATION_OCTET_STREAM);
		HttpPost post = new HttpPost(remoteUrl);
		post.setEntity(entity);
		post.setHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		post.setHeader("reason",reason);
		CloseableHttpResponse response = hc.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		byte[] content = null;
		if(statusCode != 200) {
			System.err.println("Error");
			throw new Exception();
		}
		HttpEntity respEntity = response.getEntity();
		if(respEntity != null) {
			long clen = respEntity.getContentLength();
			content = new byte[(int) clen];
			DataInputStream dis = new DataInputStream(respEntity.getContent());
			dis.readFully(content);
			dis.close();
		}

		File tempFile = File.createTempFile("_gateone-", ".pdf");
		try {
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(content);
			FileInputStream fis = new FileInputStream(tempFile);
//			doc.saveIncremental(fis, fos);
			fis.close();
			fos.flush();
			fos.close();
			return FileUtils.readFileToByteArray(tempFile);
		}
		finally {
			tempFile.deleteOnExit();
			tempFile.delete();
		}
		*/
		return null;
	}

	@Override
	public SignatureDetails verify(byte[] pdfContent) throws Exception {
		/*
		if(!configured) {
			throw new RuntimeException("Remote PDF Signer not configured.");
		}

		RemoteSignatureDetails rsd = null;
		HttpClientBuilder hcb = HttpClients.custom();
		CloseableHttpClient hc = hcb.build();
		ByteArrayEntity entity
				= new ByteArrayEntity(pdfContent, ContentType.APPLICATION_OCTET_STREAM);
		HttpPost post = new HttpPost(remoteVerifyUrl);
		post.setEntity(entity);
		post.setHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.getMimeType());
		CloseableHttpResponse response = hc.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode != 200) {
			System.err.println("Error");
			throw new Exception();
		} else {
			HttpEntity respEntity = response.getEntity();
			//InputStream instream = respEntity.getContent();
			long clen = respEntity.getContentLength();
			byte []content = new byte[(int) clen];
			DataInputStream dis = new DataInputStream(respEntity.getContent());
			dis.readFully(content);
			dis.close();
			String result= new String(content);
			   if (result != null ){
			       Gson gson = new Gson();
			       rsd  = gson.fromJson(result,RemoteSignatureDetails.class);
			   }
		}
		
		return convert(rsd);
		*/
		return null;
	}
	
	
	/**
	 * <p>
	 * </p>
	 *
	 * @param rsd
	 * @return
	 * @throws NoSuchProviderException 
	 * @throws CertificateException 
	 * @throws IOException 
	 */
	private SignatureDetails convert(RemoteSignatureDetails rsd) throws CertificateException, NoSuchProviderException, IOException {
		List<Certificate> extractedCerts = new ArrayList<Certificate>();
		SignatureDetails sd = null;
		if(rsd != null) {
			List<String> certs = rsd.getCertificates();
			for (String cert : certs) {
				byte[] certBytes = Base64.decode(cert);
				InputStream input = new ByteArrayInputStream(certBytes);
				CertificateFactory certFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE, JCE_PROVIDER);
				X509Certificate x509cert = (X509Certificate) certFactory.generateCertificate(input);
				input.close();
				extractedCerts.add(x509cert);
			}
			sd = new SignatureDetails(rsd.getSignatureReason(), extractedCerts);
		}
		return sd;
	}

	public void init() throws Exception {
		if(!"none".equalsIgnoreCase(remoteUrl) && !"none".equalsIgnoreCase(remoteVerifyUrl)){
			configured = true;
		}
		
		if(!configured) {
			LOGGER.warn("############################################################");
			LOGGER.warn("Remote PDF Signer Not configured as not url property found");
			LOGGER.warn("############################################################");
			return;
		}
	}

}
