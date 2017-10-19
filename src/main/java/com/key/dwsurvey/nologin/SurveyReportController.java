package com.key.dwsurvey.nologin;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.savesurvey.entity.Question;
import com.key.dwsurvey.mysurvey.entity.SurveyDirectory;
import com.key.dwsurvey.mysurvey.entity.SurveyStats;
import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.mysurvey.service.SurveyStatsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by issuser on 2017/9/27.
 */
@Controller
@RequestMapping("/da")
public class SurveyReportController {

    @Autowired
    private SurveyDirectoryManager directoryManager;
    @Autowired
    private SurveyStatsManager surveyStatsManager;
    @Autowired
    private AccountManager accountManager;

    private SurveyStats surveyStats = new SurveyStats();
    private SurveyDirectory directory = new SurveyDirectory();

    /**
     *数据分析
     * @param surveyId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/survey-report!defaultReport")
    public String defaultReport(@RequestParam("surveyId")String surveyId, Model model) throws Exception {
        // 得到频数分析数据
        HttpServletRequest request=SpringUtils.getRequest();
        User user = accountManager.getCurUser();
        SurveyDirectory directory = new SurveyDirectory();
        SurveyStats surveyStats = new SurveyStats();
        if(user!=null){
            directory=directoryManager.getSurveyByUser(surveyId, user.getId());
            SurveyDirectory survey=directoryManager.getSurveyByUser(surveyId, user.getId());
            if(survey!=null){
                request.setAttribute("survey", survey);
            }
            if(directory!=null){
                List<Question> questions = surveyStatsManager.findFrequency(directory);
                surveyStats.setQuestions(questions);
                model.addAttribute("directory",directory);
                model.addAttribute("surveyStats",surveyStats);
                model.addAttribute("surveyId",surveyId);
                model.addAttribute("survey",survey);
            }
        }
        return "page/content/diaowen-da/default-report";
    }

    /**
     * 取得某一题的统计数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/survey-report!chartData")
    @ResponseBody
    public String chartData(@RequestParam("quId") String quId) throws Exception {
        HttpServletResponse response= SpringUtils.getResponse();
        //取柱状图数据
        User user = accountManager.getCurUser();
        if(user!=null){
            String questionId=SpringUtils.getParameter("quId");
            Question question=new Question();
            question.setId(questionId);
            surveyStatsManager.questionDateCross(question);
            /*response.getWriter().write(question.getStatJson());*/
            return question.getStatJson();
        }
        return null;
    }

    @RequestMapping("/survey-report!lineChart")
    @ResponseBody
    public String lineChart(@RequestParam("surveyId") String surveyId) throws Exception {
        User user = accountManager.getCurUser();
        if(user!=null){
            directory=directoryManager.getSurveyByUser(surveyId, user.getId());
            if(directory!=null){
                List<Question> questions = surveyStatsManager.dataChart1s(directory);
                surveyStats.setQuestions(questions);
            }
        }
        return "page/content/diaowen-da/line-chart";
    }

    @RequestMapping("/survey-report!pieChart")
    @ResponseBody
    public String pieChart(@RequestParam("surveyId") String surveyId) throws Exception {
        User user = accountManager.getCurUser();
        if(user!=null){
            directory=directoryManager.getSurveyByUser(surveyId, user.getId());
            if(directory!=null){
                List<Question> questions = surveyStatsManager.dataChart1s(directory);
                surveyStats.setQuestions(questions);
            }
        }
        return "page/content/diaowen-da/pie-chart";
    }
}
