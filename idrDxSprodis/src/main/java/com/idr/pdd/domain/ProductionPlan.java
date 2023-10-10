package com.idr.pdd.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductionPlan {

	private int no;
	private int order_id;
	private String order_name;
	private int flow_id;
	private String flow_name;
	private int item_id;
	private String item_name;
	private int process_id;
	private String process_name;
	private int lot_id;
	private String lot_name;
	private int lot_size;
	private String lot_unit;
	private int lot_qty;
	private int lot_work;
	private String start_time;
	private String end_time;
	private int first_lot_qty;
	private int status;
	private int penalty;
	private int group_id;
	private int start_type;
	private int start_order;
	private int start_lot;
	private int start_count;
	private float estimate_time;
	private int color;
	private int auto_due;
}
