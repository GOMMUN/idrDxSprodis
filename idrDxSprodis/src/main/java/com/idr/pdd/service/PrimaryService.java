package com.idr.pdd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.domain.ProcessMaster;
import com.idr.pdd.domain.ProductionPlan;
import com.idr.pdd.mapper.primary.ProcessMasterMapper;
import com.idr.pdd.mapper.primary.ProductionPlanMapper;

@Service
public class PrimaryService {

	@Autowired
	private ProcessMasterMapper processMasterMapper;
	
	@Autowired
	private ProductionPlanMapper productionPlanMapper;
	
	public List<ProcessMaster> processMasterFindAll(){
		return processMasterMapper.findAll();
	}
	
	public List<ProductionPlan> productionPlanFindAll(){
		return productionPlanMapper.findAll();
	}
}
