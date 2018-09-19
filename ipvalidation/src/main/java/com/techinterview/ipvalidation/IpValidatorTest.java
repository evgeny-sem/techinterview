package com.techinterview.ipvalidation;

import static org.junit.Assert.*;

import org.junit.Test;

public class IpValidatorTest {
	
	@Test
	public void test() {
		assertTrue(IpValidator.validateIpAddress("192.168.0.0", "192.168.0.0/24"));
		assertTrue(IpValidator.validateIpAddress("100.0.0.0", "100.0.0.0/16"));
		assertTrue(IpValidator.validateIpAddress("10.10.0.0", "10.10.0.0/32"));
		assertTrue(IpValidator.validateIpAddress("10.10.0.1", "10.10.0.1/16"));
		assertFalse(IpValidator.validateIpAddress("10.10.0.1", "10.10.0.2/32"));
		assertFalse(IpValidator.validateIpAddress("10.10.0.1", "10.10.0.7/31"));

	}

}
