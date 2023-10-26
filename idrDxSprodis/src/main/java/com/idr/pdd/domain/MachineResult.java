package com.idr.pdd.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class MachineResult {
	private String machine_id;
    private String machine_name;
    private int uptime;
    private int MinStartTime;
    private int MaxFinishTime;
    private String equipPerformace;
}
