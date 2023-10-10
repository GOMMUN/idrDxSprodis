package com.idr.pdd.mapper.primary;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.idr.pdd.domain.ProcessMaster;

@Mapper
public interface ProcessMasterMapper {

	@Select("SELECT item_id, item_name FROM process_master")
	List<ProcessMaster> findAll();
}
