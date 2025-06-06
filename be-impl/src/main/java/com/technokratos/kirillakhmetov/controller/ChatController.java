package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.model.ChatMessage;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserContextHolder userContextHolderImpl;
    private final OwnerService ownerServiceImpl;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/chat")
    public String getChatPage(Model model) {
        OwnerResponse owner = ownerServiceImpl.getProfileInfo(userContextHolderImpl
                .getUserIdFromSecurityContext());
        model.addAttribute("owner", owner);
        return "chat";
    }

    @ResponseBody
    @PostMapping("/chat/send")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendMessageToTopic(@RequestBody ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        log.info("Получено сообщение от {}: {}", chatMessage.getSender(), chatMessage.getContent());
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        log.info("Пользователь {} присоединился к чату", chatMessage.getSender());
        return chatMessage;
    }
} 