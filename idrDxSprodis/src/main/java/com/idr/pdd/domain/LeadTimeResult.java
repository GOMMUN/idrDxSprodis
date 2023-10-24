package com.idr.pdd.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadTimeResult {
	private String ProcTimeD;
	private String ProcTimeH;
	private String AvgLeadTimeD;
	private String AvgLeadTimeH;
    private String MaxLeadTimeD;
    private String MaxLeadTimeH;
    private String MinLeadTimeD;
    private String MinLeadTimeH;
    private String AvgLossTimeD;
    private String AvgLossTimeH;
    private String MaxLossTimeD;
    private String MaxLossTimeH;
    private String MinLossTimeD;
    private String MinLossTimeH;
}
