package com.example.mybatisplus.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.mybatisplus.common.rest.ResultBean;
import com.example.mybatisplus.model.UserDO;
import com.example.mybatisplus.model.dto.UserDto;
import com.example.mybatisplus.model.vo.UserVo;
import com.example.mybatisplus.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = {"/user"})
    public ResultBean<List<UserVo>> users() {
        List<UserDO> poList = userService.list();
        List<UserVo> voList = BeanUtil.copyToList(poList , UserVo.class);
        return ResultBean.ok(voList);
    }

    @GetMapping(value = {"/user/{id}"})
    public ResultBean<UserVo> user(@PathVariable("id")Long id) {
        UserDO user = userService.getById(id);
        UserVo userVo = BeanUtil.copyProperties(user , UserVo.class);
        return ResultBean.ok(userVo);
    }

    @PostMapping(value = "/user")
    public ResultBean<UserDO> save(@RequestBody UserDto userDto) {
        UserDO user = BeanUtil.copyProperties(userDto , UserDO.class);
        boolean flag = userService.save(user);
        if (flag) return ResultBean.ok(user);
        return ResultBean.badRequest("新增失败");
    }

    @DeleteMapping(value = "/user/{id}")
    public ResultBean<Long> del(@PathVariable("id") Long id) {
        boolean flag = userService.removeById(id);
        if (flag) return ResultBean.ok(id);
        return ResultBean.badRequest("删除失败");
    }

    @PutMapping(value = "/user")
    public ResultBean<UserDO> update(@RequestBody UserDto userDto) {
        UserDO user = BeanUtil.copyProperties(userDto , UserDO.class);
        boolean flag = userService.updateById(user);
        if (flag) return ResultBean.ok(user);
        return ResultBean.badRequest("更新失败");
    }

}
