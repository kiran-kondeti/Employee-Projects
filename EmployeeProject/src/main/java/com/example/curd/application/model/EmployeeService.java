package com.example.curd.application.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.curd.application.repository.EmployeeRepository;
import com.example.global.exception.handling.advice.EmptyInputException;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;

	public List<Employee> storeEmployeeDetails(List<Employee> emp) {
		emp.forEach(e -> {
			if (Optional.ofNullable(e) == null) {
				throw new EmptyInputException();
			}
		});
		List<Employee> empObj = empRepo.saveAll(emp);
		return empObj;
	}

	public List<Employee> getAllemps() {
		List<Employee> empList = new ArrayList<Employee>();
		try {
			empList = empRepo.findAll();
			empList.forEach(e -> {
				DateTimeFormatter formateDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(e.getDoj(), formateDate);
				LocalDate endOfFinancialYer = LocalDate.of(date.getYear() + 1, Month.APRIL, 1);
				Double perMonthSalry = e.getSalary() / 31;
				long days = ChronoUnit.DAYS.between(date, endOfFinancialYer);
				Double perYaerSalary = perMonthSalry * days;
				if (perYaerSalary.compareTo(new Double(250000)) <= 0) {
					e.setTaxAmount(new Double(0));
				} else if (perYaerSalary.compareTo(new Double(250000)) > 0
						&& perYaerSalary.compareTo(new Double(500000)) <= 0) {
					Double taxMount = perYaerSalary * 5 / 100;
					e.setTaxAmount(taxMount);
				} else if (perYaerSalary.compareTo(new Double(500000)) > 0
						&& perYaerSalary.compareTo(new Double(1000000)) <= 0) {
					Double actualTaxableAmount = perYaerSalary - new Double(250000);
					Double upto25LakhTaxAmount = new Double(250000) * 5 / 100;
					Double afte25SlabAmount = actualTaxableAmount - new Double(250000);
					Double remainingAmountTax = afte25SlabAmount * 10 / 100;
					e.setTaxAmount(upto25LakhTaxAmount + remainingAmountTax);
				} else if (perYaerSalary.compareTo(new Double(1000000)) > 0) {
					Double actualTaxableAmount = perYaerSalary - new Double(250000);
					Double upto25LakhTaxAmount = new Double(250000) * 5 / 100;
					Double upto50LakhTaxAmount = new Double(500000) * 10 / 100;
					Double more10TenLakhTax = (perYaerSalary - (750000)) * 20 / 100;
					e.setTaxAmount(upto25LakhTaxAmount + upto50LakhTaxAmount + more10TenLakhTax);
					if (perYaerSalary.compareTo(new Double(2500000)) > 0) {
						e.setCessAmount((perYaerSalary - new Double(2500000)) * 2 / 100);
					}
				}
			});
		} catch (Exception e) {
		}
		return empList;

	}
}
