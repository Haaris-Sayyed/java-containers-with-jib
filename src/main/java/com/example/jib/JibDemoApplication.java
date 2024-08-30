package com.example.jib;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JibDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JibDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner setup(){
		return args -> {
			StepsPrinter.printMavenPluginBuildSteps();
			StepsPrinter.printMavenJibCoreBuildSteps();
		};
	}

}
