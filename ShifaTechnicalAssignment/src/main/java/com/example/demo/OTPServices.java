package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import java.util.concurrent.TimeUnit;


@Service
public class OTPServices {

	
	private static final Integer EXPIRE_MINS = 1;
	
	private com.google.common.cache.LoadingCache<String, Integer> otpCache;

	 public OTPServices(){
	 super();
	 otpCache = CacheBuilder.newBuilder().
	     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
	      public Integer load(String key) {
	             return 0;
	       }
	   });
	 }
	
    @Autowired JavaMailSender mailSender;
     
    public String generateOneTimePassword(String email)  {
    	    
            String otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
            
    	   return otp;
    	}
    
     
    public void sendOTPEmail(String email, String OTP) 
    	throws UnsupportedEncodingException, MessagingException {
    	    MimeMessage message = mailSender.createMimeMessage();              
    	    MimeMessageHelper helper = new MimeMessageHelper(message);
    	     
    	    helper.setFrom("shifakorti@gmail.com", "Shifa");
    	    helper.setTo(email);
    	     
    	    String subject = "Here's your One Time Password (OTP) - Expire in 1 minutes!";
    	     
    	    String content = "<p>Hello " + email + "</p>"
    	            + "<p>You OTP Code is "
    	            + "<p><b>" + OTP + "</b></p>"
    	            + "<br>"
    	            + "<p>The code is valid for 1 minute</p>";
    	    
    	    
    	     
    	    helper.setSubject(subject);
    	     
    	    helper.setText(content, true);
    	     
    	    mailSender.send(message); 
    }
 
     
     public int getOTP(String key){ 
    	 try{
    	  return otpCache.get(key); 
    	 }catch (Exception e){
    	  return 0; 
    	 }
    	  }

    	 //This method is used to clear the OTP catched already
    	 public void clearOTP(String key){ 
    	  otpCache.invalidate(key);
    	  }
    	 }


