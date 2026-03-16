package flipnote.notification.application.service;

import java.util.List;
import java.util.Locale;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.notification.application.dto.request.NotificationListRequest;
import flipnote.notification.application.dto.response.NotificationResponse;
import flipnote.notification.common.response.CursorPagingResponse;
import flipnote.notification.domain.notification.Notification;
import flipnote.notification.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationQueryService {

	private final NotificationRepository notificationRepository;
	private final MessageSource messageSource;

	public CursorPagingResponse<NotificationResponse> getNotifications(Long userId, NotificationListRequest req) {
		List<Notification> notifications = notificationRepository.findNotificationsByReceiverIdAndCursor(
			userId, req.getCursorId(), req.getGroupId(), req.getRead(), req.getPageRequest()
		);

		boolean hasNext = notifications.size() > req.getSize();
		Long nextCursor = null;
		if (hasNext) {
			notifications = notifications.subList(0, req.getSize());
			nextCursor = notifications.get(notifications.size() - 1).getId();
		}

		List<NotificationResponse> content = notifications.stream()
			.map(notification -> {
				String message = buildMessage(notification);
				return NotificationResponse.of(notification, message);
			})
			.toList();

		return CursorPagingResponse.of(content, hasNext, nextCursor);
	}

	private String buildMessage(Notification notification) {
		String key = notification.getType().getMessageKey();
		String template = messageSource.getMessage(key, null, key, Locale.KOREA);
		StringSubstitutor substitutor = new StringSubstitutor(notification.getVariables());
		return substitutor.replace(template);
	}
}
