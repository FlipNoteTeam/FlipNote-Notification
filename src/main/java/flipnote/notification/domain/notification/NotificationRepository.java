package flipnote.notification.domain.notification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

public interface NotificationRepository {

	Notification save(Notification notification);

	List<Notification> saveAll(List<Notification> notifications);

	Optional<Notification> findByIdAndReceiverId(Long id, Long receiverId);

	List<Notification> findNotificationsByReceiverIdAndCursor(
		Long receiverId, Long cursor, Long groupId, Boolean read, Pageable pageable
	);

	int bulkMarkAsRead(Long userId, LocalDateTime now);
}
