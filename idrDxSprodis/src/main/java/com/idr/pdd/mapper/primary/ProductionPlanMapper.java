package com.idr.pdd.mapper.primary;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.idr.pdd.domain.ProductionPlan;
import com.idr.pdd.domain.ProductionPlanSub;

@Mapper
public interface ProductionPlanMapper {

	@Select(" SELECT "
			+ " no "
			+ " , order_id "
			+ " , order_name "
			+ " , flow_id "
			+ " , flow_name "
			+ " , item_id "
			+ " , item_name "
			+ " , process_id "
			+ " , process_name "
			+ " , lot_id "
			+ " , lot_name "
			+ " , lot_size "
			+ " , lot_unit "
			+ " , lot_qty "
			+ " , lot_work "
			+ " , DATETIME(start_time, 'unixepoch', 'localtime') "
			+ " , DATETIME(end_time, 'unixepoch', 'localtime') "
			+ " , first_lot_qty "
			+ " , status "
			+ " , penalty "
			+ " , group_id "
			+ " , start_type "
			+ " , start_order "
			+ " , start_lot "
			+ " , start_count "
			+ " , estimate_time "
			+ " , color "
			+ " , auto_due "
			+ " FROM production_plan "
			)
	List<ProductionPlan> findAll();
	
	@Select("SELECT DISTINCT item_id, item_name FROM process_master")
	List<Map<String, String>> getItem();

	@Select("SELECT lot_id, lot_name FROM lot_master WHERE item_id = #{item_id} ORDER BY from_proc_id")
	List<Map<String, String>> getLot(String item_id);

	@Select("SELECT lot_id, from_proc_id as proc_id, (SELECT proc_name FROM process_master WHERE proc_id = from_proc_id) as proc_name, lot_name, lot_size, lot_unit, from_lot_qty as lot_qty FROM lot_master WHERE from_proc_id != 0 AND item_id = #{item_id} AND lot_id = #{lot_id}")
	ProductionPlanSub selectplan(@Param("item_id") int item_id, @Param("lot_id") int lot_id);

	@Insert("INSERT INTO \"production_plan\" (" +
	        "    `no`, " +
	        "    `order_id`, " +
	        "    `order_name`, " +
	        "    `flow_id`, " +
	        "    `flow_name`, " +
	        "    `item_id`, " +
	        "    `item_name`, " +
	        "    `process_id`, " +
	        "    `process_name`, " +
	        "    `lot_id`, " +
	        "    `lot_name`, " +
	        "    `lot_size`, " +
	        "    `lot_unit`, " +
	        "    `lot_qty`, " +
	        "    `lot_work`, " +
	        "    `start_time`, " +
	        "    `end_time`, " +
	        "    `status`, " +
	        "    `penalty`, " +
	        "    `group_id`, " +
	        "    `start_type`, " +
	        "    `start_order`, " +
	        "    `start_lot`, " +
	        "    `start_count`, " +
	        "    `estimate_time`, " +
	        "    `color`, " +
	        "    `auto_due`" +
	        ") VALUES (" +
	        "    0, " +
	        "    (SELECT MAX(`order_id`) + 1 FROM \"production_plan\"), " +
	        "    #{order_name}, " +
	        "    1, " +
	        "    'F1', " +
	        "    #{item_id}, " +
	        "    (SELECT DISTINCT item_name FROM process_master WHERE item_id = #{item_id}), " +
	        "    #{proc_id}, " +
	        "    #{proc_name}, " +
	        "    #{lot_id}, " +
	        "    #{lot_name}, " +
	        "    #{lot_size}, " +
	        "    #{lot_unit}, " +
	        "    #{lot_qty}, " +
	        "    #{lot_work}, " +
	        "    #{start_time}, " +
	        "    #{end_time}, " +
	        "    0, " +
	        "    1, " +
	        "    0, " +
	        "    0, " +
	        "    0, " +
	        "    0, " +
	        "    0, " +
	        "    600345, " +
	        "    5579200, " +
	        "    0" +
	        ")")
	void insertplan(@Param("lot_id") int lot_id, @Param("lot_name") String lot_name, @Param("lot_qty") int lot_qty, @Param("lot_size") int lot_size, @Param("lot_unit") String lot_unit, @Param("proc_id") int proc_id,
	                @Param("proc_name") String proc_name, @Param("start_time") String start_time, @Param("end_time") String end_time, @Param("item_id") int item_id, @Param("order_name") String order_name, @Param("lot_work") int lot_work);

	
	@Delete("DELETE FROM production_plan "
			+ "WHERE order_id=#{order_id}")
	void delete(int order_id);
	

	@Update("UPDATE production_plan " +
	        "SET order_name = #{order_name}, item_id = #{item_id}, " +
	        "item_name = (SELECT DISTINCT item_name FROM process_master WHERE item_id = #{item_id}), " +
	        "process_id = #{process_id}, process_name = #{process_name}, " +
	        "lot_id = #{lot_id}, lot_name = #{lot_name}, " +
	        "lot_size = #{lot_size}, lot_unit = #{lot_unit}, " +
	        "lot_qty = #{lot_qty}, lot_work = #{lot_work}, " +
	        "start_time = #{start_time}, end_time = #{end_time} " +
	        "WHERE order_id = #{order_id}")
	void update(int lot_id, String lot_name, int lot_qty, int lot_size, String lot_unit, int proc_id, String proc_name,
			String start_time, String end_time, int item_id, String order_name, int lot_work,int order_id);

}
