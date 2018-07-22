package com.bubble.boot.profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bubble.boot.config.PictureUploadProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yanlin
 */
@Slf4j
@Controller
public class PictureUploadController {

	private final Resource picturesDir;
	private final Resource anonymousPicture;
	private final MessageSource messageSource;
	private final UserProfileSession userProfileSession;
	
	@Autowired
	public PictureUploadController(PictureUploadProperties uploadProperties,
			MessageSource messageSource, UserProfileSession userProfileSession) {
		
		picturesDir = uploadProperties.getUploadPath();
		anonymousPicture = uploadProperties.getAnonymousPictures();
		
		this.userProfileSession = userProfileSession;
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/profile", params = {"upload"}, method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes,
			Model model) throws IOException {
		
		if(file.isEmpty() || !isImage(file)) {
			redirectAttributes.addFlashAttribute("error", 
					"Incorrect file. Please upload a picture.");
			return "redirect:/profile";
		}
		
		Resource picturePath = copyFileToPictures(file);
		userProfileSession.setPicturePath(picturePath);
		return "redirect:profile";
	}
	
	/**
	 * 展示图片
	 * @param response 
	 */
	@RequestMapping(value = "/showUploadPicture")
	public void getUploadPicture(HttpServletResponse response) throws IOException {
		Resource picturePath = userProfileSession.getPicturePath();
		picturePath = Objects.isNull(picturePath) ? anonymousPicture : picturePath;
		log.debug("picturePath={}", picturePath);
		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(
				picturePath.getFilename()));
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
	public ModelAndView handleIOException(HttpServletRequest req, Exception ex, Locale locale) {
		log.error("Request:" + req.getRequestURL() + " raised " + ex);
		
		ModelAndView mv = new ModelAndView("profile/profilePage");
		// 从信息 bundle 中获取信息
		mv.addObject("error", messageSource.getMessage("upload.io.exception", null, locale));
		mv.addObject("profileForm", userProfileSession.toForm());
		return mv;
	}
	
	/**
	 * 处理MultipartException
	 */
	@RequestMapping("/uploadError")
	public ModelAndView onUploadError(Locale locale) {
		
		ModelAndView mv = new ModelAndView("profile/profilePage");
		//mv.addObject("error", request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE));
		mv.addObject("error", messageSource.getMessage("upload.file.too-big", null, locale));
		mv.addObject("profileForm", userProfileSession.toForm());
		return mv;
	}
}
