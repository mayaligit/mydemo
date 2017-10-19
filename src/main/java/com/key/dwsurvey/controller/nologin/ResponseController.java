package com.key.dwsurvey.controller.nologin;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.key.dwsurvey.entity.AnCheckbox;
import com.key.dwsurvey.entity.AnRadio;
import com.key.dwsurvey.entity.SurveyDetail;
import com.key.dwsurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.ipaddr.IPService;
import com.key.common.utils.CookieUtils;
import com.key.common.utils.HttpRequestDeviceUtils;
import com.key.common.utils.NumberUtils;
import com.key.common.utils.web.SpringUtils;
import com.key.common.QuType;
import com.key.dwsurvey.entity.SurveyAnswer;
import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.pojo.StatusEntity;
import com.key.dwsurvey.service.SurveyAnswerManager;
import com.octo.captcha.service.image.ImageCaptchaService;


/**
 * 答卷 action
 * @author KeYuan(keyuan258@gmail.com)
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
@Namespaces({ @Namespace("/") })
@InterceptorRefs({ @InterceptorRef(value = "paramsPrepareParamsStack") })
@Results({
		@Result(name = ResponseAction.RESULT_FREQUENCY, location = "/WEB-INF/page/surveydir/survey/stats/response-frequency.jsp", type = SpringUtils.DISPATCHER),
		@Result(name = CrudActionSupport.INPUT, location = "/WEB-INF/page/surveydir/survey/response/response-survey.jsp", type = SpringUtils.DISPATCHER),
		@Result(name = ResponseAction.INPUT_IFRAME, location = "/WEB-INF/page/surveydir/survey/response/response-answer-iframe.jsp", type = SpringUtils.DISPATCHER),
		@Result(name = ResponseAction.ANSWER_SUCCESS, location = "/WEB-INF/page/content/diaowen-answer/response-success.jsp", type = SpringUtils.DISPATCHER),
		@Result(name = ResponseAction.ANSWER_CODE_ERROR, location = "/wenjuan/${sid}.html?errorcode=3", type = SpringUtils.REDIRECT),
		@Result(name = ResponseAction.ANSWER_CODE_ERROR_M, location = "/survey!answerSurveryMobile.action?surveyId=${surveyId}&errorcode=3", type = SpringUtils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSWER_SUCCESS, location = "response!answerSuccess.action?sid=${sid}", type = SpringUtils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSWER_FAILURE, location = "response!answerFailure.action?surveyId=${surveyId}", type = SpringUtils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSER_ERROR, location = "response!answerError.action?surveyId=${surveyId}", type = SpringUtils.REDIRECT),
		@Result(name = ResponseAction.RELOAD_ANSER_ERROR_M, location = "response!answerErrorM.action?surveyId=${surveyId}", type = SpringUtils.REDIRECT),
		@Result(name = ResponseAction.RESPONSE_MSG, location = "/WEB-INF/page/content/diaowen-answer/response-msg.jsp", type = SpringUtils.DISPATCHER),
		@Result(name = ResponseAction.RELOAD_ANSWER_SUCCESS_M, location = "response!answerSuccessM.action?surveyId=${surveyId}", type = SpringUtils.REDIRECT),
		@Result(name = ResponseAction.RESPONSE_MOBILE, location = "response!answerMobile.action?surveyId=${surveyId}", type = SpringUtils.REDIRECT) })
@AllowedMethods({"saveMobile","answerSuccess","answerMobile","answerFailure","answerError","answerSuccessM","ajaxCheckSurvey"})*/
@Controller
public class ResponseController {
	private static final long serialVersionUID = -2289729314160067840L;

	protected static final String RESULT_FREQUENCY = "resultFrequency";
	protected final static String INPUT_IFRAME = "input_iframe";
	protected final static String ANSWER_SUCCESS = "page/content/diaowen-answer/response-success";
	protected final static String ANSWER_FAILURE = "page/content/diaowen-answer/response-failure";
	protected final static String ANSWER_ERROR = "page/content/diaowen-answer/response-input-error";
	protected final static String ANSWER_ERROR_M = "page/content/diaowen-answer/response-input-error-m";
	protected final static String ANSWER_SUCCESS_M = "page/content/diaowen-answer/response-success-m";
	protected final static String RELOAD_ANSWER_SUCCESS = "response!answerSuccess.action?sid=";
	protected final static String RELOAD_ANSWER_FAILURE = "response!answerFailure.action?surveyId=";
	//返回错误页面信息，重定向
	protected final static String RELOAD_ANSER_ERROR = "response!answerError.action?surveyId=";// 已经答过，在间隔时间内
	//protected final static String RELOAD_ANSER_ERROR_M = "reloadAnserErrorM";// 已经答过，在间隔时间内
	protected final static String ANSWER_CODE_ERROR = "answerCodeError";// 验证码不正确
	//protected final static String ANSWER_CODE_ERROR_M = "answerCodeErrorM";// 验证码不正确
	//令牌机制
	protected final static String ANSWER_INPUT_RULE = "page/content/diaowen-answer/response-input-rule";// 令牌
	protected final static String RELOAD_ANSWER_SUCCESS_M = "reloadAnswerSuccessM";//
	//protected final static String SURVEY_RESULT = "surveyResult";
	//返回提示信息，返回提示数据给页面
	protected final static String RESPONSE_MSG = "page/content/diaowen-answer/response-msg";
	protected final static String RESPONSE_MOBILE = "responseMobile";
	//封装检查问卷状态的pojo
	private StatusEntity statusEntity;
	@Autowired
	private SurveyAnswerManager surveyAnswerManager;
	@Autowired
	private SurveyDirectoryManager directoryManager;
	@Autowired
	private IPService ipService;
	@Autowired
	private AccountManager accountManager;
	// @Autowired
	// private GenericManageableCaptchaService captchaService;
	@Qualifier("imageCaptchaService")
	private ImageCaptchaService imageCaptchaService;

	/**
	 * 进入答卷页面根据请求的地址判断是响应电脑端还是移动端
	 * 1、进入问卷之前，先对问卷状态进行判断问卷状态 通过filterStatus()方法来判断
	 */
	@RequestMapping("/response.action")
	public ModelAndView rulue(@RequestParam("sid") String sid) throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		HttpServletResponse response = SpringUtils.getResponse();
		SurveyDirectory directory = directoryManager.getSurveyBySid(sid);
		if (directory != null) {
			String surveyId = directory.getId();
			//判断问卷的方法，以及返回页面
			statusEntity = filterStatus(directory,request);
			if(statusEntity!=null){
			    if(statusEntity.getStatus()==0){
			        ModelAndView mv=new ModelAndView(statusEntity.getDispatcherUrl());
	                mv.addObject("surveyName", statusEntity.getSurveyName());
	                mv.addObject("msg", statusEntity.getMsg());
	                return mv;
			    }else{
			        SpringUtils.getResponse().sendRedirect("response!answerError.action?surveyId="+surveyId);
			        return null;
			    }
			    
			}
			
			if (HttpRequestDeviceUtils.isMobileDevice(request)) {
			    //手机端入口
			    SpringUtils.getResponse().sendRedirect("/response!answerMobile.action?surveyId="+surveyId);
				
			} else {
			    //电脑端入口
				String htmlPath = directory.getHtmlPath();
				/*request.getRequestDispatcher("/" + htmlPath).forward(request,
						response);*/
				String contextPath = request.getContextPath();
				/**response.sendRedirect(contextPath+"/" + htmlPath);
				 *1.不能跳转访问web-inf下面的静态资源
				 *2.要跳转的是webapps下面的静态资源
				 */
				ModelAndView mv=new ModelAndView("redirect:"+contextPath+"/" + htmlPath);
				return mv;
			}
		}

		return null;
	}
	//判断问卷的状态 是否是公开的
	public StatusEntity filterStatus(SurveyDirectory directory,HttpServletRequest request){
	    SurveyDetail surveyDetail = directory.getSurveyDetail();
		int rule = surveyDetail.getRule();
		String surveyId = surveyDetail.getDirId();
		Integer ynEndNum = surveyDetail.getYnEndNum();
		Integer endNum = surveyDetail.getEndNum();
		Integer ynEndTime = surveyDetail.getYnEndTime();
		Date endTime = surveyDetail.getEndTime();
		Integer anserNum = directory.getAnswerNum();

//		|| (endTime!=null && ynEndTime==1 && endTime.getTime() < (new Date().getTime())
		if (directory.getSurveyQuNum() <= 0
				|| directory.getSurveyState() != 1 || (anserNum!=null && ynEndNum==1 && anserNum > endNum )) {
		    statusEntity.setSurveyName("目前该问卷已暂停收集，请稍后再试");
		    statusEntity.setMsg("目前该问卷已暂停收集，请稍后再试");
		    statusEntity.setDispatcherUrl(RESPONSE_MSG);
		    statusEntity.setStatus(0);
			return statusEntity;
		}
		if (2 == rule) {
		    statusEntity.setMsg("rule2");
			// response!answerError.action?surveyId=${surveyId}
		    statusEntity.setDispatcherUrl(RELOAD_ANSER_ERROR+surveyId);
		    statusEntity.setStatus(1);
			return statusEntity;
		} else if (3 == rule) {
			String ruleCode = request.getParameter("ruleCode");
			String surveyRuleCode = surveyDetail.getRuleCode();
			if (ruleCode == null || !ruleCode.equals(surveyRuleCode)) {
			    //page/content/diaowen-answer/response-input-rule 令牌地址
			    statusEntity.setDispatcherUrl(ANSWER_INPUT_RULE);
			    statusEntity.setStatus(0);
				return statusEntity;
			}
		}
		return statusEntity;
	}
	/**
	 * 跳转到移动端的业务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/response!answerMobile.action")
	public String answerMobile(@RequestParam("surveyId")String surveyId) throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		HttpServletResponse response = SpringUtils.getResponse();
		SurveyDirectory directory = directoryManager.getSurvey(surveyId);

		if (directory != null) {
		    
		    StatusEntity filterStatus = filterStatus(directory,request);
			if(filterStatus!=null){
				return filterStatus.getDispatcherUrl();
			}
			String htmlPath = directory.getHtmlPath();
			htmlPath = htmlPath.substring(0,htmlPath.lastIndexOf("/"));
			request.getRequestDispatcher("/" + htmlPath+"/m_"+surveyId+".html").forward(request,response);
			return null;
		}

		return null;
	}

	@RequestMapping("/response!save.action")
	public String save() throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		HttpServletResponse response = SpringUtils.getResponse();
		String formFrom = request.getParameter("form-from");
		String sid = request.getParameter("sid");
		String surveyId = request.getParameter("surveyId");
		try {
			String ipAddr = ipService.getIp(request);
			long ipNum = surveyAnswerManager.getCountByIp(surveyId, ipAddr);
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int refreshNum = surveyDetail.getRefreshNum();
			User user = accountManager.getCurUser();
			SurveyAnswer entity = new SurveyAnswer();
			if (user != null) {
				entity.setUserId(user.getId());
			}
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			Integer effective = surveyDetail.getEffective();
			if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
				return RELOAD_ANSER_ERROR + surveyId;
			}
			if (ipNum >= refreshNum) {
				String code = request.getParameter("jcaptchaInput");
				if (!imageCaptchaService.validateResponseForID(request.getSession().getId(), code)) {
					//    /wenjuan/${sid}.html?errorcode=3"
					//return ANSWER_CODE_ERROR;
					return request.getContextPath()+"/wenjuan/"+sid+".html";
				}
			}
			Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
			String addr = ipService.getCountry(ipAddr);
			String city = ipService.getCurCityByCountry(addr);
			entity.setIpAddr(ipAddr);
			entity.setAddr(addr);
			entity.setCity(city);
			entity.setSurveyId(surveyId);
			entity.setDataSource(0);
			surveyAnswerManager.saveAnswer(entity, quMaps);
			int effe = surveyDetail.getEffectiveTime();
			CookieUtils.addCookie(response, surveyId, (ipNum + 1) + "",
					effe * 60, "/");
			// exambatchManager.savePaperAnswer(entity,quMaps);
		} catch (Exception e) {
			e.printStackTrace();
			return "response!answerFailure.action?surveyId="+surveyId;
		}
		//response!answerSuccess.action?sid=${sid}
		return "redirect:/response!answerSuccess.action?sid="+sid;
	}
	/**
	 * 移动端答题
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/response!saveMobile.action")
	public String saveMobile() throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		HttpServletResponse response = SpringUtils.getResponse();

		String surveyId = request.getParameter("surveyId");
		try {
			String ipAddr = ipService.getIp(request);
			long ipNum = surveyAnswerManager.getCountByIp(surveyId, ipAddr);
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int refreshNum = surveyDetail.getRefreshNum();
			User user = accountManager.getCurUser();

			SurveyAnswer entity = new SurveyAnswer();
			if (user != null) {
				entity.setUserId(user.getId());
			}
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			Integer effective = surveyDetail.getEffective();
			if ((effective != null && effective > 1 && cookie != null) || (effectiveIp != null && effectiveIp == 1 && ipNum > 0)) {
				// response!answerErrorM.action?surveyId=${surveyId}
				//return RELOAD_ANSER_ERROR_M;
				return "response!answerErrorM.action?surveyId="+surveyId;
			}
			if (ipNum >= refreshNum) {
				String code = request.getParameter("jcaptchaInput");
				if (!imageCaptchaService.validateResponseForID(request
						.getSession().getId(), code)) {
					// /survey!answerSurveryMobile.action?surveyId=${surveyId}&errorcode=3"
					//return ANSWER_CODE_ERROR_M;
					return "/survey!answerSurveryMobile.action?surveyId="+surveyId+"&errorcode=3";
				}
			}

			Map<String, Map<String, Object>> quMaps = buildSaveSurveyMap(request);
			String addr = ipService.getCountry(ipAddr);
			String city = ipService.getCurCityByCountry(addr);
			entity.setIpAddr(ipAddr);
			entity.setAddr(addr);
			entity.setCity(city);
			entity.setSurveyId(surveyId);
			entity.setDataSource(0);
			surveyAnswerManager.saveAnswer(entity, quMaps);

			int effe = surveyDetail.getEffectiveTime();
			CookieUtils.addCookie(response, surveyId, (ipNum + 1) + "",
					effe * 60, "/");
			// exambatchManager.savePaperAnswer(entity,quMaps);
		} catch (Exception e) {
			e.printStackTrace();
			return "response!answerFailure.action?surveyId="+surveyId;
		}
		//response!answerSuccessM.action?surveyId=${surveyId}
		return "response!answerSuccessM.action?surveyId="+surveyId;
		// return SURVEY_RESULT;
	}


	public Map<String, Map<String, Object>> buildSaveSurveyMap(HttpServletRequest request) {
		Map<String, Map<String, Object>> quMaps = new HashMap<String, Map<String, Object>>();
		Map<String, Object> yesnoMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.YESNO + "_");//是非
		quMaps.put("yesnoMaps", yesnoMaps);
		Map<String, Object> radioMaps = WebUtils.getParametersStartingWith(
				request, "qu_"+QuType.RADIO + "_");//单选
		Map<String, Object> checkboxMaps = WebUtils.getParametersStartingWith(
				request, "qu_"+QuType.CHECKBOX + "_");//多选
		Map<String, Object> fillblankMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.FILLBLANK + "_");//填空
		quMaps.put("fillblankMaps", fillblankMaps);
		Map<String, Object> dfillblankMaps = WebUtils
				.getParametersStartingWith(request, "qu_"
						+ QuType.MULTIFILLBLANK + "_");//多项填空
		for (String key : dfillblankMaps.keySet()) {
			String dfillValue = dfillblankMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, dfillValue);
			dfillblankMaps.put(key, map);
		}
		quMaps.put("multifillblankMaps", dfillblankMaps);
		Map<String, Object> answerMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.ANSWER + "_");//多行填空
		quMaps.put("answerMaps", answerMaps);
		Map<String, Object> compRadioMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.COMPRADIO + "_");//复合单选
		for (String key : compRadioMaps.keySet()) {
			String enId = key;
			String quItemId = compRadioMaps.get(key).toString();
			String otherText = SpringUtils.getParameter("text_qu_"
					+ QuType.COMPRADIO + "_" + enId + "_" + quItemId);
			AnRadio anRadio = new AnRadio();
			anRadio.setQuId(enId);
			anRadio.setQuItemId(quItemId);
			anRadio.setOtherText(otherText);
			compRadioMaps.put(key, anRadio);
		}
		quMaps.put("compRadioMaps", compRadioMaps);
		Map<String, Object> compCheckboxMaps = WebUtils
				.getParametersStartingWith(request, "qu_" + QuType.COMPCHECKBOX
						+ "_");//复合多选
		for (String key : compCheckboxMaps.keySet()) {
			String dfillValue = compCheckboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, "tag_" + dfillValue);
			for (String key2 : map.keySet()) {
				String quItemId = map.get(key2).toString();
				String otherText = SpringUtils.getParameter("text_"
						+ dfillValue + quItemId);
				AnCheckbox anCheckbox = new AnCheckbox();
				anCheckbox.setQuItemId(quItemId);
				anCheckbox.setOtherText(otherText);
				map.put(key2, anCheckbox);
			}
			compCheckboxMaps.put(key, map);
		}
		quMaps.put("compCheckboxMaps", compCheckboxMaps);
		Map<String, Object> enumMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.ENUMQU + "_");//枚举
		quMaps.put("enumMaps", enumMaps);
		Map<String, Object> scoreMaps = WebUtils.getParametersStartingWith(request, "qu_" + QuType.SCORE + "_");//分数
		for (String key : scoreMaps.keySet()) {
			String tag = scoreMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			scoreMaps.put(key, map);
		}
		quMaps.put("scoreMaps", scoreMaps);
		Map<String, Object> quOrderMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.ORDERQU + "_");//排序
		for (String key : quOrderMaps.keySet()) {
			String tag = quOrderMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			quOrderMaps.put(key, map);
		}
		quMaps.put("quOrderMaps", quOrderMaps);
		Map<String, Object> chenRadioMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.CHENRADIO + "_");
		for (String key : chenRadioMaps.keySet()) {
			String tag = chenRadioMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			chenRadioMaps.put(key, map);
		}
		quMaps.put("chenRadioMaps", chenRadioMaps);
		Map<String, Object> chenCheckboxMaps = WebUtils
				.getParametersStartingWith(request, "qu_" + QuType.CHENCHECKBOX
						+ "_");
		for (String key : chenCheckboxMaps.keySet()) {
			String tag = chenCheckboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenCheckboxMaps.put(key, map);
		}
		quMaps.put("chenCheckboxMaps", chenCheckboxMaps);
		Map<String, Object> chenScoreMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.CHENSCORE + "_");
		for (String key : chenScoreMaps.keySet()) {
			String tag = chenScoreMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenScoreMaps.put(key, map);
		}
		quMaps.put("chenScoreMaps", chenScoreMaps);
		Map<String, Object> chenFbkMaps = WebUtils.getParametersStartingWith(
				request, "qu_" + QuType.CHENFBK + "_");
		for (String key : chenFbkMaps.keySet()) {
			String tag = chenFbkMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenFbkMaps.put(key, map);
		}
		quMaps.put("chenFbkMaps", chenFbkMaps);
		for (String key:radioMaps.keySet()) {
			String enId = key;
			String quItemId = radioMaps.get(key).toString();
			String otherText = SpringUtils.getParameter("text_qu_"
					+ QuType.RADIO + "_" + enId + "_" + quItemId);
			AnRadio anRadio = new AnRadio();
			anRadio.setQuId(enId);
			anRadio.setQuItemId(quItemId);
			anRadio.setOtherText(otherText);
			radioMaps.put(key, anRadio);
		}
		quMaps.put("compRadioMaps", radioMaps);
		for (String key : checkboxMaps.keySet()) {
			String dfillValue = checkboxMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, dfillValue);
			for (String key2 : map.keySet()) {
				String quItemId = map.get(key2).toString();
				String otherText = SpringUtils.getParameter("text_"
						+ dfillValue + quItemId);
				AnCheckbox anCheckbox = new AnCheckbox();
				anCheckbox.setQuItemId(quItemId);
				anCheckbox.setOtherText(otherText);
				map.put(key2, anCheckbox);
			}
			checkboxMaps.put(key, map);
		}
		quMaps.put("compCheckboxMaps", checkboxMaps);
		Map<String, Object> chenCompChenRadioMaps = WebUtils
				.getParametersStartingWith(request, "qu_"
						+ QuType.COMPCHENRADIO + "_");
		for (String key : chenCompChenRadioMaps.keySet()) {
			String tag = chenCompChenRadioMaps.get(key).toString();
			Map<String, Object> map = WebUtils.getParametersStartingWith(
					request, tag);
			for (String keyRow : map.keySet()) {
				String tagRow = map.get(keyRow).toString();
				Map<String, Object> mapRow = WebUtils
						.getParametersStartingWith(request, tagRow);
				map.put(keyRow, mapRow);
			}
			chenCompChenRadioMaps.put(key, map);
		}
		quMaps.put("compChenRadioMaps", chenCompChenRadioMaps);
		return quMaps;
	}
	@RequestMapping("/response!answerSuccess.action")
	public String answerSuccess(@RequestParam("sid")String sid) throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		SurveyDirectory directory = directoryManager.getSurveyBySid(sid);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("viewAnswer", directory.getViewAnswer());
		request.setAttribute("sid", directory.getSid());

		return "page/content/diaowen-answer/response-success";
	}

	@RequestMapping("/response!answerFailure.action")
	public String answerFailure() throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		String surveyId = request.getParameter("surveyId");
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("sId", directory.getSid());
		return ANSWER_FAILURE;
	}

	@RequestMapping("/response!answerError.action")
	public String answerError() throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		String surveyId = request.getParameter("surveyId");
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("sId", directory.getSid());
		String ipAddr = ipService.getIp(request);
		request.setAttribute("ip", ipAddr);
		return ANSWER_ERROR;
	}

	@RequestMapping("/response!answerErrorM.action")
	public String answerErrorM() throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		String surveyId = request.getParameter("surveyId");
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("surveyName", directory.getSurveyName());
		request.setAttribute("sId", directory.getSid());
		String ipAddr = ipService.getIp(request);
		request.setAttribute("ip", ipAddr);
		return ANSWER_ERROR_M;
	}

	@RequestMapping("/response!answerSuccessM.action")
	public String answerSuccessM() throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		String surveyId = request.getParameter("surveyId");
		SurveyDirectory directory = directoryManager.get(surveyId);
		request.setAttribute("directory", directory);
		return ANSWER_SUCCESS_M;
	}

	/**
	 * 异步有效性验证
	 * 
	 * @return
	 */
	public String ajaxCheckSurvey() throws Exception {
		HttpServletRequest request = SpringUtils.getRequest();
		HttpServletResponse response = SpringUtils.getResponse();
		String surveyId = request.getParameter("surveyId");
		// 0 1 2
		String ajaxResult = "0";
		try {
			SurveyDirectory directory = directoryManager.getSurvey(surveyId);
			SurveyDetail surveyDetail = directory.getSurveyDetail();
			int effective = surveyDetail.getEffective();
			int rule = surveyDetail.getRule();
			request.setAttribute("directory", directory);
			// 调查规则
			String surveyStatus = "0";
			// cookie
			Cookie cookie = CookieUtils.getCookie(request, surveyId);
			// 根据 源IP
			String ip = ipService.getIp(request);
			Long ipNum = 0L;
			if (effective > 1) {
				// 根据 cookie过滤
				if (cookie != null) {
					String cookieValue = cookie.getValue();
					if (cookieValue != null
							&& NumberUtils.isNumeric(cookieValue)) {
						ipNum = Long.parseLong(cookieValue);
					}
					surveyStatus = "1";
				} else {
					/*
					 * SurveyAnswer surveyAnswer =
					 * surveyAnswerManager.getTimeInByIp(surveyDetail, ip); if
					 * (surveyAnswer != null) { request.setAttribute("msg",
					 * 2);//表示在有效性验证，间隔时间内 surveyStatus="1"; }
					 */
				}
			}

			ipNum = surveyAnswerManager.getCountByIp(surveyId, ip);
			if (ipNum == null) {
				ipNum = 0L;
			}
			Integer effectiveIp = surveyDetail.getEffectiveIp();
			if (effectiveIp != null && effectiveIp == 1 && ipNum > 0) {
				surveyStatus = "2";
			}

			String isCheckCode = "0";
			// 启用验证码
			int refreshNum = surveyDetail.getRefreshNum();
			if (ipNum >= refreshNum) {
				isCheckCode = "3";
			}
			ajaxResult = "{surveyStatus:\"" + surveyStatus
					+ "\",isCheckCode:\"" + isCheckCode + "\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*response.getWriter().write(ajaxResult);*/
		return ajaxResult;
	}


}
