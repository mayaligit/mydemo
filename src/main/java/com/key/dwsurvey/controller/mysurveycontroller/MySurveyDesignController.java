package com.key.dwsurvey.controller.mysurveycontroller;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.DiaowenProperty;
import com.key.common.utils.JspToHtml;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.entity.Question;
import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.entity.SurveyStyle;
import com.key.dwsurvey.service.QuestionManager;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.service.SurveyReqUrlManager;
import com.key.dwsurvey.service.SurveyStyleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by issuser on 2017/9/26 0026.
 */

@Controller
@RequestMapping("/design")
public class MySurveyDesignController {

    protected final static String PREVIEWDEV = "previewDev";
    protected final static String COLLECTSURVEY = "collectSurvey";
    protected final static String RELOADDESIGN = "reloadDesign";

    @Autowired
    private QuestionManager questionManager;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    @Autowired
    private SurveyStyleManager surveyStyleManager;
    @Autowired
    private SurveyReqUrlManager surveyReqUrlManager;
    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value = "/my-survey-design")
    public ModelAndView intoWenJuanPage(@RequestParam("surveyId") String surveyId,
                                        HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/page/content/diaowen-design/survey");
        buildSurvey(request, response, surveyId);
        return mav;
    }

    /**
     * 发布问卷
     */
    @RequestMapping(value = "/my-survey-design!previewDev")
    public ModelAndView previewDev(@RequestParam("surveyId")String surveyId,
                             HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        buildSurvey(request,response,surveyId);
        ModelAndView mav = new ModelAndView("/page/content/diaowen-design/survey_preview_dev");
       // SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
        //mav.addObject("survey",survey);
        mav.addObject("surveyId",surveyId);
        return mav;
    }

    /**
     *
     * 确认发布
     */
    @RequestMapping(value = "/my-survey-design!devSurvey")
    public String devSurvey(@RequestParam("surveyId")String surveyId) throws Exception {
        SurveyDirectory survey=surveyDirectoryManager.get(surveyId);
        Date createDate=survey.getCreateDate();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/");
        try{
            String url="/survey!answerSurvey.action?surveyId="+surveyId;
            //String filePath="WEB-INF/wjHtml/"+dateFormat.format(createDate);
            String filePath="wjHtml/"+dateFormat.format(createDate);
            String fileName=surveyId+".html";
            new JspToHtml().postJspToHtml(url, filePath, fileName);
            survey.setHtmlPath(filePath+fileName);

            url="/survey!answerSurveryMobile.action?surveyId="+surveyId;
            //filePath="WEB-INF/wjHtml/"+dateFormat.format(createDate);
            filePath="wjHtml/"+dateFormat.format(createDate);
            fileName="m_"+surveyId+".html";
            new JspToHtml().postJspToHtml(url, filePath, fileName);

            survey.setSurveyState(1);
            surveyDirectoryManager.save(survey);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/my-collect/collect?surveyId="+surveyId;
    }


    private void buildSurvey(HttpServletRequest request, HttpServletResponse response, String surveyId) {
        //判断是否拥有权限
        User user = accountManager.getCurUser();
        if (user != null) {
            String userId = user.getId();
            SurveyDirectory surveyDirectory = surveyDirectoryManager.getSurveyByUser(surveyId, userId);
            if (surveyDirectory != null) {
                surveyDirectoryManager.getSurveyDetail(surveyId, surveyDirectory);
//				SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
                List<Question> questions = questionManager.findDetails(surveyId, "2");
                surveyDirectory.setQuestions(questions);
                surveyDirectory.setSurveyQuNum(questions.size());
                surveyDirectoryManager.save(surveyDirectory);
                SpringUtils.setReqAttribute("survey", surveyDirectory);
                SurveyStyle surveyStyle = surveyStyleManager.getBySurveyId(surveyId);
                SpringUtils.setReqAttribute("surveyStyle", surveyStyle);

                SpringUtils.setReqAttribute("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
            } else {
                SpringUtils.setReqAttribute("msg", "未登录或没有相应数据权限");
            }
        } else {
            SpringUtils.setReqAttribute("msg", "未登录或没有相应数据权限");
        }
    }
    
    /**
     * ajax保存问卷题目
     * @param surveyId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/my-survey-design!ajaxSave")
    @ResponseBody
    public String ajaxSave(@RequestParam("surveyId") String surveyId) throws Exception {
        HttpServletRequest request = SpringUtils.getRequest();
        HttpServletResponse response = SpringUtils.getResponse();
        String res = "false";
        String svyName = request.getParameter("svyName");
        String svyNote = request.getParameter("svyNote");
        // 收集规则
        String effective = request.getParameter("effective");
        String effectiveIp = request.getParameter("effectiveIp");
        String rule = request.getParameter("rule");
        String ruleCode = request.getParameter("ruleCode");
        String refresh = request.getParameter("refresh");
        String mailOnly = request.getParameter("mailOnly");
        String ynEndNum = request.getParameter("ynEndNum");
        String endNum = request.getParameter("endNum");
        String ynEndTime = request.getParameter("ynEndTime");
        String endTime = request.getParameter("endTime");
        String showShareSurvey = request.getParameter("showShareSurvey");
        String showAnswerDa = request.getParameter("showAnswerDa");
        // 获取surveyId
        SurveyDirectory survey = surveyDirectoryManager.getSurvey(surveyId);
        SurveyDetail surveyDetail = survey.getSurveyDetail();
        User user = accountManager.getCurUser();
        if (user != null && survey != null) {
            String userId = user.getId();
            if (userId.equals(survey.getUserId())) {

                if (svyNote != null) {
                    svyNote = URLDecoder.decode(svyNote, "utf-8");
                    surveyDetail.setSurveyNote(svyNote);
                }
                if (svyName != null && !"".equals(svyName)) {
                    svyName = URLDecoder.decode(svyName, "utf-8");
                    survey.setSurveyName(svyName);
                }

                //保存属性
                if (effective != null && !"".equals(effective)) {
                    surveyDetail.setEffective(Integer.parseInt(effective));
                }
                if (effectiveIp != null && !"".equals(effectiveIp)) {
                    surveyDetail.setEffectiveIp(Integer.parseInt(effectiveIp));
                }
                if (rule != null && !"".equals(rule)) {
                    surveyDetail.setRule(Integer.parseInt(rule));
                    surveyDetail.setRuleCode(ruleCode);
                }
                if (refresh != null && !"".equals(refresh)) {
                    surveyDetail.setRefresh(Integer.parseInt(refresh));
                }
                if (mailOnly != null && !"".equals(mailOnly)) {
                    surveyDetail.setMailOnly(Integer.parseInt(mailOnly));
                }
                if (ynEndNum != null && !"".equals(ynEndNum)) {
                    surveyDetail.setYnEndNum(Integer.parseInt(ynEndNum));
                    //surveyDetail.setEndNum(Integer.parseInt(endNum));
                    if (endNum != null && endNum.matches("\\d*")) {
                        surveyDetail.setEndNum(Integer.parseInt(endNum));
                    }
                }
                if (ynEndTime != null && !"".equals(ynEndTime)) {
                    surveyDetail.setYnEndTime(Integer.parseInt(ynEndTime));
//				    surveyDetail.setEndTime(endTime);
                    surveyDetail.setEndTime(new Date());
                }
                if (showShareSurvey != null && !"".equals(showShareSurvey)) {
                    surveyDetail.setShowShareSurvey(Integer.parseInt(showShareSurvey));
                    survey.setIsShare(Integer.parseInt(showShareSurvey));
                }
                if (showAnswerDa != null && !"".equals(showAnswerDa)) {
                    surveyDetail.setShowAnswerDa(Integer.parseInt(showAnswerDa));
                    survey.setViewAnswer(Integer.parseInt(showAnswerDa));
                }

                surveyDirectoryManager.save(survey);
                res = "true";
               return res;

            }
        }

        return "none";
    }

    @RequestMapping(value = "/my-survey-design!copySurvey")
    public String copySurvey() throws Exception {
        //引用问卷
        //id="402880e541d051000141d0f708ff0004";
        HttpServletRequest request=SpringUtils.getRequest();
        String fromBankId=request.getParameter("fromBankId");
        String surveyName=request.getParameter("surveyName");
        surveyName=URLDecoder.decode(surveyName,"utf-8");
        String tag=request.getParameter("tag");
        tag="2";
        SurveyDirectory directory=surveyDirectoryManager.createBySurvey(fromBankId,surveyName,tag);
        String surveyId = directory.getId();
        return "redirect:/design/my-survey-design.action?surveyId="+surveyId;
    }
}

