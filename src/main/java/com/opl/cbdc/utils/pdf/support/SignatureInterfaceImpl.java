package com.opl.cbdc.utils.pdf.support;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.exceptions.SignatureException;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

/**
 * @author Khyati Desai
 */

public class SignatureInterfaceImpl implements SignatureInterface {

	private PrivateKey    pvtKey;
	private Certificate[] certs;

	public SignatureInterfaceImpl(KeyStore ks, String alias, String pin) throws Exception {

		pvtKey = (PrivateKey) ks.getKey(alias, pin.toCharArray());
		certs = ks.getCertificateChain(alias);
	}

	////////////////////////////////////////////////////////////////////////////
	// Methods of interface SignatureInterface

	@Override
	public byte[] sign(InputStream content) throws SignatureException, IOException {
		try {
			List<Certificate> certList = Arrays.asList(certs);
			Store certStore = new JcaCertStore(certList);

			CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
			gen.addCertificates(certStore);

			ContentSigner cs = new JcaContentSignerBuilder("SHA1withRSA") //MD5withRSA or SHA1withDSA
				.setProvider(Security.getProviders()[0])
				.build(pvtKey);

			DigestCalculatorProvider dcp = new JcaDigestCalculatorProviderBuilder()
				.setProvider(Security.getProviders()[0]).
				build();

			SignerInfoGenerator sig = new JcaSignerInfoGeneratorBuilder(dcp)
				.build(cs, (X509Certificate) certs[0]);

			gen.addSignerInfoGenerator(sig);

			CMSProcessableByteArray cpba = new CMSProcessableByteArray(
				IOUtils.toByteArray(content));

			CMSSignedData sigData = gen.generate(cpba, false);
			return sigData.getEncoded();
		}
		catch(Exception exep) {
			
			throw new SignatureException(exep);
		}
	}
}
