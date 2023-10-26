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
	
	@Select(" SELECT "
			+ "        AVG(Finish_Proc_time-Start_Proc_time) AS proctime, "
			+ "        AVG(Leadtime_sec - Wait_Rest_time_sec) AS AVGleadtime, "
			+ "        MAX(Leadtime_sec - Wait_Rest_time_sec) AS MAXleadtime, "
			+ "        MIN(Leadtime_sec - Wait_Rest_time_sec) AS MINleadtime, "
			+ "        AVG(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec) AS AVGlosstime, "
			+ "        MAX(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec) AS MAXlosstime, "
			+ "        MIN(Moving_time_sec + Waiting_time_sec - Wait_Rest_time_sec) AS MINlosstime "
			+ "    FROM Loss_Time "
			+ "    WHERE 1=1 AND Process_id <> 0")
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


	@Select("SELECT "
			+ "    machine_id , "
			+ "    machine_name , "
			+ "    SUM(finish_time - start_time) AS uptime, "
			+ "    MIN(start_time) as MinStartTime, "
			+ "    MAX(finish_time) as MaxFinishTime "
			+ " FROM machine_result "
			+ " GROUP BY machine_id "
			+ " ORDER BY machine_id")
	List<MachineResult> EquipResult();


}
