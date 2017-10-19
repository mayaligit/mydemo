package com.key.dwsurvey.systemuser.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.plugs.page.Page;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.mysurvey.entity.SurveyDirectory;
import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.systemuser.service.UserManager;

/**
 * 用户中心配置信息
 * 功能模块：
 * 提供用户增删改查，以及分页操作 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sy/user")
public class UserAdminController {
    
    
	protected final static String USER_ROLE="userRole";
	@Autowired
	private UserManager userManager;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	SurveyDirectory surveyDirectory=new SurveyDirectory();
	
	//######TODO 分页查询功能
	/**
	 * 返回用户列表 list用户表单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/user-admin.action")
	public ModelAndView list(Page page,User user) throws Exception {
		ModelAndView mav = new ModelAndView("page/content/diaowen-useradmin/list");
		try{
			HttpServletRequest request=SpringUtils.getRequest();
			String surveyState = request.getParameter("status");
			String loginName = request.getParameter("loginName");
			String pageParam = request.getParameter("page.pageNo");
			if(pageParam !=null){
			    int pageNo=Integer.parseInt(pageParam);
			    page.setPageNo(pageNo);
			}
			if(surveyState==null||"".equals(surveyState)){
				user.setStatus(null);
			}else{
			    if("2".equals(surveyState)){
			        int state =1;
			    }
			    user.setStatus(Integer.parseInt(surveyState));
			}
			user.setLoginName(loginName);
			page=userManager.findPage(page,user);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//获取当前登录用户的id
		Object principal = SecurityUtils.getSubject().getPrincipal();
		mav.addObject("id", "1");
		mav.addObject("page", page);
		return mav;
	}
	
	//######TODO 添加用户功能
	/**
	 * 添加页面 
	 * @return liangyu
	 * @throws Exception
	 */
	@RequestMapping("/user-admin!input.action")
	public ModelAndView input() throws Exception {
		ModelAndView mav = new ModelAndView("page/content/diaowen-useradmin/input");
		HttpServletRequest request= SpringUtils.getRequest();
		String id = (String) request.getAttribute("id");
		if(id !=null){
		    User user = userManager.get(id);
		    mav.addObject("user",user);
		}
		return mav;
	}
	
	//######TODO 修改用户功能
	/**
	 * 修改用户页面
	 */
	@RequestMapping("/updateUser")
    public ModelAndView updateUser(String id) throws Exception {
        ModelAndView mv = new ModelAndView("page/content/diaowen-useradmin/input");
        if(id !=null){
            User user = userManager.get(id);
            mv.addObject("user",user);
        }
        return mv;
    }
	
	//######TODO 保存用户功能
	/**
     * 保存修改用户
     * @param user
     * @return redirect 
     * @throws Exception
     */
	@RequestMapping("/nosm/user-admin!save.action")
	@ResponseBody
	public ModelAndView save(User user) throws Exception {
		ModelAndView mav = new ModelAndView("redirect:/sy/user/user-admin.action");
		HttpServletRequest request= SpringUtils.getRequest();
		HttpSession session = request.getSession();
		User curUser = accountManager.getCurUser();
		String loginName = curUser.getLoginName();
		String[] loginNames=new String[20];
		loginNames[0]=loginName;
		userManager.adminSave(user,loginNames);
		return mav;
	}

	
	//######TODO 账号禁用功能
	/**
     * 账号禁用
     */
    @RequestMapping("/nosm/user-admin!delete.action{id}")
    @ResponseBody
    public String delete(String id) throws Exception {
        String result="false";
        try{
            userManager.disUser(id);
            result="true";
        }catch (Exception e) {
        }
        return result;
    }
    
    //######TODO 异步验证登录名是否可用
    /**
     * 异步校验登录名
     * 检验登录名是否可用
     * @param email
     * @param id
     * @return json 
     * @throws Exception
     */
	@RequestMapping("/user-admin!checkLoginNamelUn.action")
	@ResponseBody
	public String checkLoginNamelUn() throws Exception{
		HttpServletRequest request= SpringUtils.getRequest();
		String loginName=request.getParameter("loginName");
		String id=request.getParameter("id");
		User user=userManager.findNameUn(id,loginName);
		String result="true";
		if(user!=null){
			result="false";
		}
		return result;
	}
	
	//######TODO 账号邮箱是否可用
	/**
	 * 检验email是否可用
	 * @param email
	 * @param id
	 * @return json 
	 * @throws Exception
	 */
	@RequestMapping("/user-admin!checkEmailUn.action")
	@ResponseBody
	public String checkEmailUn() throws Exception{
		HttpServletRequest request= SpringUtils.getRequest();
		HttpServletResponse response= SpringUtils.getResponse();
		String email=request.getParameter("email");
		String id=request.getParameter("id");
		User user=userManager.findEmailUn(id,email);
		String result="true";
		if(user!=null){
			result="false";
		}
		return result;
	}
	
}
