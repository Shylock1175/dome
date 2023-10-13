package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐
     * @param setmealDTO
     */
    void addSetmeal(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult setmealPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量的删除
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     *  根据Id查询套餐
     * @param id
     * @return
     */
    SetmealVO getById(Long id);

    /**
     * 修改套餐信息
     * @param setmealDTO
     */
    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    void startOfStop(Integer status, Long id);
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
