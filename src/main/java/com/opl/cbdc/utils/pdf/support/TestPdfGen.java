package com.opl.cbdc.utils.pdf.support;

import com.opl.cbdc.utils.pdf.PdfRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Khyati Desai
 */

public class TestPdfGen {

	public static void main(String[] args) throws Exception {
		generateImpl();
		/*
		DefaultPdfSigner dps = new DefaultPdfSigner();
		dps.setKeyStoreLocation("test");
		dps.setPin("test123");
		dps.setAlias("test");
		dps.afterPropertiesSet();

		File inFile = new File("/Users/ineeld/Downloads/ubqui_logo.pdf");
		//File inFile = new File("tada.pdf");
		byte[] original = FileUtils.readFileToByteArray(inFile);
		byte[] signedData = dps.sign(original);

		FileOutputStream fos = new FileOutputStream("sample.pdf");
		fos.write(signedData);
		fos.flush();
		fos.close();
		*/
		//generateImpl();

		//DefaultPdfSigner dps = new DefaultPdfSigner();
		//File inFile = new File("/Users/ineeld/Downloads/terms-and-conditions.1.pdf");
		//dps.verify(FileUtils.readFileToByteArray(inFile));
	}

	private static void generateImpl() throws Exception {
		DefaultPdfGenerator dpg = new DefaultPdfGenerator();
		//dpg.afterPropertiesSet();
		PdfRequest request = new PdfRequest();
		request.setTemplate("invoice.tax.fo.xml");

		Date agmtDate = new Date();
		SimpleDateFormat dayFmt = new SimpleDateFormat("dd");
		SimpleDateFormat monthFmt = new SimpleDateFormat("MMMM");
		SimpleDateFormat yearFmt = new SimpleDateFormat("yyyy");
		request.parameters().put("agmtDay", dayFmt.format(agmtDate));
		request.parameters().put("agmtMonth", monthFmt.format(agmtDate));
		request.parameters().put("agmtYear", yearFmt.format(agmtDate));

		request.parameters().put("auaCode", "Khyati");
		request.parameters().put("auaName", "Temp Labs Private Limited");
		request.parameters().put("auaAddress",
				"#18/2A,Ace Business Park, Second Floor, Above Spencer's Hypermarket,"
						+ "Bangalore - 560102, Karnataka");

		request.parameters().put("company", true);
		request.parameters().put("saName", "Zero Mass Foundation");
		request.parameters().put("saAddress",
				"T-951, 5th Floor, Belapur Railway Station Commercial Complex,"
				+ " Tower-4, Sector 11, CBD Belapur, Navi Mumbai - 400 614");

		byte[] data = dpg.generate(request);
		FileOutputStream fos =null;
		try {
			fos = new FileOutputStream(new File("sample.pdf"));
			fos.write(data);
			fos.flush();
			fos.close();
		}finally {
			if(fos!=null) {
				fos.close();
			}
		}
		
		
	}
}
