package com.techinterview.ipvalidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;

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
	public static boolean validateIpAddress(String ipAddress, String cidrRange) {
		  //Reference
		  //https://stackoverflow.com/questions/9622967/how-to-see-if-an-ip-address-belongs-inside-of-a-range-of-ips-using-cidr-notation
		  
		  Pattern ptn = Pattern.compile("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	        Matcher mtch = ptn.matcher(ipAddress);
	        if(mtch.find())
	        {

	    		int addressInt = convertAddressToInt(ipAddress);
	    		Pair<Integer, Integer> cidrBits = splitCidrIpAddress(cidrRange);
	    		int mask = cidrBits.getValue();
	    		return (addressInt & mask) == (cidrBits.getKey() & mask);
	        }
	        else
	        {
	        	throw new IllegalArgumentException("Invalid argument");
	        }
	    
	

}
private static Pair<Integer, Integer> splitCidrIpAddress(String cidrRange) {
	int slash = cidrRange.indexOf('/');
	int subnet = Integer.parseInt(cidrRange.substring(slash + 1));
	if(subnet<0 || subnet>32) throw new IllegalArgumentException("Invalid Range");
	int bits = convertAddressToInt(cidrRange.substring(0, slash));
	int mask = ~((1 << (32 - subnet)) - 1);

	return new Pair<Integer, Integer>(bits, mask);
	}

private static int convertAddressToInt(String ipAddress) {
		// TODO Auto-generated method stub
	String[] parts = ipAddress.split("\\."); 
	if( parts.length != 4 )
	{
		throw new IllegalArgumentException("IP Address did not have 4 parts");
	}

	int results = Integer.parseInt(parts[3]) & 0xFF;
	results |= (Integer.parseInt(parts[2]) & 0xFF) << 8;
	results |= (Integer.parseInt(parts[1]) & 0xFF) << 16;
	results |= (Integer.parseInt(parts[0]) & 0xFF) << 24;

	return results;
	}



public static void main(String args[]) throws Exception
{
	System.out.println("Is valid? "+validateIpAddress("192.168.0.0", "192.168.0.0/24"));
	//Output Is valid? true
	
	System.out.println("Is valid? "+validateIpAddress("100.0.0.0", "100.0.0.0/16"));
	//Output Is valid? true
	
	System.out.println("Is valid? "+validateIpAddress("10.10.0.0", "10.10.0.0/32"));
	//Output Is valid? true
	
	System.out.println("Is valid? "+validateIpAddress("10.10.0.1", "10.10.0.2/32"));
	//Output Is valid? false
	
	System.out.println("Is valid? "+validateIpAddress("10.10.0.1", "10.10,0.2/32"));
	//Output is IllegalArgumentException: IP Address did not have 4 parts
}
}
