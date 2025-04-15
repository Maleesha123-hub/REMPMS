package com.pdev.rempms.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.net.InetAddress;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);

		try {
			InetAddress address = InetAddress.getByName("DESKTOP-681N2R8.mshome.net");
			System.out.println("IP Address: " + address.getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
