package com.gemptc.thirdcode;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * GeetestWeb�����ļ�
 * 
 *
 */
public class GeetestConfig {

	// �����Լ���captcha_id��private_key
	private static final String geetest_id = "2d815ebf039e7b149e8e582f59b1a6b6";
	private static final String geetest_key = "cc47e7271b1f0db408201dd27f8222fd";
	private static final boolean newfailback = true;

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}
	
	public static final boolean isnewfailback() {
		return newfailback;
	}

}
