package com.fastcampus.ch3;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.*;
import org.springframework.web.context.support.*;

import javax.servlet.*;
import javax.servlet.http.*;

//@Controller
public class HomeController {

	/**
	 * 두 개의 Application Context (root, servlet)에 어떻게 접근 할 수 있을까?
	 * Root ApplicationContext : 아래의 코드 두 줄 추가 (line 33~34)
	 * Servlet Application Context : @Autowired를 이용한 주입
	 */

	@Autowired
	WebApplicationContext servletAC; // Servlet Application Context는 이렇게 주입을 받을 수 있다.

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, HttpServletRequest request, Model model) {
		// 원래 서블릿에서는 request.getServletContext()지만, 컨트롤러는 HttpServlet을 상속받지 않아서(서블릿이 아니라서) 아래와 같이 해야함.
		// 즉, Root ApplicationContext를 컨트롤러에서 얻어오려면 아래 두 줄을 추가해줘야한다.
		ServletContext sc = request.getSession().getServletContext(); // ApplicationContextFacade
		WebApplicationContext rootAC = WebApplicationContextUtils.getWebApplicationContext(sc); // Root AC

		System.out.println("webApplicationContext = " + rootAC);
		System.out.println("servletAC = " + servletAC);

		System.out.println("rootAC.getBeanDefinitionNames() = " + Arrays.toString(rootAC.getBeanDefinitionNames()));
		System.out.println("servletAC.getBeanDefinitionNames() = " + Arrays.toString(servletAC.getBeanDefinitionNames()));

		System.out.println("rootAC.getBeanDefinitionCount() = " + rootAC.getBeanDefinitionCount());
		System.out.println("servletAC.getBeanDefinitionCount() = " + servletAC.getBeanDefinitionCount());

		System.out.println("servletAC.getParent()==rootAC = " + (servletAC.getParent() == rootAC)); // servletAC.getParent()==rootAC = true
		return "home";
	}
}