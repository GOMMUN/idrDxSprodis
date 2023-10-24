package com.idr.pdd.mapper.secondary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.idr.pdd.domain.MachineResult;
import com.idr.pdd.domain.ProcessMaster;
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


}
