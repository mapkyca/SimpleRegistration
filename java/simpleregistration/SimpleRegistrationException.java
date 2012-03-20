/**
 * @file
 * Simple registration exception.
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
public class SimpleRegistrationException extends Exception {
    SimpleRegistrationException(String string) {
	super(string);
    }
    
}
