package com.ef.jcpt.core.digest;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.lang3.Validate;

import com.ef.jcpt.core.common.Exceptions;

public class Digests {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";
	public static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";
	public static final int HASH_INTERATIONS = 20000;
	public static final int SALT_SIZE = 8;
	private static SecureRandom random = new SecureRandom();

	public static byte[] md5(byte[] input) {
		return digest(input, "MD5", null, 1);
	}

	public static byte[] md5(byte[] input, int iterations) {
		return digest(input, "MD5", null, iterations);
	}

	public static byte[] sha1(byte[] input) {
		return digest(input, "SHA-1", null, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, "SHA-1", salt, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, "SHA-1", salt, iterations);
	}

	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			if (salt != null) {
				digest.update(salt);
			}
			byte[] result = digest.digest(input);
			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	public static byte[] md5(InputStream input) throws IOException {
		return digest(input, "MD5");
	}

	public static byte[] sha1(InputStream input) throws IOException {
		return digest(input, "SHA-1");
	}

	private static byte[] digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8192;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);
			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}
			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	public static String entryptPassword(String plainPassword) {
		byte[] salt = generateSalt(8);

		byte[] hashPassword = getEncryptedPassword(plainPassword, salt);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}

	public static String getMD5(String text) {
		String returnStr = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			returnStr = Encodes.encodeHex(md.digest(text.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	public static String getSHA1(String text) {
		String returnStr = "";
		try {
			MessageDigest md = MessageDigest.getInstance(SHA1);
			returnStr = Encodes.encodeHex(md.digest(text.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));

		byte[] hashPassword = getEncryptedPassword(plainPassword, salt);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}

	public static byte[] getEncryptedPassword(String password, byte[] salt) {
		int derivedKeyLength = 160;
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 20000, derivedKeyLength);
		try {
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			return f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(validatePassword("123456", "2ce776b962772cdce1b4161714fe7f9de94767776ba9368454bceb3f"));
		// System.out.println(entryptPassword("123456"));//9f85c27e79e812e76e02772c5b933e56b2fafa2c2f1956328c1d6967
		// System.out.println("9f85c27e79e812e76e02772c5b933e56b2fafa2c2f1956328c1d6967".length());
		// System.out.println(validatePassword("123456",
		// "9f85c27e79e812e76e02772c5b933e56b2fafa2c2f1956328c1d6967"));
		// System.out.println(entryptPassword("kviuff"));
		// System.out.println(validatePassword("kviuff",
		// "66073c9476862b7e4c4e381ce415eb1469f77b6a"));
		// System.out.println(getMD5("彭宁"));
	}
}
