package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface OrderDetailMapper {
    /**
     * 插入订单明细
     * @param orderDetailList
     */
    void insert(List<OrderDetail> orderDetailList);

    /**
     * 获取订单详情
     * @param orderId
     * @return
     */
    @Select("select * from order_detail where order_id=#{orderId}")
    List<OrderDetail> getById(Long orderId);
}
