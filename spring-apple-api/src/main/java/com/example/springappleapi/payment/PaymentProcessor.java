package com.example.springappleapi.payment;

import com.example.springcybersource.*;
import com.example.springappleapi.models.*;

public class PaymentProcessor {
    private static boolean DEBUG = true ;

	private String apiHost;
    private String merchantKeyId;
    private String merchantsecretKey;
    private String merchantId;

	public PaymentProcessor(String apiHost, String merchantKeyId, String merchantsecretKey, String merchantId){
		this.apiHost = apiHost;
		this.merchantKeyId = merchantKeyId;
		this.merchantsecretKey = merchantsecretKey;
		this.merchantId = merchantId;
	}

	private String getMonthNumeric(String month){
		return month.length() == 1 ? ("0" + month) : month;
	}

	private String getCardType(String cardNum){
		char firstDigit = cardNum.charAt(0);
		switch(firstDigit){
			case '3':
				return "003";
			case '4':
				return "001";
			case '5':
				return "002";
			case '6':
				return "004";
			default:
				return null;
		}
	}

    public String process(PaymentsCommand paymentCommand, String amount, long cartId) {
		
        System.out.println("===== CYBERSOURCE PAYMENT TEST =====") ;

        if (DEBUG) {
         	System.out.println( apiHost ) ;
        	System.out.println( merchantKeyId ) ;
        	System.out.println( merchantsecretKey ) ;
        	System.out.println( merchantId ) ;         	
        }
 
		CyberSourceAPI api = new CyberSourceAPI() ;
		CyberSourceAPI.setHost( apiHost ) ;
		CyberSourceAPI.setKey( merchantKeyId ) ;
		CyberSourceAPI.setSecret(merchantsecretKey ) ;
		CyberSourceAPI.setMerchant( merchantId ) ;

		AuthRequest auth = new AuthRequest() ;
		auth.reference = "CartId: " + cartId ;
		auth.billToFirstName = paymentCommand.getFirstname() ;
		auth.billToLastName = paymentCommand.getLastname() ;
		auth.billToAddress = paymentCommand.getAddress() ;
		auth.billToCity = paymentCommand.getCity() ;
		auth.billToState = paymentCommand.getState();
		auth.billToZipCode = paymentCommand.getZip() ;
		auth.billToPhone = "4083341234";//paymentCommand.getPhone();
		auth.billToEmail = paymentCommand.getEmail() ;
		auth.transactionAmount = amount ;
		auth.transactionCurrency = "USD" ;
		auth.cardNumnber = paymentCommand.getCardnum() ;
		auth.cardExpMonth = getMonthNumeric(paymentCommand.getCardexpmon()) ;
		auth.cardExpYear = paymentCommand.getCardexpyear() ;
		auth.cardCVV = paymentCommand.getCardcvv() ;
		auth.cardType = getCardType(paymentCommand.getCardnum()) ;
		boolean authValid = false ;
		AuthResponse authResponse = new AuthResponse() ;
		System.out.println("\n\nAuth Request: " + auth.toJson() ) ;
		authResponse = api.authorize(auth) ;
		System.out.println("\n\nAuth Response: " + authResponse.toJson() ) ;
		if ( authResponse.status.equals("AUTHORIZED") ) {
			authValid = true ;
		}
		
		boolean captureValid = false ;
		CaptureRequest capture = new CaptureRequest() ;
		CaptureResponse captureResponse = new CaptureResponse() ;
		if ( authValid ) {
			capture.reference = "CartId: " + cartId  ;
			capture.paymentId = authResponse.id ;
			capture.transactionAmount = amount ;
			capture.transactionCurrency = "USD" ;
			System.out.println("\n\nCapture Request: " + capture.toJson() ) ;
			captureResponse = api.capture(capture) ;
			System.out.println("\n\nCapture Response: " + captureResponse.toJson() ) ;
			if ( captureResponse.status.equals("PENDING") ) {
				captureValid = true ;
			}
			return "" + authResponse.toJson() + "|" + captureResponse.toJson();
		}
		return "" + authResponse.toJson();
    }	
}

