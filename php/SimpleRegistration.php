<?php
/**
 * @file
 * Really simple product registration.
 * 
 * Simple registration tool, validates a username and entry code for a user,
 * returns whether it is valid.
 * 
 * Keeping honest people honest :)
 * 
 * @package nppllogbook.logbook
 * @copyright Marcus Povey Consulting, All rights reserved.
 * @license The MIT License (see LICENCE.txt), other licenses available.
 * @author Marcus Povey <marcus@marcus-povey.co.uk>
 * @link http://www.marcus-povey.co.uk
 */
 
 class SimpleRegistration
 {
	 /** Hash table */
	 private static $HASHTABLE = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
	 
	 /** Salt */
	 private static $salt;
	 
	 /**
      * Generate some salt.
      * Min 8 chars long.
      */
	 public static function setSalt($newsalt) {
		 if (strlen($newsalt) < 8) throw new SimpleRegistrationException("Salt too short");
		 self::$salt = $newsalt;
	 }
	 
	 /**
      * Return the salt
      * @return String
      */
	 public static function getSalt() {
		 return self::$salt;
	 }
	 
	 /**
	  * Generate a hashcode (compatible with java String.hashCode())
	  */
	 private static function hashCode($string)
	 {
		$h = 0;
		$len = strlen($string);
	
		for ($i = 0; $i < $len; $i++) {
			
			$h = $h + ord($string[$i]);
		}
		return $h;
	 }
	 
	 /**
      * Generate a new code.
      * @param name
      * @return 
      */
	 public static function generate($name)
	 {
		 if (!self::$salt) 
			throw new SimpleRegistrationException("No salt");
		 
		 $name = trim($name);
		 $name = strtoupper($name);
		 
		 $hexString = base_convert(
			(self::hashCode($name) << 8) + self::hashCode(self::$salt), 10, 16
		 );
		 
		 $last = '0';
		 $current = '0';
		 $out = "";
		 
		 for ($n = 0; $n < strlen($hexString); $n ++)
		 {
			 $current = $hexString[$n];
			 if ($n>0) $last = $hexString[$n-1];
			 
			 $charat = (int)(hexdec($current) + hexdec($last));
			 
			 $out .= self::$HASHTABLE[$charat];
		 }
		 
		 return $out;
	 }
	 
	 /**
      * Validate a code.
      * @param name
      * @param codetovalidate
      * @return boolean
      */
	 public static function validate($name, $codetovalidate)
	 {
		 $name = trim($name);
		 $codetovalidate = trim($codetovalidate);
		 
		 if (!$name) return false;
		 if (!$codetovalidate) return false;
		 
		 if (strcmp($codetovalidate, self::generate($name))==0)
			return true;
			
		 return false;
	 }
 }
 
 class SimpleRegistrationException extends Exception {}
