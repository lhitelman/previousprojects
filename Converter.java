package project5;
/**
 * This class offers several conversion methods that can convert binary numbers to either a decimal
 * or hexidecimal representation or decimal numbers to hexidecimal representation or hexidecimal
 * numbers to binary representation. The user interacts with this program by passing the chosen method
 * an appropriate String or int representation to convert. The program responds by returning the
 * converted number in either a String or int or by returning null if the method was passed an
 * invalid argument. 
 * 
 * @author Lily Hitelman
 * @version 11/17/18
 * 
 */
public class Converter {
	
	/**
	 * This public wrapper method converts a string representation of a binary number 
	 * to a decimal int by validating the argument and passing it to an internal recursive
	 * method that calculates the conversion.
	 * 
	 * @param binary -- a string representation of a binary number, starting with "0b".
	 * @return decimal int representation of the binary number.
	 * @throws IllegalArgumentException if the binary string passed is invalid, meaning
	 * it contains more than 31 binary digits, the first two characters are not 0b, or the 
	 * binary number contains something other than 1s and 0s.
	 */
	public static int binaryToDecimal(String binary) throws IllegalArgumentException{
		if (binary == null)
			throw new IllegalArgumentException("Illegal Binary String: Entry is null."); 
		if (binary.length() > 33 || binary.length() < 3)
			throw new IllegalArgumentException("Illegal Binary String: Length is larger than 31"); 
		if (binary.charAt(0) != '0' || binary.charAt(1) != 'b')
			throw new IllegalArgumentException("Illegal Binary String: First two characters are not 0b"); 
		for (int i = 2; i < binary.length(); i++) {
			if (binary.charAt(i) != '0' && binary.charAt(i) != '1')
				throw new IllegalArgumentException("Illegal Binary String: Contains non-binary characters");
		}
		
		
		return binToDecInternal (binary.substring(2), 0);
		
	}
	
	/**
	 * This private internal method accepts a String with a binary number
	 * @param binary -- the binary string entered by the user to convert, starting with "0b".
	 * @param n -- the position (from right to left) that a digit will occupy, starting
	 * at 0 and increasing by one every digit. Also the power of two that the digit will be 
	 * raised to.
	 * @return decimal representation of the number in int form.
	 */
	private static int binToDecInternal (String binary, int n) {
		if (binary.length() == 0)
			return 0;
		//isolate right most char
		char curchar = binary.charAt(binary.length()-1);
		int curint = curchar - '0';
		
		//pass int to power n of 2
		int total = curint * power(2, n);
		
		//return that total
		return total + binToDecInternal(binary.substring(0, binary.length()-1), n+1);
		
	}
	
	/**
	 * This private internal method raises a given number (base) to a given power (n).
	 * @param base - the number to be raised to a certain power.
	 * @param n - the power to which the number should be raised.
	 * @return integer totaling the base raised to the n-th power.
	 */
	private static int power(int base, int n) {
		if (n <= 0) {
			return 1;
		}
		if (n%2 == 0) {
			int temp = power(base, n/2);
			return temp * temp;
		}
		else {
			int temp = power(base, n/2);
			return temp * temp * base;
		}

	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * This public method converts a String representation of a binary number 
	 * to a String representation of a hexadecimal number. It first checks to 
	 * make sure the argument is valid, then passes the argument to an internal
	 * recursive function.
	 * @param binary -- a string representation of a binary number, starting with "0b".
	 * @return String hexidecimal representation of the binary number starting with "0x"
	 * or null if the argument is not valid.
	 */
	public static String binaryToHex (String binary) {
		if (binary == null)
			return null;
		if (binary.charAt(0) != '0' || binary.charAt(1) != 'b')
			return null;
		if (binary.length() > 33 || binary.length() < 3)
			return null;
		for (int i = 2; i < binary.length(); i++) {
			if (binary.charAt(i) != '0' && binary.charAt(i) != '1')
				return null;
		}
		
		return "0x" + binToHexInternal(binary.substring(2));

	}
	
	/**
	 * This private internal method converts a given binary string to a hexidecimal representation. The method 
	 * takes four binary digits at a time, and converts the four digits into the correct hexidecimal letter. 
	 * The method does this for the whole binary string and returns the string of hexidecimal letter(s).
	 * @param binary - the validated binary string given by the user, starting with "0b".
	 * @return String and containing the hexidecimal representation of the number.
	 */
	private static String binToHexInternal (String binary) {
		//check how many digits, make sure %4 = 0
		while (binary.length() % 4 != 0) {
			return binToHexInternal("0" + binary);
		}
		String subBinary = binary.substring (binary.length()- 4);
		
		int decValue = 0;
		String strValue = "";
		
		for (int i = 0; i < 4; i++) {
			decValue += (subBinary.charAt(subBinary.length()-1-i) - '0') * power(2,i);
		}
		if (decValue >= 0 && decValue <10)
			strValue = decValue + "";
		if (decValue == 10)
			strValue = "A";
		if (decValue == 11)
			strValue = "B";
		if (decValue == 12)
			strValue = "C";
		if (decValue == 13)
			strValue = "D";
		if (decValue == 14)
			strValue = "E";
		if (decValue == 15)
			strValue = "F";
		if (binary.length() - 4 > 0)
			return  binToHexInternal(binary.substring(0, binary.length()- 4)) + strValue;
		else 
			return strValue;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * This public method accepts an int from the user and converts it to binary representation.
	 * First, the method validates the argument, then it passes the argument to an internal 
	 * recursive method.
	 * @param decimal - an int passed to the method by the user.
	 * @return String containing the binary representation of the decimal, starting with "0b" or null
	 * if the argument is not valid.
	 */
	public static String decimalToBinary (int decimal)  {
		if (decimal < 0)
			return null;
		else
			return "0b" + (decToBinInternal(decimal)+ "");
	}
	/**
	 * This private internal method accepts an int passed by the wrapper function decimalToBinary.
	 * First the method performs modulo and integer division operations to represent the
	 * decimal as a binary number.
	 * @param decimal - int argument entered by the user and passed through the public method.
	 * @return String of the binary representation of the number.
	 */
	private static String decToBinInternal(int decimal) {
		if (decimal == 0)
			return "";

		int remainder = decimal % 2;
		int divisor = decimal/2;
		if (remainder == 0)
			return decToBinInternal(divisor) + "0";
		return decToBinInternal(divisor) + (remainder + "");
	}
	
	
	
	
	
	
	
	/**
	 * This public method accepts a String from the user and error checks to make sure 
	 * it is a valid hexidecimal value before passing it to the internal function.
	 * @param hex - a String containing a hexidecimal number starting with "0x".
	 * @return String containing a binary representation of the number starting with "0b" or null
	 * if the argument is not valid.
	 */
	public static String hexToBinary (String hex) {
		if (hex == null) 
			return null;
		
		if (hex.charAt(0) != '0' || hex.charAt(1) != 'x')
			return null;
		
		for (int i = 2; i < hex.length(); i++) {
			if (hex.charAt(i) >= '0' && hex.charAt(i) <= '9')
				continue;
			else if (hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F') 
				continue;
			else
				return null;
		}
		
		
		return "0b" + hexToBinInternal(hex.substring(2), "0");
		
	}
	
	/**
	 * This internal method converts one character at a time to a binary
	 * representation of the digit, then concatenates the binary representations 
	 * together.
	 * @param hex - valid hex String entered by the user and passed by the wrapper function.
	 * @param binary - String representing the converted digits in binary form, must be 4 digits.
	 * @return String containing the binary representation of the hexidecimal number. 
	 */
	private static String hexToBinInternal (String hex, String binary){
		//convert right most hex symbol to binary
		char c = hex.charAt(hex.length()-1);
		String binString = "0";
		if (c >= '0' && c <= '9')
			binString = decToBinInternal(c - '0');
		if (c == 'A')
			binString = decToBinInternal(10);
		if (c == 'B')
			binString = decToBinInternal(11);
		if (c == 'C')
			binString = decToBinInternal(12);
		if (c == 'D')
			binString = decToBinInternal(13);
		if (c == 'E')
			binString = decToBinInternal(14);
		if (c == 'F')
			binString = decToBinInternal(15);
		
		while (binString.length() % 4 != 0)
			binString = "0" + binString;
		
		//if hex.length() == 1 return addBinary;
		if (hex.length() - 1 == 0)
			return binString;
		//else hexToBinInternal with hex substring and result of addBinary
		else
			return hexToBinInternal(hex.substring(0, hex.length()-1), binString) + binString;
		
	}
	
	
}
