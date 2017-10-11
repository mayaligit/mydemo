package com.key.dwsurvey.controller.mysurveycontroller;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 我的问卷
 * Created by issuser on 2017/9/26 0026.
 */
@Controller
@RequestMapping(value="/design")
public class MySurveyController {

    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private AccountManager accountManager;


    @RequestMapping("/my-survey")
    public ModelAndView list(Page page) throws Exception {
        ModelAndView mav = new ModelAndView("/page/content/diaowen-design/list");
        HttpServletRequest request= SpringUtils.getRequest();
        try{
            String surveyState = request.getParameter("surveyState");
            String surveyName = request.getParameter("surveyName");
            SurveyDirectory surveyDirectory=new SurveyDirectory();
            String pageParam = request.getParameter("page.pageNo");
            if(pageParam !=null){
                int pageNo=Integer.parseInt(pageParam);
                page.setPageNo(pageNo);
            }
            if(surveyState==null||"".equals(surveyState)){
                surveyDirectory.setSurveyState(null);
            }
            if(surveyName!=null&&!"".equals(surveyName)){
                surveyDirectory.setSurveyName(surveyName);
            }
            page=surveyDirectoryManager.findByUser(page,surveyDirectory);
        }catch (Exception e) {
            e.printStackTrace();
        }
        mav.addObject("page",page);
        return mav;
    }

    @RequestMapping("/my-survey!delete")
    @ResponseBody
    public String delete(@RequestParam("id")String id) throws Exception {
        HttpServletResponse response=SpringUtils.getResponse();
        String result="false";
        try{
            User user = accountManager.getCurUser();
            if(user!=null){
                String userId=user.getId();
                SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(id,userId);
                if(surveyDirectory!=null){
                    surveyDirectoryManager.delete(id);
                    result="true";
                }
            }
        }catch (Exception e) {
            result="false";
        }
        response.getWriter().write(result);
        return null;
    }
    
    //问卷壮态设置
    @RequestMapping("/my-survey!surveyState")
    @ResponseBody
    public String surveyState(@RequestParam("id") String id,
                              @RequestParam("surveyState") int surveyState) throws Exception{
        HttpServletResponse resp=SpringUtils.getResponse();
        String result="";
        try{
            User user= accountManager.getCurUser();
            if(user!=null){
                String userId=user.getId();
                SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(id, userId);
                if(surveyDirectory!=null){
                    surveyDirectory.setSurveyState(surveyState);
                    surveyDirectoryManager.upSurveyData(surveyDirectory);
                }
            }
            result="true";
        }catch(Exception e){
            e.printStackTrace();
            result="error";
        }
        //resp.getWriter().write(result);
        return result;
    }

    /**
     * 设置收集规则
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/my-survey!attrs")
    public String attrs(@RequestParam("id")String id) throws Exception {
        HttpServletRequest request=SpringUtils.getRequest();
        HttpServletResponse response=SpringUtils.getResponse();
        try{
            SurveyDirectory survey=surveyDirectoryManager.getSurvey(id);
            JsonConfig cfg = new JsonConfig();
            cfg.setExcludes(new String[]{"handler","hibernateLazyInitializer"});
            JSONObject jsonObject=JSONObject.fromObject(survey,cfg);
            response.getWriter().write(jsonObject.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    protected final static String COLLECT1="collect1";
    protected final static String IFRAME="iframe";
    protected final static String SITECOMP="sitecomp";
    protected final static String WEIXIN="weixin";
    protected final static String SHARE="share";

    /**
     * 问卷收集答卷
     * @param surveyId
     * @return jsp
     */
    @RequestMapping("/my-collect")
    public String collectAnswer(@RequestParam("surveyId") String surveyId,Model model){
        HttpServletRequest request=SpringUtils.getRequest();
        String tabId=request.getParameter("tabId");
        String baseUrl = "";
        baseUrl = request.getScheme() +"://" + request.getServerName()
                + (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
                + request.getContextPath();

        request.setAttribute("baseUrl", baseUrl);
        model.addAttribute("surveyId", surveyId);
        model.addAttribute("baseUrl",baseUrl);
        User user=accountManager.getCurUser();
        if(user!=null){
            SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(surveyId, user.getId());
            if(surveyDirectory!=null){
                request.setAttribute("survey", surveyDirectory);
                if(IFRAME.equals(tabId)){
                    return "page/content/diaowen-collect/collect_iframe";
                }else if(SITECOMP.equals(tabId)){
                    return "page/content/diaowen-collect/collect_website";
                }else if(WEIXIN.equals(tabId)){
                    return "page/content/diaowen-collect/collect_weixin";
                }else if(SHARE.equals(tabId)){
                    return "page/content/diaowen-collect/collect_2";
                }
                return "page/content/diaowen-collect/collect_1";
            }
        }
        return null;
    }

}
