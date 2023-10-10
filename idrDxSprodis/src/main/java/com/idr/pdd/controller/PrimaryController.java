package com.idr.pdd.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idr.pdd.common.Message;
import com.idr.pdd.common.StatusEnum;
import com.idr.pdd.domain.ProductionPlan;
import com.idr.pdd.service.PrimaryService;

@RestController
@RequestMapping("/primary")
public class PrimaryController {

	@Autowired
	private PrimaryService service;
	
	@GetMapping("/")
//	@Operation(summary = "등록array", description = "array비가동내역을 신규 등록합니다.")
	public ResponseEntity<Message> findAll() {
		
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		try {
			
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(service.processMasterFindAll());
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}catch (Exception e) {

				
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/productionPlan/findAll")
	public ResponseEntity<Message> ProductionPlanFindAll() {
		
		Message message = new Message();
		HttpHeaders headers = new HttpHeaders();

		try {
			
			List<ProductionPlan> list = service.productionPlanFindAll();
			
			headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			message.setStatus(StatusEnum.OK.getCode());
			message.setMessage(StatusEnum.OK.getName());
			message.setData(list);
			
			return new ResponseEntity<>(message, headers, HttpStatus.OK);
		}catch (Exception e) {

				
			message.setStatus(StatusEnum.BAD_REQUEST.getCode());
			message.setMessage(e.getMessage());
			message.setData(null);

			return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
		}
	}
}
