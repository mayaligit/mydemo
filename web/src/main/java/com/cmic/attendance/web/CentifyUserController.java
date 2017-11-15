package com.cmic.attendance.web;

import com.cmic.attendance.Constant.Constant;
import com.cmic.attendance.model.RcsToken;
import com.cmic.attendance.model.UserBo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.web.RestException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 何荣
 * @create 2017-10-25 9:58
 **/
@Api(description = "考勤表管理")
@RestController
@RequestMapping("/attendance")
public class CentifyUserController {

    private static Logger log = Logger.getLogger(CentifyUserController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${index.server}")
    private String index;

    /**
     * +
     * 考勤系统入口
     *
     * @param rcsToken 封装请求数据
     * @param request+
     * @return
     */
    @RequestMapping("/info")
    public ModelAndView info(RcsToken rcsToken, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getSession().getId();
        /*response.setHeader("Access-Control-Allow-Origin", "*");*/
        //校验token , 并将获取到的用户信息放到session域中
        this.certifyToken(request, rcsToken);
        //判断session中是否有登陆用户
        UserBo user = this.getSessionUser(request);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + index);
        return mav;
    }

    private BaseAdminEntity certifyToken(HttpServletRequest request, RcsToken rcsToken) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("token", rcsToken.getToken());
        paramMap.add("contactId", rcsToken.getContactId());
        paramMap.add("enterId", rcsToken.getEnterId());
        //用户名的获取;

        String userStr = this.restTemplate.postForObject(Constant.certifyServicePath + Constant.userINfo, paramMap, String.class);
        if (null == userStr) {
            throw new RestException("统一认证,获取用户信息失败");
        }
        //解析请求数据 , 获取用户电话和用户名
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject) parser.parse(userStr);
        String phone = jsonObject.get("msisdn").getAsString();
        String username = jsonObject.get("username").getAsString();
        //获取用户所属企业ID 和 企业名称
        String sessionId = request.getSession().getId();
        String enterId = rcsToken.getEnterId();
        String enterName = rcsToken.getEnterName();

        //将用户信息封装到实体类中,并放入session域中
        BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId(phone);
        adminEntity.setName(username);
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO"    ,adminEntity);
        log.debug("adminEntity:" + adminEntity.toString());
        return adminEntity;
    }

    private UserBo getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (UserBo) session.getAttribute(Constant.LOGIN_SESSION_KEY);
    }
}
