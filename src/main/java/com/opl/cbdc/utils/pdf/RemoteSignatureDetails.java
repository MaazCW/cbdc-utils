package com.opl.cbdc.utils.pdf;

import java.util.List;

/**
 * @author Khyati Desai
 */

public class RemoteSignatureDetails {

	private SignatureError    error;
	private String            sigReason;
	private List<String> certificates;
	
	public RemoteSignatureDetails() {
	}

	public RemoteSignatureDetails(SignatureError error) {
		this.error = error;
	}

	public RemoteSignatureDetails(String reason, List<String> certs) {
		sigReason = reason;
		certificates = certs;
	}

	public SignatureError getError() {
		return error;
	}

	public String getSignatureReason() {
		return sigReason;
	}

	public List<String> getCertificates() {
		return certificates;
	}
}
