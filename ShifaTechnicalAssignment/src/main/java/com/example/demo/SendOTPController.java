package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendOTPController {

	
	@Autowired
	OTPServices oTPServices;
	
	@GetMapping("/receiveOTP")
	public ResponseEntity<String> getapi(@PathVariable ("email") String email) throws UnsupportedEncodingException, MessagingException
	{
		boolean isEmailPresent=EmailValidation.emailPresent(email);
		if(isEmailPresent)
		{
		boolean isValidEmail=EmailValidation.validemail(email);
		if(isValidEmail)
		{
			String OTP=oTPServices.generateOneTimePassword(email);
			oTPServices.sendOTPEmail(email,OTP);
			oTPServices.clearOTP(email);
			return ResponseEntity.status(HttpStatus.OK).body("EMAIL_OK: email containing OTP has been sent successfully.");
			
		}
		else{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EMAIL_INVALID: email address is invalid.");
		}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EMAIL_FAIL: email address does not exist or sending to the email has failed.");
		}
		
		
	}
}


