package com.bubble.boot.config;

import java.io.IOException;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 配置双通道模式
 * 除了8080端口以外，还会加载签文件生成的keystore并在8443创建另外一个通道
 * @author yanlin
 */
@Configuration
public class SslConfig {

	@Bean
	public EmbeddedServletContainerFactory servletContainer() throws IOException {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addAdditionalTomcatConnectors(createSslConnector());
		return tomcat;
	}
	
	private Connector createSslConnector() throws IOException {
		String keystoreFile = new ClassPathResource("tomcat.keystore").getFile().getAbsolutePath();
		Connector connector = new Connector(Http11NioProtocol.class.getName());
		Http11NioProtocol protocol = (Http11NioProtocol)connector.getProtocolHandler();
		
		connector.setSecure(true);
		connector.setScheme("https");
		
		protocol.setSSLEnabled(true);
		protocol.setKeyAlias("bubblespringmvc4");
		protocol.setKeystoreFile(keystoreFile);;
		protocol.setSSLProtocol("TLS");
		return connector;
	}
}
