package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.DishFlavor;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void intertBatch(List<DishFlavor> flavors);

    /**
     * 菜品批量删除
     * @param dishIds
     */
    @Delete("delete from dish_flavor where dish_id=#{dishId}")
    void deleteByDishId(Long dishIds);

    /**
     * 根据菜品id查询口味数据
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id=#{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
    /**
     * 菜品批量删除
     * @param ids
     */
    void deleteByDishIds(List<Long> ids);

    /**
     * 修改
     * @param id
     */

    
}
