package flipnote.notification.domain.fcmtoken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository {

	FcmToken save(FcmToken fcmToken);

	List<FcmToken> findByUserId(Long userId);

	Optional<FcmToken> findByToken(String token);

	void deleteById(Long id);

	void deleteByUserIdAndTokenIn(Long userId, List<String> tokens);

	int bulkUpdateLastUsedAt(List<String> tokens, LocalDateTime now);
}
