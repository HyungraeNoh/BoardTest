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
	public String select(HttpServletRequest req) {
		List<ListBean> list = session.selectList("board.select");
		System.out.println(list.size());
		req.setAttribute("list", list);
		return "home";
	}
	
	@RequestMapping("/insert")
	public String insert(HttpServletRequest req) {
		session.insert("board.insert", new ListBean(0, req.getParameter("txt")));
		return "redirect:/";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest req) {
		session.update("board.update", new ListBean(Integer.parseInt(req.getParameter("no")), req.getParameter("txt")));
		return "redirect:/";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest req) {
		session.update("board.delete", new ListBean(Integer.parseInt(req.getParameter("no")), req.getParameter("txt")));
		return "redirect:/";
	}
	
	// 고유한 파일명 생성 (UUID)
	String randomFileName = UUID.randomUUID().toString();
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String file(@RequestParam("file") MultipartFile[] files, HttpServletRequest req) {
		session.insert("board.insert", new ListBean(0, req.getParameter("title"), req.getParameter("txt")));
		try {
			int[] statusList = new int[files.length];
			for(int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				System.out.println(file.getOriginalFilename());
				// 원본 파일명 생성 (test.txt)
				String originalFileName = file.getOriginalFilename();
				// 파일 확장자 생성 (test.txt -> .txt)
				String ext = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
				
				
				// 프로젝트 경로 받기
				System.out.println(req.getContextPath());  // 화면에서만 사용 하면 좋다
				System.out.println(req.getSession().getServletContext().getRealPath("/")); // 파일 처리 시 좋다.
				
				// 데이터 가져오기
				byte[] data = file.getBytes();
				// 저장 경로 + 파일명 정의
				String realPath = req.getSession().getServletContext().getRealPath("/");
				
				String path = "D:\\IDE\\workspace\\Resource\\"; // 작성자 / 메뉴 / 날짜 / 시간 / 파일명
				// 파일 객체 생성
				File f = new File(path);
				// 파일 생성 경로에 폴더가 없으면 생성 처리
				if(!f.isDirectory()) {
					f.mkdirs();
				}
				// 출력 객체 생성 + 파일 객체 넣기   (저장경로 + uuid + 확장자)
				OutputStream os = new FileOutputStream(new File(path + randomFileName + ext));
				// 가져온 데이터 출력 객체에 넣기
				os.write(data);
				// 출력 객체 종료
				os.close();
				
				FilesBean fb = new FilesBean();
				fb.setDelYn("N");
				fb.setFileName(originalFileName);
				fb.setFileURL(randomFileName + ext);
				int status = session.insert("test.insert", fb);
				statusList[i] = status;
				System.out.println(i + ") " + originalFileName + " : " + status);
				
				
				
//				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
//				InputStream is = file.getInputStream();
//				InputStreamReader isr = new InputStreamReader(is);
//				BufferedReader br = new BufferedReader(isr);
//				StringBuilder sb = new StringBuilder();
//				String txt = "";
//				String result = "";
//				while((txt = br.readLine()) != null) result += txt + "\n";
//				System.out.println(result);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@RequestMapping("/download/{originalName}/{ext}")
	public void download(HttpServletRequest req, HttpServletResponse res, 
			             @PathVariable("originalName") String originalFileName,
			             @PathVariable("ext") String ext) {
		String path = "D:\\IDE\\workspace\\Resource\\";
		String fileName = randomFileName + ext;
//		String originalFileName = "test.txt";
		try {
			InputStream input = new FileInputStream(path + fileName);
			OutputStream output = res.getOutputStream();
			IOUtils.copy(input, output);		
			res.setHeader("Content-Disposition", "attachment; filename=\"" + (originalFileName +"."+ext) + "\"");
			input.close();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/write")
	public String write() {
		
		return "write";
	}
	
}
