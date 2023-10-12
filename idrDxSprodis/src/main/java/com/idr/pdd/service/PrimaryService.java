package com.idr.pdd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.domain.Insertparam;
import com.idr.pdd.domain.ProcessMaster;
import com.idr.pdd.domain.ProductionPlan;
import com.idr.pdd.domain.ProductionPlanSub;
import com.idr.pdd.mapper.primary.ProcessMasterMapper;
import com.idr.pdd.mapper.primary.ProductionPlanMapper;

@Service
public class PrimaryService {

	@Autowired
	private ProcessMasterMapper processMasterMapper;

	@Autowired
	private ProductionPlanMapper productionPlanMapper;

	public List<ProcessMaster> processMasterFindAll() {
		return processMasterMapper.findAll();
	}

	public List<ProductionPlan> productionPlanFindAll() {
		return productionPlanMapper.findAll();
	}

	public List<Map<String, String>> getItem() {
		// TODO Auto-generated method stub
		return productionPlanMapper.getItem();
	}

	public List<Map<String, String>> getLot(String get_id) {
		// TODO Auto-generated method stub
		return productionPlanMapper.getLot(get_id);
	}

	public void insertPlan(Insertparam param) {

		ProductionPlanSub p = productionPlanMapper.selectplan(param.getItem_id(), param.getLot_id());

		productionPlanMapper.insertplan(p.getLot_id(), p.getLot_name(), p.getLot_qty(), p.getLot_size(),
				p.getLot_unit(), p.getProc_id(), p.getProc_name(), param.getStart_time(), param.getEnd_time(),
				param.getItem_id(), param.getOrder_name(), param.getLot_work());
	}

	public void remove(Insertparam param) {
		productionPlanMapper.delete(param.getOrder_id());

	}

	public void update(Insertparam param) {
		
		ProductionPlanSub p = productionPlanMapper.selectplan(param.getItem_id(), param.getLot_id());
		
		productionPlanMapper.update(p.getLot_id(), p.getLot_name(), p.getLot_qty(), p.getLot_size(),
				p.getLot_unit(), p.getProc_id(), p.getProc_name(), param.getStart_time(), param.getEnd_time(),
				param.getItem_id(), param.getOrder_name(), param.getLot_work(),param.getOrder_id());
	}
}
