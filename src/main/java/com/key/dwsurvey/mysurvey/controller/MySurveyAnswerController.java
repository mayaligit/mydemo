package com.key.dwsurvey.mysurvey.controller;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.savesurvey.entity.Question;
import com.key.dwsurvey.mysurvey.entity.SurveyAnswer;
import com.key.dwsurvey.mysurvey.entity.SurveyDirectory;
import com.key.dwsurvey.mysurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by issuser on 2017/9/27.
 */
@Controller
@RequestMapping("/da")
public class MySurveyAnswerController {

    @Autowired
    private SurveyAnswerManager surveyAnswerManager;
    @Autowired
    private SurveyDirectoryManager directoryManager;
    @Autowired
    private AccountManager accountManager;
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;

    /**
     * 原始数据页面
     * @param surveyId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/my-survey-answer")
    public String execute(String surveyId, Model model) throws Exception {
        HttpServletRequest request=SpringUtils.getRequest();
        Page<SurveyAnswer> page = new Page<SurveyAnswer>();
        User user=accountManager.getCurUser();
        if(user!=null){
            SurveyDirectory survey=directoryManager.getSurveyByUser(surveyId, user.getId());
            if(survey!=null){
                page=surveyAnswerManager.answerPage(page, surveyId);
                request.setAttribute("survey", survey);
                model.addAttribute("survey",survey);
            }
        }
        model.addAttribute("page",page);
        model.addAttribute("surveyId",surveyId);
        return "page/content/diaowen-da/survey-answer-data";
    }

    /**
     * 下载数据
     * @param surveyId
     * @return
     * @throws Exception
     */
    @RequestMapping("/my-survey-answer!exportXLS")
    @ResponseBody
    public String exportXLS(@RequestParam("surveyId") String surveyId) throws Exception{
        HttpServletRequest request= SpringUtils.getRequest();
        HttpServletResponse response=SpringUtils.getResponse();
        try{
            String savePath = request.getSession().getServletContext().getRealPath("/");
            User user=accountManager.getCurUser();
            if(user!=null){
                SurveyDirectory survey=directoryManager.getSurveyByUser(surveyId, user.getId());
                if(survey!=null){
                    savePath=surveyAnswerManager.exportXLS(surveyId,savePath);
                    response.sendRedirect(request.getContextPath()+savePath);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看原始数据
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/my-survey-answer!responseAnswer")
    public String responseAnswer(Model model) throws Exception {
        HttpServletRequest request=SpringUtils.getRequest();
        String answerId=request.getParameter("answerId");
        List<Question> questions = new ArrayList<Question>();
        try {
            SurveyDirectory directory=new SurveyDirectory();
            if (answerId != null) {
                SurveyAnswer answer = surveyAnswerManager.getModel(answerId);
                questions = surveyAnswerManager.findAnswerDetail(answer);
                User user=accountManager.getCurUser();
                if(answer.getSurveyId() != null && user!=null){
                    SurveyDirectory survey=directoryManager.getSurveyByUser(answer.getSurveyId(), user.getId());
                    if(survey!=null){
                        request.setAttribute("questions", questions);
                        request.setAttribute("directory", directory);
                        model.addAttribute("questions", questions);
                        model.addAttribute("directory", directory);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "page/content/diaowen-answer/response-answer";
    }

    @RequestMapping("/my-survey-answer!delete")
    @ResponseBody
    public String delete() throws Exception {
        HttpServletRequest request=SpringUtils.getRequest();
        HttpServletResponse response=SpringUtils.getResponse();
        String result="error";
        try{
            String answerId=request.getParameter("answerId");
            if (answerId != null) {
                SurveyAnswer answer = surveyAnswerManager.getModel(answerId);
                User user=accountManager.getCurUser();
                if(answer.getSurveyId() != null && user!=null){
                    SurveyDirectory survey=directoryManager.getSurveyByUser(answer.getSurveyId(), user.getId());
                    if(survey!=null){
                        surveyAnswerManager.delete(answer);
                        Integer answerNum = survey.getAnswerNum();
                        if(answerNum!=null && answerNum>=1){
                            survey.setAnswerNum(answerNum-1);
                            directoryManager.save(survey);
                        }
                    }
                }
            }
            result="true";
        }catch(Exception e){
            e.printStackTrace();
        }
        //response.getWriter().write(result);
        return result;
    }
}
