package com.techinterview.onlinestore.service;

import com.techinterview.onlinestore.domain.BackPack;
import com.techinterview.onlinestore.domain.Product;
import com.techinterview.onlinestore.domain.SmartPhone;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Implement the function productListToString that does conversion of provided list of products to text representation
 * of this list in this way.
 * - Result string is \n - separated string. E.g.
 *
 *   product 1 details
 *   product 2 details
 *   ...
 *   product 3 details
 *
 * - Each line contains details of one product
 * - Product description line should look like this:
 *   Product name (product GUID), product attribute 1 name:product attribute1 value; ... product attribute n name:product attributen value;
 *   For example. List contains BackPack definition:
 *   BackPack {
 *       guid: 111-222-333
 *       name: Cool Backpack
 *       maxContentWeight: 15
 *       color: "Black"
 *       capacity: 20
 *   }
 *   This becka pakc description string should look like this:
 *   Cool Backpack (111-222-333), maxContentWeight: 15, color: "Black", capacity: 20
 *
 * Keep in mind these requirements:
 * - String reorientation can be modified in future.
 * - There is a possibility to support multiply ways to convert list of products to string. E.g. it it is possible that in future
 *   you will need to implement support of different formats of outpust string (e.g. json instead of \n-separated string).
 * The basic idea is to make your current implementation flexible and modifiable in future.
 *
 * You can use any build system to build the sources (maven, gralde, ant).
 * You can use any 3rd party libs in your application.
 * Any java version (>=8).
 * Code must be tested (framework is up to you).
 * 
 * PLEASE KEEP IN MIND THAT IT CAN BE THOUSANDS OF PRODUCT IMPLEMENTATIONS, NOT JUST 2!!! INSTANCE OF SOLUTION IS BAD AND IS NOT GOING TO 
 * WORK IN A REAL WORLD!
 * 
 * Product classes (base class and implementations) can be changed to resolve the task, Any new methods, properties or anything else can be added there.
 * 
 * If you are familiar with Git, please do work in a separate branch and create a pull request with your changes.
 */
public class ProductListProcessor {

    /**
     * Make String representation of providd product list.
     * @param products list of the products that needs to be converted to String
     * @return String representation of the provided list.
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */
    public String productListToString(List<Product> products) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	StringBuffer op = new StringBuffer();
		for(Product pr : products){
			Class<?> c = pr.getClass();
			Method mGuid = c.getSuperclass().getDeclaredMethod("getGuid");
			Method mName = c.getSuperclass().getDeclaredMethod("getName");
			op.append(mName.invoke(pr));
			op.append(" (");
			op.append(mGuid.invoke(pr));
			op.append(")");
			for(Method me : c.getDeclaredMethods()){
				if(me.getName().startsWith("get")){
					op.append(", "+me.getName().substring(3)+": ");
					op.append(me.invoke(pr).toString());
					//System.out.println(me.invoke(pr));
				}
			}
			op.append("\n");
		}
        return op.toString();
    }
    
    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		// Test Case
    	List<Product> l = new ArrayList<Product>();
		BackPack b1 = new BackPack("1", "b1");
		b1.setCapacity(10);
		b1.setColor("Blue");
		b1.setMaxContentWeight(20);
		l.add(b1);
		SmartPhone s1 = new SmartPhone("2", "Note 9");
		s1.setColor("Blue");
		s1.setManufacturer("Samsung");
		s1.setNumberOfCPUs(8);
		s1.setRamSize(4);
		s1.setScreenResolution("6.3\"");
		l.add(s1);
		BackPack b2 = new BackPack("3", "b2");
		b2.setCapacity(5);
		b2.setColor("Red");
		b2.setMaxContentWeight(15);
		l.add(b2);
		SmartPhone s2 = new SmartPhone("4", "S9 Plus");
		s2.setColor("Black");
		s2.setManufacturer("Samsung");
		s2.setNumberOfCPUs(10);
		s2.setRamSize(8);
		s2.setScreenResolution("5.8\"");
		l.add(s2);
		
		ProductListProcessor p = new ProductListProcessor();
		System.out.println(p.productListToString(l));
	}
}
