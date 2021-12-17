package com.launchcode.AroundTownServer;

import com.launchcode.AroundTownServer.data.EventRepository;
import com.launchcode.AroundTownServer.models.Event;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class AroundTownServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AroundTownServerApplication.class, args);
	}

	}
