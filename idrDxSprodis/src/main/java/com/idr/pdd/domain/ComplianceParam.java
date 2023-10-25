package com.idr.pdd.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplianceParam {
	private String order_id;
	private String order_name;
	private String start_time;
	private String trans_starttime;
	private String end_time;
	private String trans_endtime;
	private String due_time;
	private String trans_duetime;
	private String rate;
	private String taken_time;
}