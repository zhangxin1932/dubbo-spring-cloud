package com.zy.sharding.mybatis.mapper;

import com.zy.sharding.mybatis.entity.TbOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * https://github.com/apache/shardingsphere-example/tree/dev/sharding-jdbc-example/sharding-example/sharding-spring-boot-mybatis-example/src/main/resources
 * https://segmentfault.com/a/1190000023828622
 */
@Repository
public interface TbOrderMapper {

    int insert(@Param("orderName") String orderName, @Param("id") Long id);

    TbOrder selectOrderById(@Param("id") Long id);

    List<TbOrder> selectOrders();
}
