package com.opl.cbdc.utils.pdf.support;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.opl.cbdc.utils.pdf.IPdfSigner;
import com.opl.cbdc.utils.pdf.SignatureDetails;
import com.opl.cbdc.utils.pdf.SignatureError;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDPropBuild;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDPropBuildDataDict;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Khyati Desai
 */

public class DefaultPdfSigner implements IPdfSigner {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPdfSigner.class);

	private KeyStore keyStore;
	private String keyStoreLoc;
	private String keyStorePin;
	private String keyAlias;
	private String signatureName;
	private String signatureLoc;
	private String signatureContact;
	private boolean configured = false;
	////////////////////////////////////////////////////////////////////////////
	// Methods of interface InitializingBean

	public DefaultPdfSigner(String keyStoreLoc, String keyStorePin, String keyAlias, String signatureName,
			String signatureLoc, String signatureContact) throws Exception {

		this.keyStoreLoc = keyStoreLoc;
		this.keyStorePin = keyStorePin;
		this.keyAlias = keyAlias;
		this.signatureName = signatureName;
		this.signatureLoc = signatureLoc;
		this.signatureContact = signatureContact;

		if (!"none".equalsIgnoreCase(this.keyStoreLoc) && this.keyStoreLoc != null) {
			configured = true;
		}

		if (!configured) {
			LOGGER.warn("############################################################");
			LOGGER.warn("Default PDF Signer Not configured as not keystore location property found");
			LOGGER.warn("############################################################");
			return;
		}

		Security.insertProviderAt(new BouncyCastleProvider(), 1);
		keyStore = KeyStore.getInstance("JKS");
		char[] pin = this.keyStorePin.toCharArray();

		FileInputStream fis = new FileInputStream(this.keyStoreLoc);
		keyStore.load(fis, pin);
		fis.close();
	}

	////////////////////////////////////////////////////////////////////////////
	// Methods of interface IPdfGenerator

	@Override
	public byte[] sign(byte[] pdfContent, String reason) throws Exception {
		if (!configured) {
			throw new RuntimeException("PDF Signer not configured.");
		}
		PDDocument doc = PDDocument.load(new ByteArrayInputStream(pdfContent.clone()));

		PDSignature signature = new PDSignature();
		signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
		signature.setSubFilter(PDSignature.SUBFILTER_ETSI_CADES_DETACHED);
		signature.setName(signatureName);
		signature.setLocation(signatureLoc);
		signature.setReason(reason);

		PDPropBuild pb = new PDPropBuild();
		PDPropBuildDataDict app = new PDPropBuildDataDict();
		pb.setPDPropBuildApp(app);
		app.setName(signatureName);
		signature.setPropBuild(pb);

		signature.setSignDate(Calendar.getInstance());
		signature.setContactInfo(signatureContact);

		SignatureInterfaceImpl si = new SignatureInterfaceImpl(keyStore, keyAlias, keyStorePin);
		doc.addSignature(signature, si);

		File tempFile = File.createTempFile("_gateone-", ".pdf");
		FileOutputStream fos = new FileOutputStream(tempFile);
		try {
			fos.write(pdfContent);
			FileInputStream fis = new FileInputStream(tempFile);
			doc.saveIncremental(fis, fos);
			fis.close();
			fos.flush();
			fos.close();
			return FileUtils.readFileToByteArray(tempFile);
		} finally {
			tempFile.deleteOnExit();
			if(tempFile.delete()) {
				
			}
			fos.close();
		}
	}

	@Override
	public SignatureDetails verify(byte[] pdfContent) throws Exception {
		PDDocument doc = null;
		try {
			doc = PDDocument.load(new ByteArrayInputStream(pdfContent));
		} catch (Exception exep) {
			LOGGER.warn("invalid pdf document detected {}", exep.toString());
			return new SignatureDetails(SignatureError.INVALID_DOCUMENT);
		}

		List<PDSignatureField> sigFields = doc.getSignatureFields();
		if (sigFields == null || sigFields.size() < 2) {
			return new SignatureDetails(SignatureError.MISSING_CERTS);
		}

		boolean ownCertFound = false;
		String ownReason = null;
		List<Certificate> certList = null;

		for (PDSignatureField sf : sigFields) {
			if (isOwnCertificate(sf)) {
				if (ownCertFound) {
					return new SignatureDetails(SignatureError.INVALID_SOURCE_CERT);
				}
				ownCertFound = true;
				ownReason = sf.getSignature().getReason();
			} else {
				certList = extractCerts(sf);
			}
		}

		if (!ownCertFound) {
			return new SignatureDetails(SignatureError.INVALID_SOURCE_CERT);
		}
		if (certList == null || certList.isEmpty()) {
			return new SignatureDetails(SignatureError.INVALID_USER_CERT);
		}

		return new SignatureDetails(ownReason, certList);
	}

	////////////////////////////////////////////////////////////////////////////
	// Helper methods

	private boolean isOwnCertificate(PDSignatureField sf) throws Exception {
		PDSignature sig = sf.getSignature();
		if (!StringUtils.equals(sig.getName(), signatureName)) {
			return false;
		}
		if (!StringUtils.equals(sig.getLocation(), signatureLoc)) {
			return false;
		}
		if (!StringUtils.equals(sig.getContactInfo(), signatureContact)) {
			return false;
		}

		List<Certificate> certList = extractCerts(sf);
		if (certList == null) {
			return false;
		}
		PublicKey pubKey = keyStore.getCertificate(keyAlias).getPublicKey();
		for (Certificate cert : certList) {
			try {
				cert.verify(pubKey);
				return true;
			} catch (Exception exep) {
			}
		}
		return false;
	}

	private List<Certificate> extractCerts(PDSignatureField sf) throws Exception {
		COSDictionary dict = sf.getDictionary();
		if (!StringUtils.equalsIgnoreCase(dict.getNameAsString("FT"), "Sig")) {
			return null;
		}
		COSDictionary cert = (COSDictionary) dict.getDictionaryObject(COSName.V);

		// System.out.println(cert.getDictionaryObject(COSName.NAME));
		// System.out.println(cert.getDictionaryObject(COSName.getPDFName("M")));
		// System.out.println(cert.getDictionaryObject(COSName.getPDFName("SubFilter")));

		COSString certString = (COSString) cert.getDictionaryObject(COSName.CONTENTS);
		byte[] certData = certString.getBytes();
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		ByteArrayInputStream certStream = new ByteArrayInputStream(certData);
		Collection<? extends Certificate> certs = factory.generateCertificates(certStream);
		return new ArrayList<Certificate>(certs);
	}
}
