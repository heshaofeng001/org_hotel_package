package org.hotel.packages.dal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hotel.packages.dal.po.CabinetBO;
import org.hotel.packages.dal.po.CabinetBOExample;
@Mapper
public interface CabinetBOMapper {
    long countByExample(CabinetBOExample example);

    int deleteByExample(CabinetBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CabinetBO record);

    int insertSelective(CabinetBO record);

    List<CabinetBO> selectByExample(CabinetBOExample example);

    CabinetBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CabinetBO record, @Param("example") CabinetBOExample example);

    int updateByExample(@Param("record") CabinetBO record, @Param("example") CabinetBOExample example);

    int updateByPrimaryKeySelective(CabinetBO record);

    int updateByPrimaryKey(CabinetBO record);
}