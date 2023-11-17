package com.opl.cbdc.utils.pdf.support;

import com.opl.cbdc.utils.pdf.IPdfSigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Khyati Desai
 */

public class PdfSignerFactory {
	public static final String PDF_SIGNER_FAC = "pdf-signer-fac";
	private static final Logger LOGGER = LoggerFactory.getLogger(PdfSignerFactory.class);

	private String configured;
	
	private boolean useRemote;
	
	public PdfSignerFactory(boolean useRemote){
		this.useRemote = useRemote;
	}

	public IPdfSigner getPdfSigner(){
		if(!"none".equalsIgnoreCase(configured)&&useRemote){
//			if() {
//				//return ctx.getBean(IPdfSigner.REMOTE_SIGNER, IPdfSigner.class);
//			} else {
//				//return ctx.getBean(IPdfSigner.DEFAULT_SIGNER, IPdfSigner.class);
//			}
		} else {
			LOGGER.warn("PDF Signer not configured as the configuration not found");
		}
		return null;
	}
}
