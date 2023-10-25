package com.idr.pdd.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idr.pdd.domain.MachineResult;
import com.idr.pdd.domain.ProcessMaster;
import com.idr.pdd.mapper.primary.ProcessMasterMapper;
import com.idr.pdd.mapper.primary.ProductionPlanMapper;
import com.idr.pdd.mapper.secondary.MachineResultMapper;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import com.idr.pdd.domain.ComplianceParam;
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

	public List<Map<String, String>> DailyProduction() {
		// TODO Auto-generated method stub
		return machineResultMapper.DailyProduction();
	}

	public List<ComplianceParam> ComplianceRate() {
		List<ComplianceParam> plantable=productionMapper.getPlanTable();//시작시간
		List<ComplianceParam> resulttable=machineResultMapper.getResultTable();//납기
		for(int i=0;i<plantable.size();i++) {
			plantable.get(i).setDue_time(resulttable.get(i).getDue_time());
		}
		List<ComplianceParam> finalResult=machineResultMapper.ComplianceRate(plantable);//종료시간
		for(int i=0;i<finalResult.size();i++) {
			finalResult.get(i).setStart_time(plantable.get(i).getTrans_starttime());
			finalResult.get(i).setDue_time(resulttable.get(i).getTrans_duetime());
			finalResult.get(i).setTaken_time(DateDifference(plantable.get(i).getTrans_starttime(),finalResult.get(i).getEnd_time()));
		}
		return finalResult;
	}
	
	public String DateDifference(String a, String b) {
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    try {
	        // 문자열을 Date 객체로 변환
	        Date date1 = format.parse(a);
	        Date date2 = format.parse(b);

	        // 두 날짜 간의 시간 차이 계산 (밀리초로)
	        long timeDifference = date2.getTime() - date1.getTime();

	        // 시간 차이를 일, 시, 분, 초로 변환
	        long seconds = timeDifference / 1000 % 60;
	        long minutes = (timeDifference / (1000 * 60)) % 60;
	        long hours = (timeDifference / (1000 * 60 * 60)) % 24;
	        long days = timeDifference / (1000 * 60 * 60 * 24);

	        // 결과 문자열로 저장
	        String timeDifferenceStr = String.format("%02dd %02d:%02d:%02d", days, hours, minutes, seconds);

	        return timeDifferenceStr;
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return "";
	    }
	}


}
