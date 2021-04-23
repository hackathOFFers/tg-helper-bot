package com.hackathon.bothelper;

import com.hackathon.bothelper.props.BotProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication
@AllArgsConstructor
@EnableConfigurationProperties({
        BotProperties.class})
public class HackathonApplication implements CommandLineRunner {

    private final TelegramBotsApi api;

    public static void main(String[] args) {
        SpringApplication.run(HackathonApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Bot started");
    }
}
