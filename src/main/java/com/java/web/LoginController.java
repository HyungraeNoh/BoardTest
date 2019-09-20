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
import java.util.Iterator;
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
	
	@RequestMapping("/loginkakao")
	public void login(HttpServletRequest req, HttpServletResponse res) {
		try {
			String url2 = "https://accounts.kakao.com/login?continue=";
			String url = "https://kauth.kakao.com/oauth/authorize";
			url += "?client_id=24a9cf2f6258f5b091fcf38880647a8e&redirect_uri=";
			url += URLEncoder.encode("http://gdj16.gudi.kr:20010/KakaoBack", "UTF-8");
			url += "&response_type=code";
			url2+=URLEncoder.encode(url, "UTF-8");
//			System.out.println(url);
//			res.sendRedirect(url);
			res.sendRedirect(url2);
		} catch (UnsupportedEncodingException e)  {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	String code;
	@RequestMapping("/KakaoBack")
	public String KakaoBack(HttpServletRequest req, HttpServletResponse res,  HttpSession hs) {
		System.out.println("KakaoBack");
		code = req.getParameter("code");
		System.out.println(code);
		try {
			
			String url="https://kauth.kakao.com/oauth/token";
			url +="?client_id=24a9cf2f6258f5b091fcf38880647a8e&redirect_uri="; 
			url +=URLEncoder.encode("http://gdj16.gudi.kr:20010/KakaoBack","UTF-8");
			url +="&code="+code; 
			url +="&grant_type=authorization_code";
			System.out.println(url);
			
			HashMap<String, Object> resultMap = HttpUtil.getUrl(url);
			
			String userUrl = "https://kapi.kakao.com/v2/user/me";
			userUrl += "?access_token=" + resultMap.get("access_token");
			
			System.out.println(userUrl);
			resultMap=HttpUtil.getUrl(userUrl);
			System.out.println(resultMap);
			JSONObject jObject = JSONObject.fromObject(resultMap);
			
			HashMap<String, Object> userResult = new HashMap<String, Object>();
			String temp=(String) jObject.get("properties");
			JSONObject tmp=JSONObject.fromObject(temp);
			
			userResult.put("id", (String)jObject.get("id"));
			userResult.put("nickname", tmp.get("nickname"));
			userResult.put("profile_image", tmp.get("profile_image"));
			userResult.put("thumbnail_image", tmp.get("thumbnail_image"));
			
			System.out.println(userResult.get("profile_image"));
			String id=(String) userResult.get("id");
				/* if(session.selectOne("login.select",userResult.get("id")) == "0") */
			LoginBean result = session.selectOne("login.selectLogin",userResult.get("id"));
				if(userResult.get("id").equals(result.getId()))
					System.out.println("입력x");
				else session.insert("login.insert",userResult);
			
			LoginBean resultList = session.selectOne("login.selectLogin",userResult.get("id"));
			req.setAttribute("login", jObject);
			req.setAttribute("result", resultList);
			
			hs.setAttribute("id", id);
			
	//		HttpSession hs=req.getSession();
	//		hs.setAttribute("login", "true");
	
//			res.sendRedirect("/");
//			RequestDispatcher rd=req.getRequestDispatcher("/");
//			rd.forward(req, res);
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
	////로그아웃
	@RequestMapping("/logout")
	public String logout(HttpSession hs, HttpServletRequest req, HttpServletResponse res) {
		try {
			System.out.println("nickname: "+hs.getAttribute("nickname"));
			System.out.println("nickname: "+hs.getAttribute("access_token"));
			String access_token =(String) hs.getAttribute("access_token");
			String url = "https://kapi.kakao.com/v1/user/logout";
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer "+access_token);
			int resCode = conn.getResponseCode();
			System.out.println(resCode);
			if(resCode == 200) {
				InputStream input = conn.getInputStream();
				InputStreamReader inputReader = new InputStreamReader(input, "utf-8");
				BufferedReader br = new BufferedReader(inputReader);
				String line = "";
				String result = "";
				while((line = br.readLine()) != null) {
					result += line;
				}
				JSONObject jObject = JSONObject.fromObject(result);
				Iterator<?> iterator = jObject.keys();
				while(iterator.hasNext()) {
					String key = iterator.next().toString();
					String value = jObject.getString(key);
					resultMap.put(key, value);
				}				
				input.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		hs.invalidate();
		return "redirect:/loginkakao";
	}
}
