package org.hotel.packages.dal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hotel.packages.dal.po.CustomerBO;
import org.hotel.packages.dal.po.CustomerBOExample;
@Mapper
public interface CustomerBOMapper {
    long countByExample(CustomerBOExample example);

    int deleteByExample(CustomerBOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomerBO record);

    int insertSelective(CustomerBO record);

    List<CustomerBO> selectByExample(CustomerBOExample example);

    CustomerBO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CustomerBO record, @Param("example") CustomerBOExample example);

    int updateByExample(@Param("record") CustomerBO record, @Param("example") CustomerBOExample example);

    int updateByPrimaryKeySelective(CustomerBO record);

    int updateByPrimaryKey(CustomerBO record);
}