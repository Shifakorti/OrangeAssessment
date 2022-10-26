package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateOTPController {

	@Autowired
	OTPServices otpService;
	
	@GetMapping("/checkOTP")
	@ResponseBody
	public  ResponseEntity<String> validateOTP(@RequestParam("otpnum") int OTP,@RequestParam("email") String email)
	{

		//Validate the Otp 
		if(OTP >= 0){
			int count =10;
		int serverOtp = otpService.getOTP(email);
     
		if(serverOtp > 0){
			for(int i=1;i<=count;i++)
		     {
		if(OTP == serverOtp){
		otpService.clearOTP(email);
		return ResponseEntity.status(HttpStatus.OK).body("OTP_OK: OTP is valid and checked");
		}
		     }
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP_TIMEOUT: timeout after 1 min");
		
		}
		
		}
		
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("OTP_FAIL: OTP is wrong after 10 tries");
		
		
		
	}
	
}
