/**
 * @file
 * Really simple product registration.
 * 
 * Simple registration tool, validates a username and entry code for a user,
 * returns whether it is valid.
 * 
 * Keeping honest people honest :)
 * 
 * @package simpleregistration
 * @copyright Marcus Povey Consulting, All rights reserved.
 * @license The MIT License (see LICENCE.txt), other licenses available.
 * @author Marcus Povey <marcus@marcus-povey.co.uk>
 * @link http://www.marcus-povey.co.uk
 */
package simpleregistration;


/**
 * @author Marcus Povey <marcus@marcus-povey.co.uk>
 */
public class SimpleRegistration {
    
    private static String HASHTABLE = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
   
    /**
     * The salt to use as a mixer.
     */
    private static String salt;
    
    /**
     * Generate some salt.
     * Min 8 chars long.
     */
    public static void setSalt(String newsalt) throws SimpleRegistrationException {
	if (newsalt.length() < 8) throw new SimpleRegistrationException("Salt too short");
	salt = newsalt;
    }
    
    /**
     * Return the salt
     * @return String
     */
    public static String getSalt() {
	return SimpleRegistration.salt;
    }
    
    /**
     * My own implementation of hashcode to remain compatibility across versions.
     * @param input
     * @return 
     */
    private static int hashcode(String input) {
	int h = 0;
	int len = input.length();
	for (int i = 0; i < len; i++) {
	    //h = 31 * h + input.charAt(i);
	    h = h + input.charAt(i);
	    System.err.println(h);
	}
	return h;
    }
    
    /**
     * Generate a new code.
     * @param name
     * @return 
     */
    public static String generate(String name) throws SimpleRegistrationException
    {
	if (SimpleRegistration.salt == null)
	    throw new SimpleRegistrationException("Salt not set");
	
	StringBuffer out = new StringBuffer();
	
	name = name.trim();
	name = name.toUpperCase();
	
	String hexString = Integer.toHexString(
		(hashcode(name) << 8) + hashcode(salt)
	);
	char last = '0';
	char current = '0';
	
	for (int n = 0; n < hexString.length(); n++)
	{
	    current = hexString.charAt(n);
	    if (n>0) last = hexString.charAt(n - 1);
	    
	    int charat = Integer.parseInt(Character.toString(current), 16) + Integer.parseInt(Character.toString(last), 16);
	    out.append(HASHTABLE.charAt(charat));
	}
	
	return out.toString();
    }
    
    /**
     * Validate a code.
     * @param name
     * @param codetovalidate
     * @return boolean
     */
    public static boolean validate(String name, String codetovalidate) throws SimpleRegistrationException
    {
	if (name == null) return false;
	if (codetovalidate == null) return false;
	
	name = name.trim();
	codetovalidate = codetovalidate.trim();
	
	if (name.length() == 0) return false;
	if (codetovalidate.length() == 0) return false;
	
	if (codetovalidate.compareTo(generate(name)) == 0)
	    return true;
	
	return false;
    }
    
}
