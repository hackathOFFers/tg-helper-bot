package com.hackathon.bothelper.props;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "message")
@RequiredArgsConstructor
@Getter
@Setter
public class BotProperties {
	private final List<HandlerProperties> handlerProperties;

	@Getter
	@Setter
	@RequiredArgsConstructor
	public static class HandlerProperties {
		private String key;
		private String description;
		private String value;
	}
}