package com.techinterview.onlinestore.service;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


import org.junit.BeforeClass;
import org.junit.Test;

import com.techinterview.onlinestore.domain.Product;
import com.techinterview.onlinestore.domain.SmartPhone;

public class ProductListProcessor_Test {
   	ProductListProcessor obj=new ProductListProcessor();
	List<Product> list3=new ArrayList<Product>();
	List<Product> list4=new ArrayList<Product>();
	SmartPhone phone3;
	//=new SmartPhone("100-200-399", "Iphone");
	
	@BeforeClass
    public void runBeforeClassMethod() {
		// This method will be run once
		phone3=new SmartPhone("100-200-300", "Iphone");
		phone3.setColor("Silver");
		phone3.setManufacturer("Apple");
		phone3.setNumberOfCPUs(8);
		phone3.setRamSize(16);
		phone3.setScreenResolution("4.7");
	    list3.add(phone3);
    } 
	
	@Test
	public void test1() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

	    String expOutput= "Iphone (100-200-300), Manufacturer: Apple, Color: Silver, NumberOfCPUs: 8, RamSize: 16.0, ScreenResolution: 4.7"+"\n";
		Object expectedOutput=expOutput;
	    assertEquals(expectedOutput, obj.productListToString(list3));
	}
	
	@Test
	public void test2() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
	
		assertEquals("", obj.productListToString(list4));
		//input passed to the productListToString is empty list
	}


}
