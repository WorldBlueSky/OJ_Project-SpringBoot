package com.example.demo.controller;

import com.example.demo.exception.DeleteException;
import com.example.demo.exception.InsertException;
import com.example.demo.exception.UpdateException;
import com.example.demo.mapper.ProblemMapper;
import com.example.demo.pojo.JsonResult;
import com.example.demo.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class ProblemManageController extends BaseController{

    @Autowired
    public ProblemMapper problemMapper;

    //1、处理增加题目的接口
    //7000-7009
    @RequestMapping("/insert")
    public JsonResult<Void> add(@RequestBody Problem problem) throws InsertException {

        JsonResult<Void> result = new JsonResult<>();

        if(problem==null){
            result.setMessage("参数非法!");
            return result;
        }

        if(problem.getTitle().equals("")||problem.getLevel().equals("")
                ||problem.getDescription().equals("")
                || problem.getTemplate().equals("")
                || problem.getTestCode().equals("")
        ){
            result.setMessage("您提交的信息不完整，请补全题目信息在提交!");
            return result;
        }

        int row = problemMapper.insert(problem);
        if(row!=1){
            throw new InsertException("增加失败!");
        }

        result.setMessage("增加成功!");
        return result;
    }

    //2、处理修改题目的接口，按照题目id进行修改
    //7010-7019
    @RequestMapping("/update")
    public JsonResult<Void> update(String id, @RequestBody Problem problem) throws UpdateException {

        //System.out.println(problem);

        JsonResult<Void> result = new JsonResult<>();

        if(id==null||id.equals("")||problem==null){
            result.setMessage("参数非法!");
            return result;
        }

        if(problem.getTitle().equals("") ||
                problem.getLevel().equals("")||
                problem.getDescription().equals("")||
                problem.getTemplate().equals("")||
                problem.getTestCode().equals("")
        ){
            result.setMessage("填入的题目信息不全，请补全信息后再次提交!");
            return result;
        }

        int idString = Integer.parseInt(id);
        int rows = problemMapper.updateById(
                idString,problem.getTitle(),
                problem.getLevel(), problem.getDescription(),
                problem.getTemplate(),problem.getTestCode()
        );

        if(rows!=1){
            throw new UpdateException("修改失败");
        }

        result.setMessage("修改成功!");
        return result;
    }

    //3、处理删除题目的接口，按照题目id进行删除
    //7020-7029
    @RequestMapping("/delete")
    public JsonResult<Void> delete(String id, HttpServletResponse resp) throws DeleteException, IOException {
        JsonResult<Void> result =new JsonResult<>();

        if(id==null || id.equals("")){
            result.setMessage("id参数不能为空");
            return result;
        }

        int idString = Integer.parseInt(id);
        int row = problemMapper.delete(idString);
        if(row!=1){
            throw new DeleteException("删除失败");
        }

        result.setMessage("删除成功!");

        resp.sendRedirect("../problemManage.html");

        return result;
    }

    //4、查找，实现按照题目名字 模糊查询进行查找
    //7030-7039
    @RequestMapping("/select")
    public JsonResult<List<Problem>> select(String title){

        JsonResult<List<Problem>> result = new JsonResult<>();

        if(title==null){
            result.setState(7031);
            result.setMessage("参数非法!");
            return result;
        }

        List<Problem> list = problemMapper.selectByLikeTitle(title);
        if(list==null||list.isEmpty()){
            result.setState(7032);
            result.setMessage("未查询到相关题目信息!");
            return result;
        }

        result.setState(7033);
        result.setMessage("查询到了相关信息!");
        result.setData(list);
        return result;
    }

    @RequestMapping("/selectAll")
    //7040-7049
    public JsonResult<List<Problem>> selectAll(){

        JsonResult<List<Problem>> result = new JsonResult<>();

        List<Problem> list = problemMapper.selectAll();
        if(list==null||list.isEmpty()){
            result.setMessage("未查询到相关题目信息!");
            return result;
        }

        result.setMessage("查询到了相关信息!");
        result.setData(list);
        return result;
    }

    @RequestMapping("/selectById")
    //7050-7059
    public JsonResult<Problem> selectById(String id){
        //System.out.println(id);

        JsonResult<Problem> result = new JsonResult<>();

        if(id==null|| id.equals("")){
            result.setMessage("id参数不合法!");
            return result;
        }

        int idString = Integer.parseInt(id);
        Problem problem = problemMapper.selectOne(idString);
        if(problem==null){
            result.setMessage("未查找到相关题目信息!");
            return result;
        }
        result.setState(7051);
        result.setMessage("已查询到相关信息显示在当前界面上");
        result.setData(problem);

        System.out.println(problem);

        return result;
    }

}
