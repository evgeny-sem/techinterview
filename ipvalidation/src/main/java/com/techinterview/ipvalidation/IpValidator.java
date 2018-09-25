package com.techinterview.ipvalidation;
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
     *
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
    private static final String ipv4_Regex=
			"^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
			"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
			"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
			"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	private static final String CIDR_Regex =
		"^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\\/(\\d|[1-2]\\d|3[0-2]))?$";
	 
    private static boolean isValidIP(String ipAddress)
	 {
		 boolean patternValue = false;
		 
		 Pattern ptn = Pattern.compile(ipv4_Regex);
	     Matcher mtch = ptn.matcher(ipAddress);
	     patternValue = mtch.find();
	     		
	     if (!patternValue)
	    	 throw new IllegalArgumentException("IP address format is invalid");
	     return patternValue;
	 }
    private static boolean isValidCIDR(String cidr)
	 { 
		 Pattern ptn = Pattern.compile(CIDR_Regex);
	     Matcher mtch = ptn.matcher(cidr);
	     return mtch.find();
	 }
    
    private static boolean cidrIsInRange(String ipAddress, String cidr)
    {
		 if (!isValidCIDR(cidr))
			 throw new IllegalArgumentException("CIDR format is invalid");
		String[] cidrSlashSplit = cidr.split("/");
		String[] cidrSplit = cidrSlashSplit[0].split("\\.");
		
    	int addr = (( Integer.parseInt(cidrSplit[0]) << 24 ) & 0xFF000000) 
    	           | (( Integer.parseInt(cidrSplit[1]) << 16 ) & 0xFF0000) 
    	           | (( Integer.parseInt(cidrSplit[2]) << 8 ) & 0xFF00) 
    	           |  ( Integer.parseInt(cidrSplit[3]) & 0xFF);
    	
    	int mask = (-1) << (32 - 10);
    	 
    	int lowest = addr & mask;
    	 
    	int highest = lowest + (~mask);
    	
    	String[] ipSplit = ipAddress.split("\\.");
    	int ip = (( Integer.parseInt(ipSplit[0]) << 24 ) & 0xFF000000) 
 	           | (( Integer.parseInt(ipSplit[1]) << 16 ) & 0xFF0000) 
 	           | (( Integer.parseInt(ipSplit[2]) << 8 ) & 0xFF00) 
 	           |  ( Integer.parseInt(ipSplit[3]) & 0xFF);
    	
    	if (lowest <= ip && ip <= highest)
    		return true;
    	else
    		return false;
    	
    }
    
    
    public static boolean validateIpAddress(String ipAddress, String cidrRange) {
        if ( ipAddress == null || cidrRange == null)
		   return false;
		 
	     if (!cidrRange.contains("/"))
	    	 cidrRange = cidrRange + "/32";
	     
	     if (isValidIP(ipAddress) &&
	         cidrIsInRange(ipAddress, cidrRange))
	    	 return true;
	     else
	    	 return false;
    }
    
    public static void main(String[] args)
{
	//Test cases
	System.out.println(IpValidator.validateIpAddress("11.1.0.0", "10.10.0.1"));
	System.out.println(IpValidator.validateIpAddress("192,168.0.1", "192.168.0.0/24"));
	System.out.println(IpValidator.validateIpAddress("192,168.0.1", ""));
	System.out.println(IpValidator.validateIpAddress("172.8.9.256", ""));
	System.out.println(IpValidator.validateIpAddress("256.0.0.-11", ""));
	System.out.println(IpValidator.validateIpAddress("11.1.0.255", "11.1.0.0/24"));
	}
}
