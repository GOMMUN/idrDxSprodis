package com.idr.pdd.mapper.secondary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.idr.pdd.domain.MachineResult;
import com.idr.pdd.domain.ProcessMaster;
import com.idr.pdd.domain.ComplianceParam;
import com.idr.pdd.domain.LeadTimeResult;

@Mapper
public interface MachineResultMapper {

	@Select("SELECT machine_id FROM machine_result")
	List<MachineResult> findAll();

	

	@Select(" SELECT DISTINCT order_id ,machine_id ,machine_name , "
			+ " (finish_time -start_time ) as uptime "
			+ " FROM machine_result mr "
			+ " group by machine_id "
			+ " ORDER BY machine_id ")
	List<Map<String, String>> getUptime();


	@Select("SELECT "
			+ "		strftime('%d', AVG(Finish_Proc_time -Start_Proc_time), 'unixepoch')-1  AS ProcTimeD, "
			+ "		strftime('%H:%M:%S', AVG(Finish_Proc_time -Start_Proc_time), 'unixepoch')  AS ProcTimeH, "
			+ "    strftime('%d', AVG(Leadtime_sec - Waiting_time_sec), 'unixepoch')-1 AS AvgLeadTimeD, "
			+ "    strftime('%H:%M:%S', AVG(Leadtime_sec - Waiting_time_sec), 'unixepoch') AS AvgLeadTimeH, "
			+ "    strftime('%d', MAX(Leadtime_sec - Waiting_time_sec), 'unixepoch')-1 AS MaxLeadTimeD, "
			+ "    strftime('%H:%M:%S', MAX(Leadtime_sec - Waiting_time_sec), 'unixepoch') AS MaxLeadTimeH, "
			+ "    strftime('%d', MIN(Leadtime_sec - Waiting_time_sec), 'unixepoch')-1 AS MinLeadTimeD, "
			+ "    strftime('%H:%M:%S', MIN(Leadtime_sec - Waiting_time_sec), 'unixepoch') AS MinLeadTimeH, "
			+ "    strftime('%d', AVG(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec), 'unixepoch')-1 AS AvgLossTimeD, "
			+ "    strftime('%H:%M:%S', AVG(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec), 'unixepoch') AS AvgLossTimeH, "
			+ "    strftime('%d', MAX(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec), 'unixepoch')-1 AS MaxLossTimeD, "
			+ "    strftime('%H:%M:%S', MAX(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec), 'unixepoch') AS MaxLossTimeH, "
			+ "    strftime('%d', MIN(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec), 'unixepoch')-1 AS MinLossTimeD, "
			+ "    strftime('%H:%M:%S', MIN(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec), 'unixepoch') AS MinLossTimeH "
			+ "	   FROM Loss_Time "
			+ "	   WHERE 1=1 AND Process_id <> 0 ")
	LeadTimeResult LeadTimeResult();


	@Select("SELECT strftime('%m-%d', DATETIME(finish_time, 'unixepoch')) AS date, "
			+ "       COUNT(*) AS count "
			+ " FROM machine_result "
			+ " WHERE process_type = 2 AND divide_type = 0 OR divide_type = 3 "
			+ " GROUP BY date "
			+ " ORDER BY date;")
	List<Map<String, String>> DailyProduction();


	//List<Map<String, String>> ComplianceRate(List<ComplianceParam> endtime);


	@Select("SELECT order_id ,due_time ,datetime(due_time+ 9*3600,'unixepoch')as trans_duetime  from production_result group by order_id order by order_id ASC")
	List<ComplianceParam> getResultTable();



	List<ComplianceParam> ComplianceRate(List<ComplianceParam> plantable);


}
