package com.bubble.boot.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yanlin
 */
@Slf4j
@Controller
@SessionAttributes("picturePath") // 将picturePath属性定义在会话中
public class PictureUploadController {

	private final Resource picturesDir;
	private final Resource anonymousPicture;
	
	private final MessageSource messageSource;
	
	@Autowired
	public PictureUploadController(PictureUploadProperties uploadProperties, MessageSource messageSource) {
		picturesDir = uploadProperties.getUploadPath();
		anonymousPicture = uploadProperties.getAnonymousPictures();
		
		log.debug("picturesDir={}", picturesDir);
		this.messageSource = messageSource;
	}
	
	@ModelAttribute("picturePath")
	public Resource picturePaht() {
		return anonymousPicture;
	}
	
	
	@RequestMapping("/upload")
	public String uploadPage() {
		return "profile/uploadPage";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes,
			Model model) throws IOException {
		
		// throw new IOException("上传错误");
		
		if(file.isEmpty() || !isImage(file)) {
			redirectAttributes.addFlashAttribute("error", 
					"Incorrect file. Please upload a picture.");
			return "redirect:/upload";
		}
		
		Resource picturePath = copyFileToPictures(file);
		model.addAttribute("picturePath", picturePath);
		
		return "profile/uploadPage";
	}
	
	/**
	 * 展示图片
	 * @param response 
	 */
	@RequestMapping(value = "/showUploadPicture")
	public void getUploadPicture(HttpServletResponse response,
			@ModelAttribute("picturePath") Resource picturePath) throws IOException {
		log.debug("picturePath={}", picturePath);
		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(
				anonymousPicture.getFilename()));
		IOUtils.copy(picturePath.getInputStream(), response.getOutputStream());
	}
	
	/**
	 * 输入流 -> 输出流
	 * @param file 文件
	 */
	private Resource copyFileToPictures(MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();
		File tempFile = File.createTempFile("pic", getFileExtension(filename), 
				picturesDir.getFile());
		try(
			InputStream in = file.getInputStream();
			OutputStream out = new FileOutputStream(tempFile)
		) {
			IOUtils.copy(in, out);
		}
		
		return new FileSystemResource(tempFile);
	}

	/**
	 * 判断上传文件是图片
	 * @param file 文件
	 */
	private boolean isImage(MultipartFile file) {
		/* getContentType() 方法将会返回文件的多用途 Internet 邮件扩展
		 * (Multipurpose Internet Mail Extensions，MIME)类型。
		 * 它将会是 image/png、image/jpg等。
		 * 所以，只需要检查 MIME 类型是否以“image”开头即可.
		 */
		return file.getContentType().startsWith("image");
	}
	
	/**
	 * 获取文件类型名称
	 * @param name 文件名
	 */
	private static String getFileExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}
	
	
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(Locale locale) {
		ModelAndView mv = new ModelAndView("profile/uploadPage");
		// 从信息 bundle 中获取信息
		mv.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
		return mv;
	}
	
	/**
	 * 处理MultipartException
	 */
	@RequestMapping("/uploadError")
	public ModelAndView onUploadError(Locale locale) {
		ModelAndView mv = new ModelAndView("profile/uploadPage");
		//mv.addObject("error", request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE));
		mv.addObject("error", messageSource.getMessage("upload.file.too-big", null, locale));
		return mv;
	}
}
