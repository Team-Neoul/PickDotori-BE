package com.theZ.dotoring.app.notification.dto;

import com.theZ.dotoring.app.notification.model.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NotificationDetailDTO {

    private String notificationName;

    private String writerMajor;

    private List<String> goals;

    private String introduce;

    private int maxRecruitment;

    private int curRecruitment;

    private boolean isWriter;

    public static NotificationDetailDTO of(Notification notification, String memberMajor, String memberNickname){
        return NotificationDetailDTO.builder()
                .notificationName(notification.getTitle())
                .writerMajor(memberMajor)
                .goals(notification.getNotificationGoals())
                .introduce(notification.getIntroduction())
                .maxRecruitment(notification.getMaxRecruitment())
                .curRecruitment(notification.getCurRecruitment())
                .isWriter(memberIsWriter(memberNickname, notification.getAuthor()))
                .build();
    }

    private static boolean memberIsWriter(String memberNickname, String notificationAuthor){
        return memberNickname.equals(notificationAuthor);
    }
}
