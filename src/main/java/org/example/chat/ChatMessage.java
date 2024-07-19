package org.example.chat;

public class ChatMessage {
    private String from; ////메시지를 보내는 사용자의 식별자(ID)
    private String to; //메시지를 받는 사용자의 식별자(ID)
    private String content; //실제 메시지의 내용

    // 기본 생성자
    public ChatMessage() {}

    // 모든 필드를 포함하는 생성자
    public ChatMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    // Getter와 Setter 메서드
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}