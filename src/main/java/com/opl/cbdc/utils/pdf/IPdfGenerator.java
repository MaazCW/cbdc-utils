package com.opl.cbdc.utils.pdf;

/**
 * @author Khyati Desai
 */
public interface IPdfGenerator {

	String DEFAULT_GENERATOR = "default-pdf-generator";

	byte[] generate(PdfRequest request) throws Exception;

	byte[] generateWord(PdfRequest request) throws Exception;
}
