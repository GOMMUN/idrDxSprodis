package com.idr.pdd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.domain.MachineResult;
import com.idr.pdd.domain.ProcessMaster;
import com.idr.pdd.mapper.primary.ProcessMasterMapper;
import com.idr.pdd.mapper.primary.ProductionPlanMapper;
import com.idr.pdd.mapper.secondary.MachineResultMapper;
import com.idr.pdd.domain.LeadTimeResult;

@Service
public class SecondaryService {

	@Autowired
	private MachineResultMapper machineResultMapper;
	@Autowired
	private ProductionPlanMapper productionMapper;

	public List<MachineResult> machineResultFindAll() {
		return machineResultMapper.findAll();
	}

	public List<MachineResult> EquipResult() {
		List<MachineResult> result = new ArrayList<>();
		List<Map<String, String>> Availabletime = productionMapper.getAvailabletime();
		List<Map<String, String>> uptime = machineResultMapper.getUptime();

		for (Map<String, String> availableTimeItem : Availabletime) {
			String orderId = String.valueOf(availableTimeItem.get("order_id"));
			// order_id가 동일한 uptime 아이템 찾기
			for (Map<String, String> uptimeItem : uptime) {
				String uptimeOrderId = String.valueOf(uptimeItem.get("order_id"));

				if (orderId.equals(uptimeOrderId)) {
					String uptimeStr = String.valueOf(uptimeItem.get("uptime"));
					String availableTimeStr = String.valueOf(availableTimeItem.get("available_time"));
					float uptimeValue = Float.parseFloat(uptimeStr);
					float availableTimeValue = Float.parseFloat(availableTimeStr);

					MachineResult mr = new MachineResult();
					mr.setMachineName(uptimeItem.get("machine_name"));
					mr.setMachineId(String.valueOf(uptimeItem.get("machine_id")));
					float equipPerformance = Math.round((uptimeValue / availableTimeValue) * 1000.0f) / 1000.0f;
					mr.setEquipPerformace(equipPerformance);

					result.add(mr);

				}
			}
		}
		return result;
	}

	public LeadTimeResult LeadTimeResult() {
		// TODO Auto-generated method stub
		LeadTimeResult result = machineResultMapper.LeadTimeResult();

		if (!result.getProcTimeD().equals("0")) {
			result.setProcTimeH(result.getProcTimeD() + "d " + result.getProcTimeH());
		}
		if (!result.getAvgLeadTimeD().equals("0")) {
			result.setAvgLeadTimeH(result.getAvgLeadTimeD()+"d "+result.getAvgLeadTimeH());
		}
		if (!result.getMaxLeadTimeD().equals("0")) {
			result.setMaxLeadTimeH(result.getMaxLeadTimeD()+"d "+result.getMaxLeadTimeH());
		}
		if (!result.getMinLeadTimeD().equals("0")) {
			result.setMinLeadTimeH(result.getMinLeadTimeD()+"d "+result.getMinLeadTimeH());
		}
		if (!result.getAvgLossTimeD().equals("0")) {
			result.setAvgLossTimeH(result.getAvgLossTimeD()+"d "+result.getAvgLossTimeH());
		}
		if (!result.getMaxLossTimeD().equals("0")) {
			result.setMaxLossTimeH(result.getMaxLossTimeD()+"d "+result.getMaxLossTimeH());
		}
		if (!result.getMinLossTimeD().equals("0")) {
			result.setMinLossTimeH(result.getMinLossTimeD()+"d "+result.getMinLossTimeH());
		}

		return result;
	}

}
