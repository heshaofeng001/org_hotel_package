package org.hotel.packages.dal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hotel.packages.dal.po.WorkOderBO;
import org.hotel.packages.dal.po.WorkOderBOExample;
@Mapper
public interface WorkOderBOMapper {
    long countByExample(WorkOderBOExample example);

    int deleteByExample(WorkOderBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WorkOderBO record);

    int insertSelective(WorkOderBO record);

    List<WorkOderBO> selectByExample(WorkOderBOExample example);

    WorkOderBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WorkOderBO record, @Param("example") WorkOderBOExample example);

    int updateByExample(@Param("record") WorkOderBO record, @Param("example") WorkOderBOExample example);

    int updateByPrimaryKeySelective(WorkOderBO record);

    int updateByPrimaryKey(WorkOderBO record);
}