//package com.example.ypa_roll_call.roomold.service.impl;
//
//import com.example.ypa_roll_call.common.annotation.annotation.logging.LogOperation;
//import com.example.ypa_roll_call.roomold.service.RoomService;
//import com.example.ypa_roll_call.roomold.service.WebhookEventHandler;
//import livekit.LivekitWebhook.WebhookEvent;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class RoomFinishedHandler implements WebhookEventHandler {
//
//    private final RoomService roomService;
//
//    @Override
//    public String supportedEventType() {
//        return "room_finished";
//    }
//
//    @Override
//    @LogOperation("ROOM_FINISHED_HANDLER")
//    public void handle(WebhookEvent event) {
//        UUID roomId = UUID.fromString(event.getRoom().getName());
//        log.info("Room {} finished on LiveKit, cleaning up local record", roomId);
//        try {
//            roomService.deleteLocalRoom(roomId);
//        } catch (Exception e) {
//            log.warn("Failed to clean up roomold {} after room_finished: {}", roomId, e.getMessage());
//        }
//    }
//}