package com.example.demo;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public class EmailValidation {

	public static boolean validemail(String email) {
		if(email.contains(".dso.org.sg"))
		return true;
		
		return false;
	}

	public static boolean emailPresent(String email) {
		// TODO Auto-generated method stub
		
		return true;
	}

}
