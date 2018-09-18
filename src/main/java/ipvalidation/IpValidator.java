package ipvalidation;

import java.util.regex.Pattern;

public class IpValidator {

    /**
     * Implement the function that validates if provided IP address is in the range defined by cidrRange parameter.
     * You can read about CIDR here https://en.wikipedia.org/wiki/Classless_Inter-Domain_Routing
     * Also, you can use this page https://www.ipaddressguide.com/cidr to understand better CIDR concept.
     * <p>
     * Code organization, implementing other functions that are called from validateAddress - all of this up to you.
     * The final code must be compilable, buildable and tested.
     * <p>
     * You are free to use any build framework to build the code (maven, gradle, ant, etc.).
     * <p>
     * You can use any java version for development (8, 9, 10, 11).
     * <p>
     * You can use any libraries to simplify any auxiliary logic (e.g. string parsing).
     * But you cannot use any 3rd party libraries to implement validation itself.
     * <p>
     * Testing framework selection is up to you. Testing the logic from "public static void main" is also OK.
     * <p>
     * If you are familiar with Git, do your work in a branch and create pull request.
     *
     * @param ipAddress String representation of IP address that needs to be validated.
     * Function must verify that IP address definition itself is valid.
     * If IP address format is invalid, function must throw IllegalArgumentException.
     * E.g. 192.168.0.1 is correct definition of IP address.
     * 256.0.0.1, 192,168.o.1, 192,168.0.1 are examples of invalid IP addresses.
     * @param cidrRange Classless Inter-Domain Routing (CIDR) definition that defines range of allowed IP addresses.
     * For example 11.1.0.0/24 CIDR defines IP addresses range from 11.1.0.0 to 11.1.0.255
     * Function must verify that cidrRange definition itself is valid.
     * If cidrRange format is invalid, function must throw IllegalArgumentException.
     * E.g. 192.168.0.0/24, 100.0.0.0/16, 10.10.0.0/32, 10.10.0.1/16 are valid definitions.
     * 192.168.0.0/35, 300.0.0.0/16, 10.10,0.0/32, 10.10.0.256/16 are invalid definitions
     * If slash is omitted in cidrRange definition, you can assume that / 32 is used.
     * E.g. cidrRange 10.10.0.1 can be treated as 10.10.0.1/32
     * @return true if provided IP address is covered by the CIDR range; false otherwise.
     * @throws IllegalArgumentException if either ipAddress or cidrRange definitions is invalid.
     */
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private static final Pattern IP_V4_PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])(/([0-2]?\\d?|3[0-2]))?$");

    public static boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public static boolean validateCIDR(final String cidr) {
        return IP_V4_PATTERN.matcher(cidr).matches();
    }

    public static boolean validate(String ipAddress, String cidrRange) {
        if (validateIP(ipAddress) && (validateCIDR(cidrRange)))
            return true;
        return false;
    }

    public static String[] getSplitString(String toBeSplit, String delimiter) {
        return toBeSplit.split(delimiter);
    }

    public static int[] getIntergerArray(String[] array) {
        int[] integers = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            integers[i] = Integer.parseInt(array[i]);
        }
        return integers;
    }

    public static int[] copyArray(int[] ip) {
        int[] array = new int[ip.length];
        for (int i = 0; i < ip.length; i++) {
            array[i] = ip[i];
        }
        return array;
    }

    public static int getPow(int power) {
        return (int) Math.pow(2, power);
    }

    public static int getIPAddress(int partialIP, int value) {
        return partialIP / value;
    }

    private static boolean compare(int[] firstAddress, int[] lastAddress, int[] ipAddress, int slab) {
        int counter = slab;
        int i = 3;
        boolean flag = false;
        while (counter != 0) {
            flag = ((firstAddress[i] <= ipAddress[i]) && lastAddress[i] >= ipAddress[i]) ? true : false;
            if (!flag)
                return flag;
            i--;
            counter--;
        }
        for (int j = 0; j < 4 - slab; j++) {
            flag = (firstAddress[j] == ipAddress[j]) ? true : false;
            if (!flag)
                return flag;
        }
        return flag;
    }

    public static boolean getAddress(int slab, int power, int[] cidrAddress, int[] ipAddress) {
        int[] firstAddress = copyArray(cidrAddress);
        int[] lastAddress = copyArray(cidrAddress);
        int z = getIPAddress(cidrAddress[3 - slab], getPow(power));

        firstAddress[4 - slab] = z * getPow(power);
        lastAddress[4 - slab] = ((z + 1) * getPow(power)) - 1;

        int counter = slab;
        int i = 3;
        while (counter != 0) {
            firstAddress[i] = 0;
            lastAddress[i] = 255;
            i--;
            counter--;
        }
        boolean flag = compare(firstAddress, lastAddress, ipAddress, slab);
        return flag;
    }


    public static boolean getAddressRange(String ipAddress, String[] cidr) {

        String[] cidrStringArray = getSplitString(cidr[0], "\\.");
        int[] cidrIntArray = getIntergerArray(cidrStringArray);

        String[] ipAddressStringArray = getSplitString(ipAddress, "\\.");
        int[] ipAddressIntArray = getIntergerArray(ipAddressStringArray);

        int format;
        if (cidr.length < 2)
            format = 32;
        else
            format = Integer.parseInt(cidr[cidr.length - 1]);


        int address = 32 - format;
        int slab = address / 8;
        int power = address % 8;
        if (power == 0)
            power = 8;

        boolean addressRange = getAddress(slab, power, cidrIntArray, ipAddressIntArray);

        return addressRange;
    }

    public static boolean validateIpAddress(String ipAddress, String cidrRange) throws IllegalArgumentException {

        boolean flag = validate(ipAddress, cidrRange);
        if (!flag) {
            throw new IllegalArgumentException("Invalid IP or CIDR");
        }
        String[] cidr = getSplitString(cidrRange, "/");

        if (getAddressRange(ipAddress, cidr))
            return true;
        else
            throw new IllegalArgumentException("Given IP does not fall in this CIDR!");
    }


    public static void main(String[] args) {
        try {
            System.out.println(validateIpAddress("10.10.7.0", "10.10.0.245/15"));
            System.out.println(validateIpAddress("10.7.0", "10.10.0.245"));
            System.out.println(validateIpAddress("12.168.0.255", "192.168.0.0/2"));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e);
        }
    }
}
