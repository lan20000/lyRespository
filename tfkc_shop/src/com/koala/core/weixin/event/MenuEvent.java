package com.koala.core.weixin.event;

/**
* 类名: MenuEvent </br>
* 描述: 自定义菜单事件 </br>
* 发布版本：V1.0  </br>
 */
public class MenuEvent extends BaseEvent {
    // 事件KEY值，与自定义菜单接口中KEY值对应
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}