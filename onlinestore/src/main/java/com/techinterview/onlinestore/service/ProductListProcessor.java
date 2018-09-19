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
     */
    public String productListToString(List<Product> products) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		StringBuilder sb=new StringBuilder();
		for(Object value:products) {
			Class<? extends Object> cls=value.getClass();
			
			Method superClassMethod1 = cls.getSuperclass().getMethod("getName");
			sb.append(superClassMethod1.invoke(value));
			sb.append(" ");
			sb.append("(");
			Method superClassMethod2 = cls.getSuperclass().getMethod("getGuid");
			sb.append(superClassMethod2.invoke(value));
			sb.append(")");
			
			// Using reflection concept in Java, all the methods of the class are found
			for(Method methodName : cls.getDeclaredMethods()){
				//System.out.println(methodName);
				if(methodName.getName().startsWith("get")){
					sb.append(", "+methodName.getName().substring(3)+": ");
					sb.append(methodName.invoke(value));
					//invokes the method at runtime
				}
			}
			sb.append("\n");
		}
		return sb.toString();
}


public static void main(String args[]) throws Exception{
	
	ProductListProcessor obj=new ProductListProcessor();
	List<Product> list1=new ArrayList<Product>();
	
	List<Product> list2=new ArrayList<Product>();
	
	SmartPhone phone1=new SmartPhone("100-200-300", "Iphone");
	phone1.setColor("Silver");
	phone1.setManufacturer("Apple");
	phone1.setNumberOfCPUs(8);
	phone1.setRamSize(16);
	phone1.setScreenResolution("4.7");
list1.add(phone1);

SmartPhone phone2=new SmartPhone("100-222-300", "Galaxy Grand");
phone2.setColor("White");
phone2.setManufacturer("Samsung");
phone2.setNumberOfCPUs(9);
phone2.setRamSize(8);
phone2.setScreenResolution("5.5");
list1.add(phone2);

BackPack bag1=new BackPack("100-222-565", "Travel Bag");
bag1.setColor("Red");
bag1.setMaxContentWeight(50);
bag1.setCapacity(9);
list1.add(bag1);

BackPack bag2=new BackPack("600-222-440", "Mini Bag");
bag2.setColor("Blue");
bag2.setMaxContentWeight(20);
bag2.setCapacity(10);
list1.add(bag2);

System.out.println(obj.productListToString(list1));
System.out.println(obj.productListToString(list2)); // empty list

}
}
