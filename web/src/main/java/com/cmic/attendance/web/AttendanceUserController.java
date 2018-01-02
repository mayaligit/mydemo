package com.cmic.attendance.web;


import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.service.AttendanceUserService;
import com.cmic.attendance.utils.MD5Util;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.web.BaseRestController;
import com.cmic.saas.utils.StringUtils;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 考勤后台管理表Controller
 */
@Api(description = "考勤后台管理表管理")
@RestController
@RequestMapping("/attendance/user")
public class AttendanceUserController extends BaseRestController<AttendanceUserService> {

    private static Logger log = Logger.getLogger(AttendanceUserController.class);

    @Value("${indexs.login}")
    private String login;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AttendanceUserService attendanceUserService;


    @ApiOperation(value = "查询", notes = "查询用户管理表列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderBy", value = "排序", defaultValue = "createDate desc", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", defaultValue = "1", paramType = "query")
    })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public PageInfo<AttendanceUser> get(@ApiIgnore AttendanceUser attendanceUser, @ApiIgnore PageInfo page) {
        page = service.findPage(page, attendanceUser);
        return page;
    }

    @ApiOperation(value = "新增", notes = "新增用户管理表", httpMethod = "POST")
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public Map<String,Object> post(@Validated @RequestBody AttendanceUser attendanceUser) {
        //service.insert(attendanceUser);
        Map<String,Object> resultMap = new HashMap<>();
        try {
            String password = MD5Util.md5(attendanceUser.getAttendancePassword());
            attendanceUser.setAttendancePassword(password);

            //测试数据
            HttpServletRequest request = WebUtils.getRequest();
            HttpServletResponse response = WebUtils.getRequestAttributes().getResponse();
            BaseAdminEntity adminEntity = (BaseAdminEntity)request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
            attendanceUser.setCreateBy(adminEntity);
            //response.setHeader("Access-Control-Allow-Origin", "*");
            //BaseAdminEntity adminEntity = new BaseAdminEntity();
            //adminEntity.setId("15240653787");
            //adminEntity.setName("梁渝");
            //测试数据结束
            //request.getSession().setAttribute("_CURRENT_ADMIN_INFO"    ,adminEntity);

            service.save(attendanceUser);
            resultMap.put("code","0");
            resultMap.put("msg","新增成功");
        }catch (Exception e){
            resultMap.put("code","1");
            resultMap.put("msg","新增失败");
        }
        return resultMap;
    }

    @ApiOperation(value = "获取", notes = "获取用户管理表", httpMethod = "POST")
    @RequestMapping(value = "/selectUserById/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AttendanceUser getAttendanceUser(@ApiParam(value = "考勤用户管理表ID") @PathVariable String id) {
        AttendanceUser attendanceUser = service.get(id);
        return attendanceUser;
    }

    @ApiOperation(value = "新增/更新", notes = "新增/更新用户管理表", httpMethod = "PUT")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public AttendanceUser put(@PathVariable String id, @Validated @RequestBody AttendanceUser attendanceUser) {
        attendanceUser.setId(id);
        service.save(attendanceUser);
        return getAttendanceUser(id);
    }

    @ApiOperation(value = "动态更新", notes = "动态更新用户管理表", httpMethod = "POST")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public Map<String,String> updateAttendanceUser(@RequestBody AttendanceUser attendanceUser) {
        //attendanceUser.setId(id);
        HashMap<String,String> resultHash =new HashMap<String,String>();

        //测试数据
        HttpServletRequest request = WebUtils.getRequest();
        HttpServletResponse response = WebUtils.getRequestAttributes().getResponse();
        //response.setHeader("Access-Control-Allow-Origin", "*");
        BaseAdminEntity adminEntity = (BaseAdminEntity)request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
       /* BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId("15240653787");
        adminEntity.setName("梁渝");*/
        attendanceUser.setUpdateBy(adminEntity);
        //request.getSession().setAttribute("_CURRENT_ADMIN_INFO"    ,adminEntity);
        try {
            String password = attendanceUser.getAttendancePassword();
            if(password!=null && password!="") {
                attendanceUser.setAttendancePassword(MD5Util.md5(password));
            }
            service.dynamicUpdate(attendanceUser);
            resultHash.put("code","0");
            resultHash.put("msg","更新成功");
            return resultHash;
        }catch (Exception e){
            resultHash.put("msg","更新失败");
            resultHash.put("code","1");
            return resultHash;
        }
    }

    @ApiOperation(value = "删除", notes = "删除考勤用户管理表", httpMethod = "DELETE")
    @RequestMapping(value = "/delectById/{id}", method = RequestMethod.DELETE)
    public void delete(@ApiParam(value = "考勤用户管理表ID") @PathVariable String id) {
        AttendanceUserVo attendanceUserVo= (AttendanceUserVo)WebUtils.getSession().getAttribute("attendanceUserVo");
            if(attendanceUserVo==null){
                return;
            }
        if("0".equals(attendanceUserVo.getUserType())){
            service.delete(id);
        }
    }


    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map login(@RequestBody AttendanceUserVo attendanceUserVo, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
//        验证码是否为空
        if (StringUtils.isBlank(attendanceUserVo.getCheckCode())) {
            map.put("status", "4");
            return map;
        }
        String checkCode = (String) WebUtils.getSession().getAttribute("checkCode");

        if (StringUtils.isNotBlank(checkCode)) {
//            验证码是否不正在正确
            if (!attendanceUserVo.getCheckCode().equals(checkCode)) {

                map.put("status", "3");
                return map;
            }
        }
        Map login = service.login(attendanceUserVo, request);

        /*if ("0".equals(login.get("status"))){
            redisTemplate.boundValueOps("attendanceUser").set("attendanceUser");
            redisTemplate.expire("attendanceUser", 30, TimeUnit.MINUTES);
            log.debug("登录成功的session"+attendanceUserVo.toString());
        }*/
        return login;
    }

    @ApiOperation(value = "用户退出", notes = "用户退出", httpMethod = "GET")
    @RequestMapping(value = "/noLogint", method = RequestMethod.POST)
    public HashMap<String, String> noLogint(HttpServletRequest request) {
       /* redisTemplate.delete("attendanceUser");*/
       /* request.getSession().removeAttribute("attendanceUserVo");*/
        log.debug(">>>>>>>用户没登录<<<<<<<<<");
        HashMap<String, String> reslutMap = new HashMap<String, String>();
        reslutMap.put("code", "2");
        return reslutMap;
    }

    @ApiOperation(value = "用户退出", notes = "用户退出", httpMethod = "GET")
    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    public HashMap<String, String> loginOut(HttpServletRequest request) {
       /* redisTemplate.delete("attendanceUser");*/
        request.getSession().removeAttribute("attendanceUserVo");
        log.debug(">>>>>>>系统管理员退出<<<<<<<<<");
        HashMap<String, String> reslutMap = new HashMap<String, String>();
        reslutMap.put("status", "0");
        return reslutMap;
    }
    /*@ApiOperation(value = "用户退出", notes = "用户退出", httpMethod = "GET")
    @RequestMapping(value="/loginout3", method = RequestMethod.GET)
    public ModelAndView info(RcsToken rcsToken, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + index);
        return mav;
    }*/

    @ApiOperation(value = "查找", notes = "显示用户列表")
    @RequestMapping(value = "/adminList", method = RequestMethod.POST)
    public Map<String, Object> findAttendanceUserList(@RequestBody AttendanceUserVo attendanceUserVo) {
        Integer pageNum=attendanceUserVo.getPageNum();
        Integer pageSize=attendanceUserVo.getPageSize();

        return service.findAttendanceUserList(pageNum, pageSize, attendanceUserVo.getAttendanceUsername());

    }

    @ApiOperation(value = "查找当前用户", notes = "显示当前用户信息")
    @RequestMapping(value = "/findUserByName", method = RequestMethod.POST)
    public AttendanceUser findUserByName(@RequestBody AttendanceUser attendanceUser) {
        AttendanceUser user = service.checkUserByName(attendanceUser.getAttendanceUsername());
        return user;
    }

}