package com.opl.cbdc.utils.config;

import com.opl.cbdc.utils.common.EncryptionUtils;

public class DataSourceProvider {

	private static String databaseName = null;
	private static String userName = null;
	private static String password = null;
	private static String databaseNamePushPull = null;
	private static String userNamePushPull = null;
	private static String passwordPushPull = null;
	// private static String readReplicaDatabaseName = null;
	private static final String URL_KEY = "ANS_DB_URL";
	// for QA url
//	private static final String URL_KEY = "ANS_DB_URL_QA";
	private static final String USER_NAME_KEY = "ANS_DB_USER_NAME";
	private static final String PASS_KEY = "ANS_DB_USER_PASS";
	// private static final String READ_REPLICA_URL_KEY = "readReplicaUrl";
	// private static final ResourceBundle bundle = ResourceBundle.getBundle("database");
	private static final String URL_KEY_PUSH_PULL = "ANS_DB_URL_PUSH_PULL";
	private static final String USER_NAME_KEY_PUSH_PULL = "ANS_DB_USER_NAME_PUSH_PULL";
	private static final String PASS_KEY_PUSH_PULL = "ANS_DB_USER_PASS_PUSH_PULL";

	private static String decryptKey(String key) {
		String value = System.getenv(key);
		EncryptionUtils cryptoConverter = new EncryptionUtils();
		return cryptoConverter.convertToEntityAttribute(value);
	}

	public static String getDatabaseName() {
		if (null == databaseName) {
			databaseName = decryptKey(URL_KEY);
			return databaseName;
		} else {
			return databaseName;
		}
	}

	public static String getUserName() {
		if (null == userName) {
			userName = decryptKey(USER_NAME_KEY);
			return userName;
		} else {
			return userName;
		}
	}

	public static String getPassword() {
		if (null == password) {
			password = decryptKey(PASS_KEY);
			return password;
		} else {
			return password;
		}
	}

//	public static String getReadReplicaDatabaseName() {
//		if (null == readReplicaDatabaseName) {
//			readReplicaDatabaseName = decryptKey(READ_REPLICA_URL_KEY);
//			return readReplicaDatabaseName;
//		} else {
//			return readReplicaDatabaseName;
//		}
//	}

	public static String getDatabaseNamePushPull() {
		if (null == databaseNamePushPull) {
			databaseNamePushPull = decryptKey(URL_KEY_PUSH_PULL);
			return databaseNamePushPull;
		} else {
			return databaseNamePushPull;
		}
	}

	public static String getUserNamePushPull() {
		if (null == userNamePushPull) {
			userNamePushPull = decryptKey(USER_NAME_KEY_PUSH_PULL);
			return userNamePushPull;
		} else {
			return userNamePushPull;
		}
	}

	public static String getPasswordPushPull() {
		if (null == passwordPushPull) {
			passwordPushPull = decryptKey(PASS_KEY_PUSH_PULL);
			return passwordPushPull;
		} else {
			return passwordPushPull;
		}
	}
}
