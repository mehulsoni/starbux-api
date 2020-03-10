package com.starbux.web.controller.impl;

import com.starbux.web.controller.IGenerateReportController;
import com.starbux.web.service.IGenerateReportService;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/report")
@Tag(description = "Report Generation Operation", name = "Reporting")
public class GenerateReportControllerImpl implements IGenerateReportController {

	@Autowired
	private IGenerateReportService generateReportService;

	@GetMapping("/order-per-customer")
	public ResponseEntity<byte[]> reportOrderPerCustomer()  {
		return generateReportService.reportOrderPerCustomer()
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No Data"));
	}
}