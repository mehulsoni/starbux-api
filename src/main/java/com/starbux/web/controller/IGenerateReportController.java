package com.starbux.web.controller;

import org.springframework.http.ResponseEntity;

public interface IGenerateReportController {

	ResponseEntity<byte[]> reportOrderPerCustomer();

//	ResponseEntity<byte[]> reportMostUserTopping();
}
