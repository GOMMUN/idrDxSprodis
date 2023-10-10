package com.idr.pdd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.domain.MachineResult;
import com.idr.pdd.domain.ProcessMaster;
import com.idr.pdd.mapper.primary.ProcessMasterMapper;
import com.idr.pdd.mapper.secondary.MachineResultMapper;

@Service
public class SecondaryService {

	@Autowired
	private MachineResultMapper machineResultMapper;
	
	public List<MachineResult> machineResultFindAll(){
		return machineResultMapper.findAll();
	}
}
