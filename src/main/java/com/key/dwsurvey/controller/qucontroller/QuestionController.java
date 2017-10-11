package com.key.dwsurvey.controller.qucontroller;

import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.service.QuestionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 题目 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	private QuestionManager questionManager;

	/**
	 * ajax删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ajaxDelete")
	@ResponseBody
	public String ajaxDelete() throws Exception {
		
		HttpServletRequest request= SpringUtils.getRequest();
		HttpServletResponse response=SpringUtils.getResponse();
		String responseStr="";
		try{
			String delQuId=request.getParameter("quId");
			questionManager.delete(delQuId);	
			responseStr="true";
		}catch (Exception e) {
			responseStr="false";
		}
		response.getWriter().write(responseStr);
		return null;
	}
	
}
