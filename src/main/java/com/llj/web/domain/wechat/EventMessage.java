package com.llj.web.domain.wechat;

/**
 * Created by lu on 2017/2/15.
 */
public class EventMessage {

    private String event;
    private String eventKey;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "event='" + event + '\'' +
                ", eventKey='" + eventKey + '\'' +
                '}';
    }
}
