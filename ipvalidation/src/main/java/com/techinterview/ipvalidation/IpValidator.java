package com.techinterview.ipvalidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpValidator {

    /**
     * Implement the function that validates if provided IP address is in the range defined by cidrRange parameter.
     * You can read about CIDR here https://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing
     * Also, you can use this page https://www.ipaddressguide.com/cidr to understand better CIDR concept.
     *
     * Code organization, implementing other functions that are called from validateAddress - all of this up to you.
     * The final code must be compilable, buildable and tested.
     *
     * You are free to use any build framework to build the code (maven, gradle, ant, etc.).
     *
     * You can use any java version for development (8, 9, 10, 11).
     *
     * You can use any libraries to simplify any auxiliary logic (e.g. string parsing).
     * But you cannot use any 3rd party libraries to implement validation itself.
     *
     * Testing framework selection is up to you. Testing the logic from "public static void main" is also OK.
     *
     * If you are familiar with Git, do your work in a branch and create pull request.
     *;
     * @param ipAddress String representation of IP address that needs to be validated.
     *                  Function must verify that IP address definition itself is valid.
     *                  If IP address format is invalid, function must throw IllegalArgumentException.
     *                  E.g. 192.168.0.1 is correct definition of IP address.
     *                  256.0.0.1, 192,168.o.1, 192,168.0.1 are examples of invalid IP addresses.
     * @param cidrRange Classless Inter-Domain Routing (CIDR) definition that defines range of allowed IP addresses.
     *                  For example 11.1.0.0/24 CIDR defines IP addresses range from 11.1.0.0 to 11.1.0.255
     *                  Function must verify that cidrRange definition itself is valid.
     *                  If cidrRange format is invalid, function must throw IllegalArgumentException.
     *                  E.g. 192.168.0.0/24, 100.0.0.0/16, 10.10.0.0/32, 10.10.0.1/16 are valid definitions.
     *                  192.168.0.0/35, 300.0.0.0/16, 10.10,0.0/32, 10.10.0.256/16 are invalid definitions
     *                  If slash is omitted in cidrRange definition, you can assume that / 32 is used.
     *                  E.g. cidrRange 10.10.0.1 can be treated as 10.10.0.1/32
     * @throws IllegalArgumentException if either ipAddress or cidrRange definitions is invalid.
     * @return true if provided IP address is covered by the CIDR range; false otherwise.
     */
	
	public static String getBinaryArray(String ipAddress) throws NullPointerException{
		String[] array=ipAddress.split("\\.");
		StringBuffer buffer= new StringBuffer();
		for (String string : array) {
			String s=Integer.toBinaryString(Integer.valueOf(string));
			if(s.length()<8){
				for (int i = 0; i < (8-s.length()); i++) {
					buffer.append("0");
				}
				buffer.append(s);
			}else{
				buffer.append(s);
			}
		}
		return buffer.toString();
	}
	
    public static boolean validateIpAddress(String ipAddress, String cidrRange) {
    	Pattern ipv4 = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
        Pattern ipv6 = Pattern.compile("([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}", Pattern.CASE_INSENSITIVE);
        Pattern ipv6Hex = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
        
        Matcher mIpv4 = ipv4.matcher(ipAddress);
        Matcher mIpv6 = ipv6.matcher(ipAddress);
        Matcher mIpv6Hex = ipv6Hex.matcher(ipAddress);
        
        String[] cidrIP = new String[2];
        if(cidrRange.contains("/")) { cidrIP = cidrRange.split("/"); } else{ cidrIP[0] = cidrRange; cidrIP[1] = "32"; }
        	
        Matcher mCidrIpv4 = ipv4.matcher(cidrIP[0]);
        Matcher mCidrIpv6 = ipv6.matcher(cidrIP[0]);
        Matcher mCidrIpv6Hex = ipv6Hex.matcher(cidrIP[0]);
        
        if((mIpv4.find() || mIpv6.find() || mIpv6Hex.find()) && (mCidrIpv4.find() || mCidrIpv6.find() || mCidrIpv6Hex.find()) && Integer.valueOf(cidrIP[1]) <= 32){
        	String inputIP = getBinaryArray(ipAddress);
        	String cidr = getBinaryArray(cidrIP[0]);
        	for(int i=0;i<Integer.valueOf(cidrIP[1]);i++){
        		if(inputIP.charAt(i)==cidr.charAt(i)){
        			continue;
        		}else{
        			throw new IllegalArgumentException("Invalid Argument");
        		}
        	}
        	return true;
        }else{
        	 throw new IllegalArgumentException("Invalid Argument");
        }
    }
    
    public static void main(String[] args) {
    	//Test cases
		System.out.println(validateIpAddress("192.168.0.1", "192.168.0.1/24"));
		System.out.println(validateIpAddress("192.168.0.1", "192.168.1.1/23"));
		System.out.println(validateIpAddress("100.0.0.1", "100.0.0.0/16"));
		System.out.println(validateIpAddress("10.10.0.0", "10.10.0.0/32"));
		System.out.println(validateIpAddress("10.10.0.1", "10.10.0.0/16"));
		System.out.println(validateIpAddress("192.168.0.1", "192.168.2.1/23"));
		System.out.println(validateIpAddress("10.10.0.1", "10.10.0.256/16"));
		System.out.println(validateIpAddress("10.10.0.1", "10.10,0.0/32"));
		System.out.println(validateIpAddress("300.0.0.1", "300.0.0.0/16"));
		System.out.println(validateIpAddress("192.168.0.1", "192.168.0.0/35"));
	 }
}
