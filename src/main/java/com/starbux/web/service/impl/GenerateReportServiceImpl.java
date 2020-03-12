package com.starbux.web.service.impl;

import com.starbux.entity.MostUserProductToppingDto;
import com.starbux.entity.OrderCart;
import com.starbux.entity.OrderPerCustomerReportDto;
import com.starbux.entity.User;
import com.starbux.exception.ReportGenerationException;
import com.starbux.web.mapper.IUserMapper;
import com.starbux.web.repository.IOrderRepository;
import com.starbux.web.repository.IUserRepository;
import com.starbux.web.service.IGenerateReportService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GenerateReportServiceImpl implements IGenerateReportService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IUserMapper userMapper;

	@Override
	public Optional<byte[]> reportOrderPerCustomer() {
		List<OrderPerCustomerReportDto> perCustomerReportDtoList = getCartOrderForReporting();

		return generate(perCustomerReportDtoList);
	}

	private Optional<byte[]> generate(List<OrderPerCustomerReportDto> data) {
		final InputStream stream = this.getClass().getResourceAsStream("/report.jrxml");
		try {
			final JasperReport report = JasperCompileManager.compileReport(stream);
			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
			final Map<String, Object> parameters = new HashMap<>();
			parameters.put("createdBy", "starbux.com");
			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
			return Optional.ofNullable(JasperExportManager.exportReportToPdf(print));
		} catch (JRException e) {
			throw new ReportGenerationException("Exception while generating report", e);
		} finally {
			try {
				stream.close();
			} catch (IOException exception) {
				throw new ReportGenerationException("Exception while generating report", exception);
			}
			Optional.empty();
		}
	}

//	@Override
//	public Optional<byte[]> reportMostUserTopping() {
//
//		List<MostUserProductToppingDto> data = getMostUserProductTopping();
//
//
//		final InputStream stream = this.getClass().getResourceAsStream("/report.jrxml");
//		try {
//			final JasperReport report = JasperCompileManager.compileReport(stream);
//			final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(data);
//			final Map<String, Object> parameters = new HashMap<>();
//			parameters.put("createdBy", "starbux.com");
//			final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
//			return Optional.ofNullable(JasperExportManager.exportReportToPdf(print));
//		} catch (JRException e) {
//			throw new ReportGenerationException("Exception while generating report", e);
//		} finally {
//			try {
//				stream.close();
//			} catch (IOException exception) {
//				throw new ReportGenerationException("Exception while generating report", exception);
//			}
//			Optional.empty();
//		}
//	}
//
//	private List<MostUserProductToppingDto> getMostUserProductTopping() {
//		List<OrderCart> orderCarts = orderRepository.findAll();
//
//		return null;
//	}

	private List<OrderPerCustomerReportDto> getCartOrderForReporting() {
		List<User> users = userRepository.findAll();
		if (CollectionUtils.isNotEmpty(users)) {
			List<OrderPerCustomerReportDto> perCustomerReportDtoList = new ArrayList<>();
			AtomicInteger atomicInteger = new AtomicInteger(1);
			for (User user : users) {
				OrderPerCustomerReportDto orderPerCustomerReportDto = new OrderPerCustomerReportDto();
				orderPerCustomerReportDto.setAmount(user.getOrders()
						.stream()
						.map(x -> x.getAmount().doubleValue())
						.reduce(0.0, Double::sum));
				orderPerCustomerReportDto.setEmail(user.getEmail());
				orderPerCustomerReportDto.setName(user.getFirstName(), user.getLastName());
				orderPerCustomerReportDto.setId(atomicInteger.getAndAdd(1));
				perCustomerReportDtoList.add(orderPerCustomerReportDto);
			}
			return perCustomerReportDtoList;
		}
		return Collections.EMPTY_LIST;
	}
}
