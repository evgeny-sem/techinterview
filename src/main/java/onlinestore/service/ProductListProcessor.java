package onlinestore.service;

import onlinestore.domain.BackPack;
import onlinestore.domain.Product;
import onlinestore.domain.SmartPhone;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implement the function productListToString that does conversion of provided list of products to text representation
 * of this list in this way.
 * - Result string is \n - separated string. E.g.
 * <p>
 * product 1 details
 * product 2 details
 * ...
 * product 3 details
 * <p>
 * - Each line contains details of one product
 * - Product description line should look like this:
 * Product name (product GUID), product attribute 1 name:product attribute1 value; ... product attribute n name:product attributen value;
 * For example. List contains BackPack definition:
 * BackPack {
 * guid: 111-222-333
 * name: Cool Backpack
 * maxContentWeight: 15
 * color: "Black"
 * capacity: 20
 * }
 * This becka pakc description string should look like this:
 * Cool Backpack (111-222-333), maxContentWeight: 15, color: "Black", capacity: 20
 * <p>
 * Keep in mind these requirements:
 * - String reorientation can be modified in future.
 * - There is a possibility to support multiply ways to convert list of products to string. E.g. it it is possible that in future
 * you will need to implement support of different formats of outpust string (e.g. json instead of \n-separated string).
 * The basic idea is to make your current implementation flexible and modifiable in future.
 * <p>
 * You can use any build system to build the sources (maven, gralde, ant).
 * You can use any 3rd party libs  in your application.
 * Any java version (>=8).
 * Code must be tested (framework is up to you).
 * <p>
 * If you are familiar with Git, please do work in a separate branch and create a pull request with your changes.
 */
public class ProductListProcessor {

    public ArrayList<Field> getFields(Product product) {
        Field[] fields = product.getClass().getDeclaredFields();
        ArrayList<Field> fieldArrayList = new ArrayList<Field>(Arrays.asList(fields));
        return fieldArrayList;
    }

    public ArrayList<Method> getGetMethods(Product product) {
        Method[] methods = product.getClass().getDeclaredMethods();
        ArrayList<Method> getMethod = new ArrayList<Method>();
        for (Method method : methods) {
            if (isGetter(method))
                getMethod.add(method);
        }
        return getMethod;
    }

    public boolean isGetter(Method method) {
        if (method.getName().startsWith("get"))
            return true;
        return false;
    }

    public String joinStrings(ArrayList<Field> fields, ArrayList<Method> methods, Product product) throws InvocationTargetException, IllegalAccessException {
        String guid = product.getGuid();
        String name = product.getName();
        StringBuilder sentence = new StringBuilder(name + " (" + guid + ")");

        if (fields.size() != methods.size()) return null;
        for (int i = 0; i < fields.size(); i++) {

            Object[] args = null;
            Object value = methods.get(i).invoke(product, args);

            sentence.append(fields.get(i).getName());
            sentence.append(": ");
            sentence.append(value + ", ");
        }
        return sentence.toString();
    }

    public String displayProductDetails(ArrayList<String> details) {
        StringBuilder sentence = new StringBuilder();
        for (String s : details) {
            sentence.append(s);
            sentence.append("\n");
        }
        return sentence.toString();
    }

    public String productListToString(List<Product> products) throws InvocationTargetException, IllegalAccessException {
        ArrayList<String> details = new ArrayList<String>();

        for (Product product : products) {
            ArrayList<Field> fields = new ArrayList<Field>();
            fields = getFields(product);
            ArrayList<Method> methods = new ArrayList<Method>();
            methods = getGetMethods(product);
            String detail = joinStrings(fields, methods, product);
            details.add(detail);
        }

        return displayProductDetails(details);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        SmartPhone sp1 = new SmartPhone("111-111-111", "iPhone X");
        List<SmartPhone> smartPhones = new ArrayList<SmartPhone>();
        sp1.setColor("Black");
        sp1.setManufacturer("iPhone");
        sp1.setNumberOfCPUs(2);
        sp1.setRamSize(2);
        sp1.setScreenResolution("2436 x 1125");
        smartPhones.add(sp1);

        BackPack bp1 = new BackPack("123-123-123", "Nike 50");
        List<BackPack> backPacks = new ArrayList<BackPack>();
        bp1.setCapacity(5000);
        bp1.setColor("Navy Blue");
        bp1.setMaxContentWeight(20);
        backPacks.add(bp1);

        final List<Product> products = new ArrayList<Product>();
        products.add(sp1);
        products.add(bp1);

        ProductListProcessor productListProcessor = new ProductListProcessor();
        System.out.print(productListProcessor.productListToString(products));
    }
}
