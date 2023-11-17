package com.opl.cbdc.utils.pdf;

/**
 * @author Khyati Desai
 */

public interface IPdfSigner {

	String DEFAULT_SIGNER = "default-pdf-signer";
	String REMOTE_SIGNER  = "remote-pdf-signer";

	byte[] sign(byte[] pdfContent, String reason) throws Exception;

	SignatureDetails verify(byte[] pdfContent) throws Exception;
}
