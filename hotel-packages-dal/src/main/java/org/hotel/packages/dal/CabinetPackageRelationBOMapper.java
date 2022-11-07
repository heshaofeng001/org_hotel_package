package org.hotel.packages.dal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hotel.packages.dal.po.CabinetPackageRelationBO;
import org.hotel.packages.dal.po.CabinetPackageRelationBOExample;
@Mapper
public interface CabinetPackageRelationBOMapper {
    long countByExample(CabinetPackageRelationBOExample example);

    int deleteByExample(CabinetPackageRelationBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CabinetPackageRelationBO record);

    int insertSelective(CabinetPackageRelationBO record);

    List<CabinetPackageRelationBO> selectByExample(CabinetPackageRelationBOExample example);

    CabinetPackageRelationBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CabinetPackageRelationBO record, @Param("example") CabinetPackageRelationBOExample example);

    int updateByExample(@Param("record") CabinetPackageRelationBO record, @Param("example") CabinetPackageRelationBOExample example);

    int updateByPrimaryKeySelective(CabinetPackageRelationBO record);

    int updateByPrimaryKey(CabinetPackageRelationBO record);
}