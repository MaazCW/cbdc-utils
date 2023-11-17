package com.opl.cbdc.utils.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * @author sandip.bhetariya
 *
 */
public class AesGcmEncryptionUtils {
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
	private static final int TAG_LENGTH_BIT = 128;
	private static final int IV_LENGTH_BYTE = 16;
	private static final int AES_KEY_BIT = 256;

//	private static final String data = "{\"data\":\"{\\\"RESULT\\\":\\\"SUCCESS\\\",\\\"MESSAGE\\\":\\\"Data retrieved successfully.No of Records=1\\\",\\\"SEARCH_RESULTS\\\":{\\\"RECORDS\\\":[{\\\"COLUMNS\\\":[{\\\"NAME\\\":\\\"Membership Type\\\",\\\"VALUE\\\":\\\"ACP\\\"},{\\\"NAME\\\":\\\"Membership Id\\\",\\\"VALUE\\\":\\\"171302\\\"},{\\\"NAME\\\":\\\"DOB (dd/MMM/yyyy)\\\",\\\"VALUE\\\":\\\"1992-05-22 00:00:00.0\\\"},{\\\"NAME\\\":\\\"Membership Status\\\",\\\"VALUE\\\":\\\"Active\\\"},{\\\"NAME\\\":\\\"Current COP Status\\\",\\\"VALUE\\\":\\\"1\\\"},{\\\"NAME\\\":\\\"COP Type\\\",\\\"VALUE\\\":\\\"Full Time\\\"},{\\\"NAME\\\":\\\"Date from COP held(dd/MMM/yyyy)\\\",\\\"VALUE\\\":\\\"2016-04-01 00:00:00.0\\\"},{\\\"NAME\\\":\\\"Applicant Full Name\\\",\\\"VALUE\\\":\\\"HATIM ASGARALI BATTIWALA\\\"},{\\\"NAME\\\":\\\"Middle Name\\\",\\\"VALUE\\\":\\\"ASGARALI\\\"},{\\\"NAME\\\":\\\"Last Name\\\",\\\"VALUE\\\":\\\"BATTIWALA\\\"},{\\\"NAME\\\":\\\"Gender\\\",\\\"VALUE\\\":\\\"MALE\\\"},{\\\"NAME\\\":\\\"Professional Address Line 1\\\",\\\"VALUE\\\":\\\"103 RADHA APARTMENTS\\\"},{\\\"NAME\\\":\\\"Professional Address Line 2\\\",\\\"VALUE\\\":\\\"NEAR RADHE MANDIR\\\"},{\\\"NAME\\\":\\\"Professional Address Line 3\\\",\\\"VALUE\\\":\\\"WAGHAWADI CIRCLE BHAV NAGAR\\\"},{\\\"NAME\\\":\\\"Professional Address Line 4\\\",\\\"VALUE\\\":\\\"\\\"},{\\\"NAME\\\":\\\"Professional City\\\",\\\"VALUE\\\":\\\"BHAVNAGAR\\\"},{\\\"NAME\\\":\\\"Professional District\\\",\\\"VALUE\\\":\\\"GJX\\\"},{\\\"NAME\\\":\\\"Professional State\\\",\\\"VALUE\\\":\\\"GUJARAT\\\"},{\\\"NAME\\\":\\\"Professional Pin Code\\\",\\\"VALUE\\\":\\\"364001\\\"},{\\\"NAME\\\":\\\"Professional Country\\\",\\\"VALUE\\\":\\\"INDIA\\\"},{\\\"NAME\\\":\\\"Pan Card Number\\\",\\\"VALUE\\\":\\\"AZJPB1058R\\\"},{\\\"NAME\\\":\\\"Professional Email ID\\\",\\\"VALUE\\\":\\\"hatimbatti@gmail.com\\\"},{\\\"NAME\\\":\\\"Professional Mobile Number\\\",\\\"VALUE\\\":\\\"9898847752\\\"}],\\\"ID\\\":\\\"0\\\"}]}}\",\"message\":\"SUCCESS\",\"serverRequestId\":\"6759e64c-1\",\"status\":200}";
	private static final String key = "Xa/Bc14y7+Y0wq2SFHAuovvsfvnW7PUkKncUstn9z7o=";
	private static final String ALGO_AES = "AES";
//    private static final String iV = "QuQm8kRPjdJKt3DrdQsDpA==";

	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	public static byte[] encrypt(byte[] pText, SecretKey secret, byte[] iv) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
		byte[] encryptedText = cipher.doFinal(pText);
		return encryptedText;
	}

	public static String encryptNew(String plainText) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String iv = UUID.randomUUID().toString().replaceAll("-", "").substring(16);
		SecretKey sKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGO_AES);

		byte[] encData = (encrypt(plainText.getBytes(StandardCharsets.UTF_8), sKey, iv.getBytes()));
//		String encryptionRequestBody = Base64.getEncoder().encodeToString(encData);
		return Base64.getEncoder().encodeToString((Base64.getEncoder().encodeToString(encData) + "::" + iv).getBytes());
	}

//	public static String decryptNew(String encodedStr) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
//		String[] split = new String(Base64.getDecoder().decode(encodedStr)).split("::");
//		SecretKey sKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGO_AES);
//		return (decrypt(Base64.getDecoder().decode(split[0].getBytes()), sKey, split[1].getBytes()));
//	}
	
	public static String decryptNew(String encodedStr) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
			/*
			 * invalid Base64 handle 
			 */
			Base64.getDecoder().decode(encodedStr.getBytes(StandardCharsets.UTF_8));
		} catch (IllegalArgumentException e) {
			return encodedStr;
		}

		String[] split = new String(Base64.getDecoder().decode(encodedStr.getBytes(StandardCharsets.UTF_8))).split("::");
		
		/*
		 * invalid Encrypt String handle 
		 */
		if(null != split && split.length != 2)
			return encodedStr;
		SecretKey sKey = new SecretKeySpec(Base64.getDecoder().decode(key), ALGO_AES);
		return (decrypt(Base64.getDecoder().decode(split[0].getBytes()), sKey, split[1].getBytes()));
	}

	public static byte[] encryptWithPrefixIV(byte[] pText, SecretKey secret, byte[] iv)
			throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		byte[] cipherText = encrypt(pText, secret, iv);
		byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length + cipherText.length).put(iv).put(cipherText).array();
		return cipherTextWithIv;
	}

	public static String decrypt(byte[] cText, SecretKey secret, byte[] iv) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
		cipher.init(Cipher.DECRYPT_MODE, secret, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
		byte[] plainText = cipher.doFinal(cText);
		return new String(plainText, UTF_8);
	}

	public static String decryptWithPrefixIV(byte[] cText, SecretKey secret) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		ByteBuffer bb = ByteBuffer.wrap(cText);
		byte[] iv = new byte[IV_LENGTH_BYTE];
		bb.get(iv);
		byte[] cipherText = new byte[bb.remaining()];
		bb.get(cipherText);
		String plainText = decrypt(cipherText, secret, iv);
		return plainText;
	}

	public static byte[] getRandomNonce() {
		byte[] nonce = new byte[IV_LENGTH_BYTE];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	public static SecretKey getAESKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(AES_KEY_BIT, SecureRandom.getInstanceStrong());
		return keyGen.generateKey();
	}

	public static String decryptSecretKey(String data, String privatekeyPath)
			throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException, InvalidKeySpecException {
		byte[] encryptedData = Base64.getDecoder().decode(data);
		PrivateKey privateKey = getPrivatekey(privatekeyPath);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] encryptedByte = cipher.doFinal(encryptedData);
		return new String(encryptedByte);
	}

	public static String decryptSecretKeyByPublicKey(String data, String publickeyPath)
			throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException, InvalidKeySpecException, CertificateException, IOException {
		byte[] encryptedData = Base64.getDecoder().decode(data);
		PublicKey publicKey = getPublicKey(publickeyPath);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] decryptedByte = cipher.doFinal(encryptedData);
		return new String(decryptedByte);
	}

	public static String encryptSecretKey(String data, String publickeyPath)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, CertificateException, IOException {
		byte[] plaintext = data.getBytes();
		PublicKey publicKey = getPublicKey(publickeyPath);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] encryptedByte = cipher.doFinal(plaintext);
		return Base64.getEncoder().encodeToString(encryptedByte);
	}

	public static String encryptSecretKeyByPrivateKey(String data, String privatekeyPath)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		byte[] plaintext = data.getBytes();
		PrivateKey privateKey = getPrivatekey(privatekeyPath);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] encryptedByte = cipher.doFinal(plaintext);
		return Base64.getEncoder().encodeToString(encryptedByte);
	}

	public static PrivateKey getPrivatekey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PrivateKey privateKey = null;
		KeyFactory keyFactory = null;
		byte[] encoded = DatatypeConverter.parseBase64Binary(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		keyFactory = KeyFactory.getInstance("RSA");
		privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static PublicKey getPublicKey(String publickeyCert) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publickeyCert)));
		return publicKey;
	}

	public static void main(String[] args) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		String encData = AesGcmEncryptionUtils.encryptNew("\t This is developed by Sandip Bhetariya"
				+ "   "
				+ "\n"
				+ "\t"
				+ "®");
		System.out.println(encData);
		System.out.println("Dec Data : " + encData);
		String decData = AesGcmEncryptionUtils.decryptNew(encData);
		System.out.println("Dec Data : " + decData);
		

	}

	public static String getKey() {
		return key;
	}

}
