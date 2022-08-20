package com.example.demo.controller;

import com.example.demo.exception.InsertException;
import com.example.demo.exception.PasswordNotMatchException;
import com.example.demo.exception.UsernameDuplicatedException;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.pojo.User;
import com.example.demo.service.SendSmsService;
import com.example.demo.service.UserService;
import com.example.demo.pojo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/oj")
public class UserController extends BaseController{
    @Autowired
    public UserService userService;

    @Autowired
    public SendSmsService sendSmsService;

    @Autowired
    public RedisTemplate<String,Object> redisTemplate;

    public Random random = new Random();


    /**
     *
     * 请求的路径 users/reg
     * 这是接收注册的请求 User user
     * 响应结果 JsonResult<void>
     */

    /**
     * 注册的逻辑
     * (1)如果用户名或密码或确认密码为空，那么返回信息提示：用户名或密码不能为空!
     * (2)此时都不为空，如果确认密码或密码不一致，那么返回信息提示：两次密码不一致，请重新输入!
     * (3)此时都不为空，且两次输入密码一致，查询数据库，如果输入的用户名已经存在了，那么抛出用户名已被占用异常，异常返回信息提示：用户名已被占用!
     * (4)此时都没有问题，但是在执行数据库插入数据的过程中，服务器断开了连接或者宕机了，此时抛出服务器中断异常，异常返回信息提示：服务器异常，注册失败!
     * (5)排除前面的所有情况，此时注册成功，返回信息提示:注册成功！
     *
     */
    @RequestMapping(value = "/reg",produces = "application/json;charset=utf8")
    public JsonResult<Void> register(String username, String password, String required) throws InsertException, UsernameDuplicatedException {

        HashMap<String,Object> map = new HashMap<>();
        JsonResult<Void> result = new JsonResult<>();

        if(username==null ||username.equals("") || password==null || password.equals("") ||required==null || required.equals("") ){
            //map.put("status",1001);
            //map.put("message","用户名或密码不能为空!");
            //return map;

            result.setState(1001);
            result.setMessage("用户名密码不能为空!");
            return result;

        }

//        System.out.println(username);
//        System.out.println(password);
//        System.out.println(required);

        if(!password.equals(required)){
            //map.put("status",1002);
            //map.put("message","两次密码不一致，请重新输入!");
            //return map;

            result.setState(1002);
            result.setMessage("两次密码不一致，请重新输入!");
            return result;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        userService.register(user);

        //map.put("status",1003);
        //map.put("message","注册成功!");
        //return map;

        result.setState(1003);
        result.setMessage("注册成功!");
        return result;
    }

    /**
     * 用户名密码登陆的逻辑
     * (1) 对传入的参数进行非空校验，如果有空的，那么返回信息提示：用户名或密码不能为空
     * (2) 如果都不为空，根据用户名进行查找，如果不存在，那么配出用户名不存在异常，异常返回信息提示：您输入的用户名不存在，请重新输入
     * (3) 如果用户名存在，那么将用户名和输入的密码（加盐值）进行匹配,如果匹配不上，那么抛出匹配错误异常，异常返回信息提示：输入密码错误，请重新输入
     * (4) 如果密码匹配成功，那么返回登陆成功的结果信息，前端跳转到主页
     */
    @RequestMapping(value = "/login",produces = "application/json;charset=utf8")
    public JsonResult<Void> loginByUser(String username, String password, HttpServletRequest request) throws PasswordNotMatchException, UsernameNotFoundException {

        //HashMap<String,Object> map = new HashMap<>();
        JsonResult<Void> result = new JsonResult<>();

        // 对参数进行非空校验
        if(username==null ||username.equals("") || password==null || password.equals("")  ){
            //map.put("status",3003);
            //map.put("message","用户名或密码不能为空!");
            //return map;

            result.setState(3003);
            result.setMessage("用户名或密码不能为空!");
            return result;
        }

        // 如果都不为空，那么执行登陆业务
        User user = userService.login(username,password);

        //如果登陆成功的话，返回登录成功的结果
        //map.put("status",3004);
        //map.put("message","登陆成功");

        result.setState(3004);
        result.setMessage("登陆成功!");

        // 设置全局session
        HttpSession session =request.getSession(true);
        session.setAttribute("user",user);

        //System.out.println("user:"+session.getAttribute("user"));

        //return map;

        return result;
    }

    /**
     * 发送手机验证码的逻辑
     *  (0) 先对手机号参数进行非法验证，非空、手机号位数11
     *  (1) 连接redis，查找手机验证码是否仍然存在未过期，如果未过期的话，那么不能发送，返回信息:验证码还未过期，再次发送失败
     *  (2) 查询数据库如果不存在验证码的话，那么生成6位随机数，发送验证码，
     *  如果发送失败，那么返回结果提示 验证码发送失败，为什么会发送失败？腾讯云每天对发送短信条数频率是会限制的，发送短信是要钱的，个人云账户没钱了等情况都会发生发送失败！
     *  如果发送成功，那么保存到redis中，设置过期时间为2分钟，返回信息提示：验证码发送成功，2分钟内有效，请及时填写
     *
     */
    @RequestMapping(value = "/phoneSend",produces = "application/json;charset=utf8")
    public JsonResult<Void> sendCode(@RequestParam(value = "phone") String phone) {


//        System.out.println(phone);
        //调用发送的方法即可

        //HashMap<String,Object> map =new HashMap<>();
        JsonResult<Void> result = new JsonResult<>();

        //0、对参数进行校验
        if(phone==null|| phone.equals("")){
             //map.put("status",4000);
             //map.put("message","输入的手机号不能为空，请重新输入!");
             //return map;

            result.setState(4000);
            result.setMessage("输入的手机号不能为空，请重新输入!");
            return result;
        }

        if(phone.length()!=11){
            //map.put("status",4001);
            //map.put("message","输入的手机号格式非法，请重新输入!");
            //return map;

            result.setState(4001);
            result.setMessage("输入的手机号格式非法，请重新输入!");
            return result;
        }

        //1、连接Redis，查找手机验证码是否存在
        String code = (String)redisTemplate.opsForValue().get(phone);

        //System.out.println("验证码："+code);

        //====================================================
        //1、1如果存在的话，说明在5分钟内已经发送过验证码了，不能再发了
        if (!StringUtils.isEmpty(code)) {
            //System.out.println("已存在，还没有过期，不能再次发送");
            //map.put("status",4002);
            //map.put("message","验证码还未过期，再次发送失败!");
            //return map;

            result.setState(4002);
            result.setMessage("验证码还未过期，再次发送失败!");
            return result;
        }
        //=====================================================

        //1。2 如果不存在的话,那么redis创建键值对生成验证码并存储，设置过期时间
        String newCode = "";
        // 生成6位随机验证码
        for (int i = 0; i < 6; i++) {
            newCode += random.nextInt(10);
        }
        // 将6位随机验证码对手机号进行发送
        boolean idSend = sendSmsService.send(phone,"1511343",newCode);

        //=====================================================

        // 因为有短信轰炸的情况，短信服务对每次发送限制次数，所以有发送不成功的情况，要考虑

        if(idSend){//如果发送成功将验证码存储到redis中
            redisTemplate.opsForValue().set(phone, newCode, 2, TimeUnit.MINUTES);
            //System.out.println("发送成功!");
            //map.put("status",4003);
            //map.put("message","验证码发送成功，2分钟内有效，请及时填写!");

            result.setState(4003);
            result.setMessage("验证码发送成功，2分钟内有效，请及时填写!");

        }else{
            //System.out.println("发送失败!");
            //map.put("status",4004);
            //map.put("message","验证码发送失败!");

            result.setState(4004);
            result.setMessage("验证码发送失败!");

        }
           return result;
    }

    /**
     * 手机验证码登陆的逻辑
     * （1）参数非空校验，phone 和code有空的，那么返回结果，提示信息：手机号或验证码不能为空
     * （2）如果不为空，那么根据手机号 查询redis数据库中的 验证码
     * （3）如果验证码不存在或者过期，那么返回结果提示信息，手机号输入错误或者超出输入有效时间，登陆失败!
     * （4）如果验证码存在，则进行比对，如果比对成功，那么返回登陆成功，前端跳转主页！如果失败，那么返回输入输入验证码错误，登陆失败！
     */

    @RequestMapping("/phoneLogin")
     public JsonResult<Void> phoneLog(String phone,String code,HttpSession session){
        //HashMap<String,Object> map =new HashMap<>();

        JsonResult<Void> result = new JsonResult<>();

        //0、对参数进行校验
        if(phone==null|| phone.trim().equals("")||code==null || code.trim().equals("")){
            //map.put("status",4005);
            //map.put("message","输入的手机号 或 验证码不能为空，请重新输入!");
            //return map;

            result.setState(4005);
            result.setMessage("输入的手机号 或 验证码不能为空，请重新输入!");
            return result;
        }

        if(phone.trim().length()!=11 || code.trim().length()!=6){
            //map.put("status",4006);
            //map.put("message","输入的手机号格式非法 或者 验证码格式错误，请重新输入!");
            //return map;

            result.setState(4006);
            result.setMessage("输入的手机号格式非法 或者 验证码格式错误，请重新输入!");
            return result;
        }

        //1、根据输入的手机号，在redis中查询验证码
        String phoneCode = (String)redisTemplate.opsForValue().get(phone.trim());

        if(phoneCode==null){// 手机号不存在或者 验证码已经过期了
            //map.put("status",4007);
            //map.put("message","手机号输入错误 或者 验证码输入超出有效时间，登陆失败!");
            //return map;

            result.setState(4007);
            result.setMessage("手机号输入错误 或者 验证码输入超过有效时间，登陆失败!");
            return result;
        }

        //2、如果验证码存在的话,进行比对
        if(!code.equals(phoneCode.trim())){// 比对失败
            //map.put("status",4008);
            //map.put("message","验证码输入错误，请重新输入!");
            //return map;

            result.setState(4008);
            result.setMessage("验证码输入错误，请重新输入!");
            return result;
        }

        //3、如果验证码比对成功，设置全局session，返回登陆成功
        session.setAttribute("phone",phone.trim());

        //map.put("status",4009);
        //map.put("message","手机号登陆成功!");
        //return map;

        result.setState(4009);
        result.setMessage("手机号登陆成功!");
        return result;
     }

     @RequestMapping("/logout")
     public JsonResult<Void> logout(HttpSession session){
        //HashMap<String,Object> map = new HashMap<>();
        JsonResult<Void> result = new JsonResult<>();

         if(session==null || (session.getAttribute("user")==null && session.getAttribute("phone")==null)){
            // 返回注销失败
             result.setState(5001);
             result.setMessage("注销失败!");
             return result;
        }

        if(session.getAttribute("user")!=null){
            session.removeAttribute("user");
        }
        if(session.getAttribute("phone")!=null){
            session.removeAttribute("phone");
        }

        // 此时注销成功
         //return map;

         result.setState(5002);
         result.setMessage("注销成功!");
         return result;
     }
}