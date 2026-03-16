package flipnote.notification.application.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import flipnote.notification.domain.notification.Notification;

public record NotificationResponse(
	Long notificationId,
	Long groupId,
	String message,
	Map<String, Object> metadata,
	boolean isRead,

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime readAt,

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime createdAt
) {

	public static NotificationResponse of(Notification notification, String message) {
		return new NotificationResponse(
			notification.getId(),
			notification.getGroupId(),
			message,
			notification.getMetadata(),
			notification.isRead(),
			notification.getReadAt(),
			notification.getCreatedAt()
		);
	}
}
