package com.opl.cbdc.utils.pdf;

import java.security.cert.Certificate;
import java.util.List;

/**
 * @author Khyati Desai
 */

public class SignatureDetails {

	private SignatureError    error;
	private String            sigReason;
	private List<Certificate> certificates;
	
	public SignatureDetails(SignatureError error) {
		this.error = error;
	}

	public SignatureDetails(String reason, List<Certificate> certs) {
		sigReason = reason;
		certificates = certs;
	}

	public SignatureError getError() {
		return error;
	}

	public String getSignatureReason() {
		return sigReason;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}
}
