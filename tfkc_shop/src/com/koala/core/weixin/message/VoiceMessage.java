package com.koala.core.weixin.message;

/**
* 类名: VoiceMessage </br>
* 描述: 请求消息之语音消息 </br>
* 发布版本：V1.0  </br>
 */
public class VoiceMessage extends BaseMessage {
	 // 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

}
