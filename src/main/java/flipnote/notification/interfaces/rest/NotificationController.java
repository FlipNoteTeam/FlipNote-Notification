package flipnote.notification.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flipnote.notification.application.dto.request.NotificationListRequest;
import flipnote.notification.application.dto.request.TokenRegisterRequest;
import flipnote.notification.application.dto.response.NotificationResponse;
import flipnote.notification.application.service.FcmTokenService;
import flipnote.notification.application.service.NotificationCommandService;
import flipnote.notification.application.service.NotificationQueryService;
import flipnote.notification.common.response.CursorPagingResponse;
import flipnote.notification.interfaces.rest.docs.NotificationControllerDocs;
import flipnote.notification.interfaces.security.AuthenticatedUser;
import flipnote.notification.interfaces.security.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/notifications")
public class NotificationController implements NotificationControllerDocs {

	private final NotificationQueryService notificationQueryService;
	private final NotificationCommandService notificationCommandService;
	private final FcmTokenService fcmTokenService;

	@Override
	@GetMapping
	public ResponseEntity<CursorPagingResponse<NotificationResponse>> getNotifications(
		@Valid @ModelAttribute NotificationListRequest req,
		@CurrentUser AuthenticatedUser user
	) {
		CursorPagingResponse<NotificationResponse> res
			= notificationQueryService.getNotifications(user.userId(), req);

		return ResponseEntity.ok(res);
	}

	@Override
	@PostMapping("/token")
	public ResponseEntity<Void> registerFcmToken(
		@Valid @RequestBody TokenRegisterRequest req,
		@CurrentUser AuthenticatedUser user
	) {
		fcmTokenService.registerFcmToken(user.userId(), req);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	@PostMapping("/read-all")
	public ResponseEntity<Void> markAllNotificationsAsRead(
		@CurrentUser AuthenticatedUser user
	) {
		notificationCommandService.markAllNotificationsAsRead(user.userId());

		return ResponseEntity.ok().build();
	}

	@Override
	@PostMapping("/{notificationId}/read")
	public ResponseEntity<Void> markNotificationAsRead(
		@PathVariable("notificationId") Long notificationId,
		@CurrentUser AuthenticatedUser user
	) {
		notificationCommandService.markNotificationAsRead(user.userId(), notificationId);

		return ResponseEntity.ok().build();
	}
}
