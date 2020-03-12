package com.starbux.web.service;

import java.util.Optional;

public interface IGenerateReportService {
	Optional<byte[]> reportOrderPerCustomer();

//	Optional<byte[]> reportMostUserTopping();
}
