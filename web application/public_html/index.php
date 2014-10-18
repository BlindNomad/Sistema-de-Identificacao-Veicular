<?php

	ini_set("display_errors", 1);

	include __DIR__ . '/../structure/lib/autoload.php';
	include __DIR__ . '/../structure/vendors/Hypersistence/Hypersistence.php';
	session_start();
	Hypersistence::registerAutoloader();

	HyperMVC::addRoute('?:controller/?:action', array());

	HyperMVC::render();
?>