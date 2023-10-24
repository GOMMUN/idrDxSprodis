package com.idr.pdd.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class MachineResult {
	private String machineId;
    private String machineName;
    private float equipPerformace;
}
