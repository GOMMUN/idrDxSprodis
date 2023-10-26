package com.idr.pdd.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadTimeResult {
	private String proctime;
	private String AVGleadtime;
	private String MAXleadtime;
	private String MINleadtime;
	private String AVGlosstime;
	private String MAXlosstime;
	private String MINlosstime;
	
}
