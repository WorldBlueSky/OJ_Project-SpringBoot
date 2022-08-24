package com.example.demo.controller;

import com.example.demo.exception.InsertException;
import com.example.demo.exception.UsernameDuplicatedException;
import com.example.demo.exception.DeleteException;
import com.example.demo.exception.UpdateException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.JsonResult;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/usermanage")
public class UserManageController extends BaseController{

    @Autowired
    public UserService userService;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public ObjectMapper objectMapper;


    // 根据id删除用户
    // 6010-6019
    @RequestMapping("/delete")
    public void delete(String id, HttpServletResponse resp) throws DeleteException, IOException {

        int idString =Integer.parseInt(id.trim());

        int row = userMapper.delete(idString);
        
        if(row!=1){
            //System.out.println("删除失败!");
            throw new DeleteException("删除失败!");
        }

        // 此时说明删除成功,转发到管理界面，相当于刷新界面
        resp.sendRedirect("../userManage.html");
    }

    // 查询全部用户
    //6020-6029
    @RequestMapping("/selectAll")
    public JsonResult<List<User>> selectAll() throws JsonProcessingException {
        JsonResult<List<User>> result = new JsonResult<>();
        List<User> list = userMapper.selectAll();
        if(list==null){
            result.setState(6020);
            result.setMessage("服务器异常，查询题目列表失败!");
            return result;
        }

        result.setState(6021);
        result.setMessage("查询成功!");
        result.setData(list);

        return result;
    }


    // 6040-6049
    @RequestMapping("/search")
    public JsonResult<List<User>>getUser(@RequestParam(required = false) String username){

        JsonResult<List<User>> result = new JsonResult<>();

        if(username==null){
            result.setState(6040);
            result.setMessage("参数非法!");
            return result;
        }

        List<User> list = userMapper.selectByLikeName("%"+username.trim()+"%");
        if(list==null|| list.isEmpty()){// 说明查询没有结果
            result.setState(6041);
            result.setMessage("此次查询未查找到相关结果!");
            return result;
        }

        // 查询到了相关的结果
        result.setState(6042);
        result.setMessage("查找结果已显示在当前页面中!");
        result.setData(list);

        //System.out.println(list);
        return result;

    }

    // 6050-6059
    @RequestMapping("/isload")
    public JsonResult<Void> load(HttpSession session){

        JsonResult<Void> result = new JsonResult<>();

        User user = (User)session.getAttribute("user");
        if(user.getIsAdmin()==1){
            // 说明当前用户是管理员用户
            result.setState(6050);
            result.setMessage("当前用户是管理员用户!");
            return result;
        }

        result.setState(6051);
        result.setMessage("当前用户只是普通用户!");
        return result;
    }

    // 6060-6069
    @RequestMapping("/selectById")
    public JsonResult<User> selectOne(String id) throws JsonProcessingException {
        JsonResult<User> result = new JsonResult<>();
        User user = userMapper.selectById(Integer.parseInt(id));
        result.setMessage("查询成功!");
        result.setData(user);
        return result;
    }

    // 6070-6079
    @RequestMapping("/updateUser")
    public JsonResult<Void> updateUser(String id, @RequestBody User user) throws UpdateException {
        Integer rows = userMapper.updateUser(Integer.parseInt(id),user.getUsername(),user.getEmail());

        if(rows!=1){
            throw new UpdateException("服务器异常，修改失败!");
        }

        JsonResult<Void> result = new JsonResult<>();
        result.setState(6070);
        result.setMessage("修改成功");
        return result;
    }

}
