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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserManageController extends BaseController{

    @Autowired
    public UserService userService;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public ObjectMapper objectMapper;

    // 增加用户

    //6000-6009
    @RequestMapping("/insert")
    public JsonResult<Void> add(@RequestParam("username") String username, @RequestParam("password") String password) throws InsertException, UsernameDuplicatedException {

        JsonResult<Void> result = new JsonResult<>();

        if(username==null || username.trim().equals("")||password==null ||password.trim().equals("")){
            result.setMessage("用户名 或 密码不能为空!");
            return result;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setPassword(password.trim());
        // 增加用户就相当于注册
        userService.register(user);

         //此时注册成功，返会JSON

        result.setState(6001);
        result.setMessage("添加用户成功!");

        //System.out.println(result);

        return result;

    }

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

    // 查询用户
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

   // 根据id修改用户
    //6030-6039
    @RequestMapping("/update")
    public JsonResult<Void> update(String id,String password) throws UpdateException {
        JsonResult<Void> result = new JsonResult<>();
        //1、根据id查找用户是否存在，如果不存在，那么返回信息提示：id参数非法，用户不存在
        if(id==null || id.trim().equals("") || password==null || password.equals("")){
            result.setMessage("id 或 密码不能为空!");
            return result;
        }

        User user = userMapper.selectById(Integer.parseInt(id.trim()));

        System.out.println(user);
        if(user==null){
            result.setMessage("输入的id参数不存在,请重新输入!");
            return result;
        }

        //2、如果用户存在的话，那么获取盐值，md5 5次加密，将新密码设置进去，修改密码即可
        String salt = user.getSalt();
        String finalPassword = UserServiceImpl.getMd5Password(password,salt);

        int row = userMapper.update(user.getId(),finalPassword);

        if(row!=1){
            throw new UpdateException("修改失败!");
        }

        result.setState(6031);
        result.setMessage("修改成功!");
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

}
