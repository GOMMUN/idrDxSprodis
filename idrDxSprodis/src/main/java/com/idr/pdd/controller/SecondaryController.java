package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idr.pdd.common.Message;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.domain.MachineResult;
import com.idr.pdd.domain.ComplianceParam;
import com.idr.pdd.domain.LeadTimeResult;
import com.idr.pdd.service.PrimaryService;
import com.idr.pdd.service.SecondaryService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/secondary")
public class SecondaryController {

	@Autowired
	private SecondaryService secondaryService;

	@GetMapping("/")
//	@Operation(summary = "등록array", description = "array비가동내역을 신규 등록합니다.")
	public ResponseEntity<Message> findAll() {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		try {

			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(secondaryService.machineResultFindAll());

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {

			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/EquipResult")
//	@Operation(summary = "등록array", description = "array비가동내역을 신규 등록합니다.")
	public ResponseEntity<Message> machineResult() {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		List<MachineResult> result = new ArrayList<>();
		try {
			result = secondaryService.EquipResult();

			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(result);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/LeadTime")
	public ResponseEntity<Message> LeadTimeResult() {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		LeadTimeResult result=new LeadTimeResult();
		try {
			result = secondaryService.LeadTimeResult();

			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(result);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/DailyProduction")
	public ResponseEntity<Message> DailyProduction() {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		
		try {
			List<Map<String,String>>result = secondaryService.DailyProduction();

			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/ComplianceRate")
	public ResponseEntity<Message> ComplianceRate() {

		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();
		
		try {
			List<ComplianceParam>result = secondaryService.ComplianceRate();

			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(result);

			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}


}
