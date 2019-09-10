package com.java.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;

@Controller
public class LoginController {
	/******************************************************************
	 * 카카오 로그인
	 * 1) 카카오 사이트 권한 요청 > code
	 * 2) 카카오 사용자 토큰 발행 요청 > token
	 * 3) 카카오 사용자 정보 요청 > 해당 token 사용자 정보 받기
	 ******************************************************************/
	@Autowired
	SqlSession session;
	@RequestMapping("/login")
	public void login(HttpServletRequest req, HttpServletResponse res) {
		try {
			String url = "https://kauth.kakao.com/oauth/authorize";
			url +="?client_id=24a9cf2f6258f5b091fcf38880647a8e&redirect_uri="; //rest api
			url +=URLEncoder.encode("http://gdj16.gudi.kr:20010/KakaoBack","UTF-8"); //uri
			url +="&response_type=code"; //token 
			System.out.println(url);
			res.sendRedirect(url);
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/KakaoBack")
	public String KakaoBack(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("KakaoBack");
		String code = req.getParameter("code");
		System.out.println(code);
		try {
		//토큰 요청
		String url="https://kauth.kakao.com/oauth/token";
		url +="?client_id=24a9cf2f6258f5b091fcf38880647a8e&redirect_uri="; //rest api
		url +=URLEncoder.encode("http://gdj16.gudi.kr:20010/KakaoBack","UTF-8"); //uri
		url +="&code="+code; //token 
		url +="&grant_type=authorization_code";
		System.out.println(url);
		
		HashMap<String, Object> resultMap = HttpUtil.getUrl(url);
		
		String userUrl = "https://kapi.kakao.com/v2/user/me";
		userUrl += "?access_token=" + resultMap.get("access_token");
		
//		userUrl = "https://kapi.kakao.com/v1/user/logout";
//		userUrl += "?access_token=" + resultMap.get("access_token");
		
		System.out.println(userUrl);
		resultMap=HttpUtil.getUrl(userUrl);
		System.out.println(resultMap);
		JSONObject jObject = JSONObject.fromObject(resultMap);
		System.out.println("1");
		
		HashMap<String, Object> userResult = new HashMap<String, Object>();
		String temp=(String) jObject.get("properties");
		JSONObject tmp=JSONObject.fromObject(temp);
		
		userResult.put("id", jObject.get("id"));
		userResult.put("nickname", tmp.get("nickname"));
		userResult.put("profile_image", tmp.get("profile_image"));
		userResult.put("thumbnail_image", tmp.get("thumbnail_image"));
		
//		userResult.put("id",jObject.get("id"));
//		JSONObject userObject = JSONObject.fromObject(userResult);
//		System.out.println(userObject.get("thumbnail_image"));
		String id=(String) userResult.get("id");
		if(session.selectOne("login.select",userResult.get("id")) == "0")
		session.insert("login.insert",userResult);
		
		LoginBean resultList = session.selectOne("login.selectLogin",userResult.get("id"));
		req.setAttribute("login", jObject);
		req.setAttribute("result", resultList);
		

//		res.sendRedirect("/");
//		RequestDispatcher rd=req.getRequestDispatcher("/");
//		rd.forward(req, res);
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		} 
//		catch (ServletException e) {
//			e.printStackTrace();
//		}
		return "redirect:/";
	}
	@RequestMapping("/logout")
	public void logout(HttpServletRequest req, HttpServletResponse res) {
		try {
//			String url="https://kauth.kakao.com/oauth/token";
//			url +="?client_id=ed94698d2dd2bbca37dbb1ad2cd5ae87&redirect_uri="; //rest api
//			url +=URLEncoder.encode("http://gdj16.gudi.kr:20003/KakaoBack","UTF-8"); //uri
//			url +="&code="+code; //token 
//			url +="&grant_type=authorization_code";
//			System.out.println(url);
//			
//			HashMap<String, Object> resultMap = HttpUtil.getUrl(url);
			
//			String userUrl = "https://kapi.kakao.com/v2/user/me";
//			userUrl += "?access_token=" + resultMap.get("access_token");
//			
			String logoutUrl = "https://kapi.kakao.com/v1/user/logout";
//			logoutUrl += "?access_token=" + resultMap.get("access_token");
//			System.out.println(userUrl);
			
			HashMap<String, Object> logoutresult=HttpUtil.getUrl(logoutUrl);
			System.out.println(logoutresult);
			res.sendRedirect(logoutUrl);
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
		
}
