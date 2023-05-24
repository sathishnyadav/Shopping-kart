package com.testyantraglobal.shopping.shoppingkarttyapi.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testyantraglobal.shopping.shoppingkarttyapi.dao.UserDao;
import com.testyantraglobal.shopping.shoppingkarttyapi.dto.User;

import net.bytebuddy.utility.RandomString;
import static com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants.SIZE;
import static com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants.RESET_URL;
import static com.testyantraglobal.shopping.shoppingkarttyapi.util.ApplicationConstants.FORGOT_URL;

@Service
public class ResetPasswordLinkService {

	@Autowired
	private UserDao dao;

	public String getResetPasswordlink(HttpServletRequest request, User user) {
		String token = RandomString.make(SIZE);
		user.setToken(token);
		dao.saveUser(user);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String reset_link = url + RESET_URL + token;
		return reset_link;
	}

	public String getVerifyAccountlink(HttpServletRequest request, User user) {
		String token = RandomString.make(SIZE);
		user.setToken(token);
		dao.saveUser(user);
		String siteurl = request.getRequestURL().toString();
		String url = siteurl.replace(request.getServletPath(), "");
		String verify_link = url + FORGOT_URL + token;
		return verify_link;
	}
}
