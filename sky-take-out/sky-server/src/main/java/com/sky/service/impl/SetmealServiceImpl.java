package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    public PageResult setmealPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> setmealVOS= setmealMapper.setmealPage(setmealPageQueryDTO);
        return new  PageResult(setmealVOS.getTotal(),setmealVOS.getResult());
    }

    /**
     * 批量的删除
     * @param ids
     */
    @Transactional
    public void delete(List<Long> ids) {
        ids.forEach(id ->{
            Setmeal setmeal=setmealMapper.getById(id);
            if (StatusConstant.ENABLE== setmeal.getStatus()){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }

        });
        ids.forEach(setmealId->{
            //删除套餐表中的数据
            setmealMapper.delete(setmealId);
            //删除套餐菜品关系表中的数据
            setmealDishMapper.delete(setmealId);
        });


    }

    /**
     *  根据Id查询套餐
     * @param id
     * @return
     */
    public SetmealVO getById(Long id) {
        Setmeal setmeal= setmealMapper.getById(id);
        List<SetmealDish>dishes=setmealDishMapper.getById(id);
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(dishes);

        return setmealVO;
    }

    /**
     * 修改套餐信息
     * @param setmealDTO
     */
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //1、修改套餐表，执行update
        setmealMapper.update(setmeal);
        //套餐id
        Long setmealId=setmealDTO.getId();
        //2、删除套餐和菜品的关联关系，操作setmeal_dish表，执行delete
        setmealDishMapper.delete(setmealId);
        List<SetmealDish>dishes=setmealDTO.getSetmealDishes();
        dishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        setmealDishMapper.addSetmeal(dishes);
    }

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     */
    public void startOfStop(Integer status, Long id) {
        Setmeal setmeal=Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //向套餐中添加1条
        setmealMapper.addSetmeal(setmeal);
        //获取insert语句生成的主键值
        Long id=setmeal.getId();
        List<SetmealDish>list=setmealDTO.getSetmealDishes();
        if (list!=null && list.size()>0){
        list.forEach(setmealDish -> setmealDish.setSetmealId(id));
        setmealDishMapper.addSetmeal(list);
        }

    }
    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
