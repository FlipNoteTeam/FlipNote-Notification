package flipnote.notification.interfaces.rest.docs;

import org.springframework.http.ResponseEntity;

import flipnote.notification.application.dto.request.NotificationListRequest;
import flipnote.notification.application.dto.request.TokenRegisterRequest;
import flipnote.notification.application.dto.response.NotificationResponse;
import flipnote.notification.common.response.CursorPagingResponse;
import flipnote.notification.interfaces.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Notification", description = "Notification API")
public interface NotificationControllerDocs {

	@Operation(summary = "알림 목록 조회")
	ResponseEntity<CursorPagingResponse<NotificationResponse>> getNotifications(
		NotificationListRequest req,
		AuthenticatedUser user
	);

	@Operation(summary = "FCM 토큰 등록")
	ResponseEntity<Void> registerFcmToken(TokenRegisterRequest req, AuthenticatedUser user);

	@Operation(summary = "모든 알림 읽음 처리")
	ResponseEntity<Void> markAllNotificationsAsRead(AuthenticatedUser user);

	@Operation(summary = "알림 읽음 처리")
	ResponseEntity<Void> markNotificationAsRead(Long notificationId, AuthenticatedUser user);
}
