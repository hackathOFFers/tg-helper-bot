package com.hackathon.bothelper.route.handlers;

import com.hackathon.bothelper.domain.ResponseMessage;
import org.apache.shiro.session.Session;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Handler {

	boolean isSuitableFor(Message message, Session session1);

	ResponseMessage getMessageForReply(Message message);
}
