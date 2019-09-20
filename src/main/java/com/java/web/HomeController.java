package com.java.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.ListBean;

@Controller
public class HomeController {
	
	@Autowired
	SqlSession session;
	
	@RequestMapping("/db")
	public String db() {
		String result = session.selectOne("test.select");
		System.out.println(result);
		return "home";
	}
	
	@RequestMapping("/")
	public String select(HttpServletRequest req, Model m) {
		if(req.getParameter("abc") != null) {
//			System.out.println("예외 1");
//			int no = Integer.parseInt(req.getParameter("abc"));
			String no = req.getParameter("abc");
//			System.out.println("예외 2");
			List<ListBean> list = session.selectList("board.tt", no);

			/*
			 * System.out.println(list.get(0).getNo());
			 * System.out.println(list.get(0).getTitle());
			 * System.out.println(list.get(0).getTxt());
			 */
			m.addAttribute("list", list);
			return "write2";
		}
		List<ListBean> list = session.selectList("board.select");
		System.out.println(list.size());
		req.setAttribute("list", list);
		return "home";
	}
	
	@RequestMapping("/insert")
	public String insert(HttpServletRequest req) {
		session.insert("board.insert", new ListBean("",  req.getParameter("title"), req.getParameter("txt")));
		return "redirect:/";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest req) {
		System.out.println("No :" +req.getParameter("no"));
		System.out.println("title :" +req.getParameter("title"));
		System.out.println("txt :" +req.getParameter("txt"));
		session.update("board.update", new ListBean(req.getParameter("no"), req.getParameter("title"), req.getParameter("txt")));
		System.out.println("test");
		return "redirect:/";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest req) {
		session.update("board.delete", new ListBean(req.getParameter("no"), req.getParameter("title"), req.getParameter("txt")));
		
		return "redirect:/";
	}
	
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login() {
//		return "home";
//	}
	
//	@RequestMapping(value="/login")
//	public String login(HttpSession hs, HttpServletRequest req) {
//		String id = hs.getId();
//		req.setAttribute("id", id);
//		return "redirect:/";
//	}
	
//	// 고유한 파일명 생성 (UUID)
//	String randomFileName = UUID.randomUUID().toString();
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public String file(@RequestParam("file") MultipartFile[] files, HttpServletRequest req) {
//		try {
//			int[] statusList = new int[files.length];
//			for(int i = 0; i < files.length; i++) {
//				MultipartFile file = files[i];
//				System.out.println(file.getOriginalFilename());
//				// 원본 파일명 생성 (test.txt)
//				String originalFileName = file.getOriginalFilename();
//				// 파일 확장자 생성 (test.txt -> .txt)
//				String ext = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
//				
//				
//				// 프로젝트 경로 받기
//				System.out.println(req.getContextPath());  // 화면에서만 사용 하면 좋다
//				System.out.println(req.getSession().getServletContext().getRealPath("/")); // 파일 처리 시 좋다.
//				
//				// 데이터 가져오기
//				byte[] data = file.getBytes();
//				// 저장 경로 + 파일명 정의
//				String realPath = req.getSession().getServletContext().getRealPath("/");
//				
//				String path = "D:\\IDE\\workspace\\Resource\\"; // 작성자 / 메뉴 / 날짜 / 시간 / 파일명
//				// 파일 객체 생성
//				File f = new File(path);
//				// 파일 생성 경로에 폴더가 없으면 생성 처리
//				if(!f.isDirectory()) {
//					f.mkdirs();
//				}
//				// 출력 객체 생성 + 파일 객체 넣기   (저장경로 + uuid + 확장자)
//				OutputStream os = new FileOutputStream(new File(path + randomFileName + ext));
//				// 가져온 데이터 출력 객체에 넣기
//				os.write(data);
//				// 출력 객체 종료
//				os.close();
//				
//				FilesBean fb = new FilesBean();
//				fb.setDelYn("N");
//				fb.setFileName(originalFileName);
//				fb.setFileURL(randomFileName + ext);
//				int status = session.insert("test.insert", fb);
//				statusList[i] = status;
//				System.out.println(i + ") " + originalFileName + " : " + status);
//				
//				
//				
////				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
////				InputStream is = file.getInputStream();
////				InputStreamReader isr = new InputStreamReader(is);
////				BufferedReader br = new BufferedReader(isr);
////				StringBuilder sb = new StringBuilder();
////				String txt = "";
////				String result = "";
////				while((txt = br.readLine()) != null) result += txt + "\n";
////				System.out.println(result);
//			}
//			
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "home";
//	}
	
//	@RequestMapping("/download/{originalName}/{ext}")
//	public void download(HttpServletRequest req, HttpServletResponse res, 
//			             @PathVariable("originalName") String originalFileName,
//			             @PathVariable("ext") String ext) {
//		String path = "D:\\IDE\\workspace\\Resource\\";
//		String fileName = randomFileName + ext;
////		String originalFileName = "test.txt";
//		try {
//			InputStream input = new FileInputStream(path + fileName);
//			OutputStream output = res.getOutputStream();
//			IOUtils.copy(input, output);		
//			res.setHeader("Content-Disposition", "attachment; filename=\"" + (originalFileName +"."+ext) + "\"");
//			input.close();
//			output.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
	@RequestMapping("/write")
	public String write(HttpServletRequest req) {
		
		return "write";
	}
	
}
//
//package kr.gudi.web;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import net.sf.json.JSONObject;
//
//@Controller
//public class LoginController {
//	
//	@Autowired
//	SqlSession session;
//	
//	/*************************************************************
//	 * 카카오 로그인
//	 * 1) 카카오 사이트 권한 요청 > code
//	 * 2) 카카오 사용자 토큰 발행 요청 > token
//	 * 3) 카카오 사용자 정보 요청 > 해당 token 사용자 정보 받기
//	 *************************************************************/
//	@RequestMapping("/")
//	public String home() {
//		return "login";
//	}
//	
//	
//	@RequestMapping("/login")
//	public void login(HttpServletRequest req, HttpServletResponse res) {		
//		try {
//			String url2 = "https://accounts.kakao.com/login?continue=";
//			String url = "https://kauth.kakao.com/oauth/authorize";
//			url += "?client_id=6bdb6d1cf2937cc33669a6cc969f40ca&redirect_uri=";
//			url += URLEncoder.encode("http://gdj16.gudi.kr:20009/Avocado", "UTF-8");
//			url += "&response_type=code";
//			url2+=URLEncoder.encode(url, "UTF-8");
////			System.out.println(url);
////			res.sendRedirect(url);
//			res.sendRedirect(url2);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@RequestMapping("/Avocado")
//	public String Avocado(HttpServletRequest req, HttpServletResponse res) {		
//		try {
//			HttpSession httpsession = req.getSession();
//			System.out.println("Avocado");
//			String code = req.getParameter("code");
//			System.out.println(code);
//			String url = "https://kauth.kakao.com/oauth/token";
//			url += "?client_id=6bdb6d1cf2937cc33669a6cc969f40ca&redirect_uri=";
//			url += URLEncoder.encode("http://gdj16.gudi.kr:20009/Avocado", "UTF-8");
//			url += "&code=" + code;
//			url += "&grant_type=authorization_code";
//			System.out.println(url);
//			HashMap<String, Object> resultMap = HttpUtil.getUrl(url);
//			System.out.println(resultMap);
//			
//			String userUrl = "https://kapi.kakao.com/v2/user/me";
//			userUrl += "?access_token=" + resultMap.get("access_token");
//			System.out.println(userUrl);
//			resultMap = HttpUtil.getUrl(userUrl);
//			System.out.println(resultMap);
//			httpsession.setAttribute("access_token", resultMap.get("access_token"));
//			LoginInfoBean logininfo = new LoginInfoBean();
//			
//			JSONObject jobj = JSONObject.fromObject(resultMap.get("properties"));
//			String nickname = jobj.getString("nickname");
//			
//			logininfo.setId(Integer.parseInt((String) resultMap.get("id")));
//			logininfo.setNickname(nickname);
//			
//			//필수
//			httpsession.setAttribute("nickname", nickname);
//
//			int result =session.insert("Avocado.login", logininfo);
//			System.out.println("성공여부:"+ result);
//			
//			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "redirect:notice";
//	}
//	
//	// 게시판 메인 페이지
//	@RequestMapping("/notice")
//	public String notice(HttpSession hs, HttpServletRequest req) {
//		String nickname = (String) hs.getAttribute("nickname");
//		System.out.println("접속자명: "+nickname);	
//		List<ListBean> list = session.selectList("Avocado.select");
//		req.setAttribute("list", list);
//		return "notice";
//	}
//	
//	// 추가하는 페이지로 이동 
//	@RequestMapping("/input")
//	public String input() {
//		return "input";
//	}
//	
//	// data insert
//	@RequestMapping(value="/insert", method = RequestMethod.POST)
//	public String insert(HttpServletRequest req, HttpSession hs, @RequestParam("file") MultipartFile[] files) {
//		String nickname = (String) hs.getAttribute("nickname");
//		String title = req.getParameter("title");
//		String comment = req.getParameter("comment");
//		
//		ListBean lb = new ListBean();
//		lb.setNickname(nickname);
//		lb.setTitle(title);
//		lb.setComment(comment);	
//		session.insert("Avocado.insert", lb);
//		
//		int no = session.selectOne("Avocado.no", lb);
//		
//		UploadListBean up = new UploadListBean();
//		try {
//			for(int i = 0; i < files.length; i++) {
//				MultipartFile file = files[i];
//				String originalFileName = file.getOriginalFilename();
//				String ext = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
//				String fileName = UUID.randomUUID().toString();
//				byte[] data = file.getBytes();
//				String path = "D:\\downloads\\Apache24\\htdocs\\"; 
//				File f = new File(path);
//				if(!f.isDirectory()) {
//					f.mkdirs();
//				}
//				OutputStream os = new FileOutputStream(new File(path + fileName + ext));
//				os.write(data);
//				os.close();
//				
//				String url = "http://192.168.3.90/" + fileName + ext; 
//				up.setNo(no);
//				up.setNickname(nickname);
//				up.setFilename(originalFileName);
//				up.setFileurl(url);
//				System.out.println(url);
//				
//				session.insert("Avocado.upload", up);
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return "redirect:notice";	
//	}
//	
//	// 자세히보기
//	@RequestMapping("/result/{index}") 
//	public String result(@PathVariable("index") int index, HttpServletRequest req) {
//		List<SelectBean> data = session.selectList("Avocado.result", index);
//		req.setAttribute("data", data);
//		return "result";
//	}
//	
//	// 해당 사진 다운로드
//	@RequestMapping("/download/{no}/{index}") 
//	public void download(HttpServletRequest req, HttpServletResponse res, @PathVariable("no") int no, 
//																		  @PathVariable("index") int index) {
//		List<UploadListBean> imgs = session.selectList("Avocado.download", no);
//		String path = "D:\\downloads\\Apache24\\htdocs\\";
//		String fileName[] = imgs.get(index).getFileurl().split("90/");
////		System.out.println(fileName[1]);
//		String originalFileName = imgs.get(index).getFilename();
//		try {
//			InputStream input = new FileInputStream(path+fileName[1]);
//			OutputStream output = res.getOutputStream();
//			IOUtils.copy(input, output);
//			
//			res.setHeader("Content-Disposition", "attachment; filename=\""+ originalFileName + "\"");
//			
//			input.close();
//			output.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//
//	
//	//삭제
//	@RequestMapping(value="/delete", method = RequestMethod.POST)
//	public String delete(HttpServletRequest req) {
//		System.out.println(Integer.parseInt(req.getParameter("no")));
//		session.update("Avocado.delete", Integer.parseInt(req.getParameter("no")));
//		return "redirect:/notice";
//	}
//	
//}


