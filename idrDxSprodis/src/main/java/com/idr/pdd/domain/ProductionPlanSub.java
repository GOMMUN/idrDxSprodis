package com.idr.pdd.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductionPlanSub {

	private int proc_id;
	private String proc_name;
	private int lot_id;
	private String lot_name;
	private int lot_size;
	private String lot_unit;
	private int lot_qty;

}
