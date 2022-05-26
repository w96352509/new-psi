package com.study.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.entity.Image;
import com.study.springmvc.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

@Controller
@RequestMapping("/upload")
public class uploadController {

	@Autowired
	private ImageRepository imageRepository;
	
	@GetMapping("/")
	private String index(Model model) {
		model.addAttribute("images" , imageRepository.findAll());
		return "upload";
	}
	
	@PostMapping("/")
	private String uploadImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Image image = new Image();
        req.getParts()
        .stream()
        .filter(part -> part.getName().equals("cname"))
        .forEach(part->{
        	try {
        		 String cname = IOUtils.toString(part.getInputStream(),
                                                 StandardCharsets.UTF_8.name());
        		 image.setName(cname);
			} catch (Exception e) {
				
			}
        });
       
        
        req.getParts()
                .stream()
                .filter(part -> part.getName().equals("upload_file"))
                .forEach(part -> {
                    try {
                        // 解析圖片(無關存入資料庫因資料庫是存路徑)
                    	// 將 InputStream -> byte[] -> base64 字串
                        InputStream is = part.getInputStream();
                        byte[] bytes = IOUtils.toByteArray(is);
                        String data = Base64.getEncoder().encodeToString(bytes);
                        System.out.println("data:"+data);
                        String img = "<img src='data:image/png;base64, %s'>";
                        img = String.format(img, data);
                        System.out.println("img:"+img);
                        
                        // 存檔資料夾
                        String fileSavingFolder = "C:\\Users\\vic\\git\\PSIHome\\psi_home\\src\\main\\resources\\static\\images";
                                                   
                        // 確認資料夾是否存在
                        File folder = new File(fileSavingFolder);
                        if(!folder.exists()) {
                            folder.mkdir(); // 建立資料夾
                        }
                        // 取得檔名
                        String fname = part.getSubmittedFileName();
                        // 存檔路徑
                        String fileSavingPath = fileSavingFolder + File.separator + fname;
                        System.out.println("fiel:"+fileSavingPath);
                        //將檔案寫入到伺服器中(存入資料夾)
                        part.write(fileSavingPath);
                        image.setPath(fname);
                       
                    } catch (Exception e) {
                    }
                });
                imageRepository.save(image);
                return "redirect:./";
    } 
	
}
