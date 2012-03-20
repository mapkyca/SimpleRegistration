<?php

require_once("SimpleRegistration.php");

SimpleRegistration::setSalt("testsaltwibble");


echo "Code generated: " . SimpleRegistration::generate("Marcus Povey") ."\n\n";

if (SimpleRegistration::validate("Marcus Povey", "DMMTZ"))
	echo "Code Valid!\n";
else
	echo "Code Invalid\n";
	
