package com.bubble.boot.config;

/**
 * @author yanlin
 */
//@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true)// 允许在应用中的类和方法添加注解
public class SecurityConfiguration 
	//extends WebSecurityConfigurerAdapter
{

	/**
	 * 搭建一个内存系统，其中包含了应用程序中的用户及其角色。它会
	 * 重写之前应用属性中所定义的安全用户名和密码。
	 */
//	@Autowired
//	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("user").password("user").roles("USER").and()
//			.withUser("admin").password("admin").roles("USER", "ADMIN");
//	}
//	
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic()
//			.and()
//			.csrf().disable()
//			.authorizeRequests()
//			.antMatchers("/login", "/logout").permitAll()
//			.antMatchers(HttpMethod.GET, "/api/**").hasRole("USER")
//			.antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
//			.antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
//			.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
//			.anyRequest().authenticated();
//	}
}
