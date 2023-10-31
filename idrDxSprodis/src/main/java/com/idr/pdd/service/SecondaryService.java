package com.idr.pdd.service;

import java.text.DecimalFormat;
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
		List<MachineResult> result = machineResultMapper.EquipResul();
		
		for(int i=0;i<result.size();i++) {
			int uptime = result.get(i).getUptime();
			int maxFinishTime = result.get(i).getMaxFinishTime();
			int minStartTime = result.get(i).getMinStartTime();
			// 부동 소수점 형태로 계산
			double ratio = ((double) uptime / (maxFinishTime - minStartTime))*100;
			String formattedRatio = String.format("%.2f", ratio);
			result.get(i).setEquipPerformace(formattedRatio);
		}
		return result;
	}

	public LeadTimeResult LeadTimeResult() {
		// TODO Auto-generated method stub
		LeadTimeResult result = machineResultMapper.LeadTimeResult();
		result.setProctime(convertSecondsToDDHHMMSS(Double.parseDouble(result.getProctime())));
		result.setAVGleadtime(convertSecondsToDDHHMMSS(Double.parseDouble(result.getAVGleadtime())));
		result.setMAXleadtime(convertSecondsToDDHHMMSS(Double.parseDouble(result.getMAXleadtime())));
		result.setMINleadtime(convertSecondsToDDHHMMSS(Double.parseDouble(result.getMINleadtime())));
		result.setAVGlosstime(convertSecondsToDDHHMMSS(Double.parseDouble(result.getAVGlosstime())));
		result.setMAXlosstime(convertSecondsToDDHHMMSS(Double.parseDouble(result.getMAXlosstime())));
		result.setMINlosstime(convertSecondsToDDHHMMSS(Double.parseDouble(result.getMINlosstime())));
		return result;
	}
	
	public static String convertSecondsToDDHHMMSS(double seconds) {
	    int days = (int) (seconds / 86400);
	    int hours = (int) ((seconds % 86400) / 3600);
	    int minutes = (int) ((seconds % 3600) / 60);
	    int remainingSeconds = (int) (seconds % 60);

	    // 각 부분을 2자리로 맞추고, 필요에 따라 0을 추가합니다.
	    String daysStr = (days == 0) ? "" : String.format("%02d", days);
	    String hoursStr = String.format("%02d", hours);
	    String minutesStr = String.format("%02d", minutes);
	    String secondsStr = String.format("%02d", remainingSeconds);
	    
	    String formattedTime = (days == 0) ? hoursStr + ":" + minutesStr + ":" + secondsStr : daysStr + "d:" + hoursStr + ":" + minutesStr + ":" + secondsStr;
	    return formattedTime;
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
