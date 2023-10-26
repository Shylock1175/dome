package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * 插入一条订单
     * @param orders
     */
    void insert(Orders orders);
    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 历史记录查询
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    @Select("select *from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 根据状态，分别查询出待接单、待派送、派送中的订单数量
     * @param status
     * @return
     */
    @Select("select count(id)from orders where status=#{status}")
    Integer countStatus(Integer status);

    /**
     * 根据状态和下单时间查询订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status=#{status} and order_time=#{orderTime}")
    List<Orders> getByStatusAndOrdertimeLT(Integer status, LocalDateTime orderTime);

    /**
     * 根据时间区间统计营业额
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * 根据时间区间统计指定状态的订单数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * 查询商品销量排名
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
