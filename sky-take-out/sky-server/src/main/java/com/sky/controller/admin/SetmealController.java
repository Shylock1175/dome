package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = "setmeal",key = "#setmealDTO.categoryId")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐:{}", setmealDTO);
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> setmealPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("套餐分页查询:{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.setmealPage(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(cacheNames = "setmeal",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {

        log.info("批量的删除:{}", ids);
        setmealService.delete(ids);
        return Result.success();
    }

    /**
     * 根据Id查询套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据Id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据Id查询套餐:{}", id);
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐信息
     *
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐信息")
    @CacheEvict(cacheNames = "setmeal",allEntries = true)
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐信息:{}",setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐起售、停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售、停售")
    @CacheEvict(cacheNames = "setmeal",allEntries = true)
    public Result startOfStop(@PathVariable Integer status,Long id){
        log.info("套餐起售、停售:{},{}",status,id);
        setmealService.startOfStop(status,id);
        return Result.success();
    }
}
