package org.hotel.packages.dal;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.hotel.packages.dal.po.PackageInfoBO;
import org.hotel.packages.dal.po.PackageInfoBOExample;

public interface PackageInfoBOMapper {
    long countByExample(PackageInfoBOExample example);

    int deleteByExample(PackageInfoBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PackageInfoBO record);

    int insertSelective(PackageInfoBO record);

    List<PackageInfoBO> selectByExample(PackageInfoBOExample example);

    PackageInfoBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PackageInfoBO record, @Param("example") PackageInfoBOExample example);

    int updateByExample(@Param("record") PackageInfoBO record, @Param("example") PackageInfoBOExample example);

    int updateByPrimaryKeySelective(PackageInfoBO record);

    int updateByPrimaryKey(PackageInfoBO record);
}