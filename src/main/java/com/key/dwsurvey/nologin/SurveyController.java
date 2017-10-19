package com.key.dwsurvey.nologin;

import com.key.common.plugs.ipaddr.IPService;
import com.key.common.utils.DiaowenProperty;
import com.key.common.utils.twodimension.TwoDimensionCode;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.mysurvey.entity.SurveyDirectory;
import com.key.dwsurvey.mysurvey.entity.SurveyStyle;
import com.key.dwsurvey.savesurvey.service.QuestionManager;
import com.key.dwsurvey.mysurvey.service.SurveyAnswerManager;
import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.mysurvey.service.SurveyStyleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 问卷 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
/*@Namespace("/")
@InterceptorRefs({ @InterceptorRef("paramsPrepareParamsStack")})
@Results({
	@Result(name= SurveyController.INDEXJSP,location="/index.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name= SurveyController.ANSERSURVEY,location="/WEB-INF/page/content/diaowen-design/answer-survey.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name= SurveyController.ANSERSURVEY_MOBILE,location="/WEB-INF/page/content/diaowen-design/answer-survey-mobile.jsp",type=Struts2Utils.DISPATCHER),
	@Result(name = ResponseAction.RESPONSE_MSG, location = "/WEB-INF/page/content/diaowen-answer/response-msg.jsp", type = Struts2Utils.DISPATCHER)
})
@AllowedMethods({"answerSurvey","answerSurveryMobile","surveyModel","answerTD","ajaxCheckSurvey"})*/

@Controller
@RequestMapping("/")
public class SurveyController {
	
	protected final static String INDEXJSP="indexJsp";
	protected final static String ANSERSURVEY="answerSurvey";
	protected final static String ANSERSURVEY_MOBILE="answerSurveyMobile";
	protected final static String SURVEYMODEL="surveyModel";
	protected final static String ANSWER_INPUT_ERROR = "answerInputError";//已经答过，在间隔时间内
	protected final static String ANSWER_INPUT_RULE = "answer_input_rule";//令牌
	
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private QuestionManager questionManager;
	@Autowired
	private SurveyStyleManager surveyStyleManager;
	@Autowired
	private IPService ipService;
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;


	//外部回答公共访问路径--静态访问
	/*@Override
	public String execute() throws Exception {
		HttpServletRequest request=Struts2Utils.getRequest();
		HttpServletResponse response=Struts2Utils.getResponse();
		String htmlPath="http://wj.diaowen.net/test";
		//request.getRequestDispatcher(htmlPath).forward(request, response);
		response.sendRedirect(htmlPath);
		return "none";
	}*/

	//问卷的动态访问方式
	@RequestMapping(value = "/survey!answerSurvey")
	public String answerSurvey(@RequestParam("surveyId")String surveyId) throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
		buildSurvey(survey,surveyId);
		return "/page/content/diaowen-design/answer-survey";
	}

	//问卷动态访问-移动端 http://localhost:8080/survey/answerSurveryMobile.action
	@RequestMapping(value = "/survey!answerSurveryMobile")
	public String answerSurveryMobile(@RequestParam("surveyId")String surveyId) throws Exception {
	    HttpServletRequest request = SpringUtils.getRequest();
		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
		buildSurvey(survey,surveyId);
	    return "/page/content/diaowen-design/answer-survey-mobile";
	}
	
	private void buildSurvey(SurveyDirectory survey,String surveyId) {
		if (survey==null)
		survey=surveyDirectoryManager.getSurvey(surveyId);
		survey.setQuestions(questionManager.findDetails(surveyId, "2"));
		SpringUtils.setReqAttribute("survey", survey);
		SurveyStyle surveyStyle=surveyStyleManager.getBySurveyId(surveyId);
		SpringUtils.setReqAttribute("surveyStyle", surveyStyle);
		SpringUtils.setReqAttribute("prevHost", DiaowenProperty.STORAGE_URL_PREFIX);
	}

	//回答问卷的二维码
	@RequestMapping(value = "/survey!answerTD")
	@ResponseBody
	public String answerTD(@RequestParam("surveyId")String surveyId) throws Exception{
	    	HttpServletRequest request=SpringUtils.getRequest();
	    	HttpServletResponse response=SpringUtils.getResponse();
	    	String down=request.getParameter("down");
			String baseUrl = "";
			baseUrl = request.getScheme() +"://" + request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
					+ request.getContextPath();

	    	String encoderContent=baseUrl+"/response/answerMobile.action?surveyId="+surveyId;
	    	ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();  
	    	BufferedImage twoDimensionImg = new TwoDimensionCode().qRCodeCommon(encoderContent, "jpg", 7);

			ImageIO.write(twoDimensionImg, "jpg", jpegOutputStream);

	        if(down==null){
		    	response.setHeader("Cache-Control", "no-store");
		        response.setHeader("Pragma", "no-cache");
		        response.setDateHeader("Expires", 0);
		        response.setContentType("image/jpeg");
		        ServletOutputStream responseOutputStream = response.getOutputStream();
		       responseOutputStream.write(jpegOutputStream.toByteArray());
		       responseOutputStream.flush();
		       responseOutputStream.close();
	        }else{
        	   response.addHeader("Content-Disposition", "attachment;filename=" + new String(("diaowen_"+surveyId+".jpg").getBytes()));
        	   byte[] bys = jpegOutputStream.toByteArray();
    		   response.addHeader("Content-Length", "" + bys.length);
    		   ServletOutputStream responseOutputStream = response.getOutputStream();
    		   response.setContentType("application/octet-stream");	
    		   responseOutputStream.write(bys);
    		   responseOutputStream.flush();
    		   responseOutputStream.close();
	        }

	        return null;
	}
}
