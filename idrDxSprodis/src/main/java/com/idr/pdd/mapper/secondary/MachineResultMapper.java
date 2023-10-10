package com.idr.pdd.mapper.secondary;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.idr.pdd.domain.MachineResult;
import com.idr.pdd.domain.ProcessMaster;

@Mapper
public interface MachineResultMapper {

	@Select("SELECT machine_id FROM machine_result")
	List<MachineResult> findAll();
}
