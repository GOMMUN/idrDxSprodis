package com.idr.pdd.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.idr.pdd.domain.ProductionPlan;

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
			+ " , start_time "
			+ " , end_time "
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
}
