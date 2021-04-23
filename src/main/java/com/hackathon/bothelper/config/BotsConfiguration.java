package com.hackathon.bothelper.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Configuration
@AllArgsConstructor
public class BotsConfiguration {

	private final List<TelegramLongPollingBot> bots;

	@Bean
	public TelegramBotsApi getTelegramBotsApi() throws TelegramApiException {
		final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		for (final TelegramLongPollingBot bot : bots) {
			telegramBotsApi.registerBot(bot);
		}
		return telegramBotsApi;
	}
}
