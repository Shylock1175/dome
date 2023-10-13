package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.SetmealDish;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询对应的套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 新增套餐中的菜品
     * @param list
     */
    void addSetmeal(List<SetmealDish> list);

    /**
     * 批量删除套餐
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
    void delete(Long setmealId);

    /**
     *  根据Id查询套餐
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id=#{setmealId} ")
    List<SetmealDish> getById(Long setmealId);
}
