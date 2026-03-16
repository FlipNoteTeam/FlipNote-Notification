package flipnote.notification.domain.fcmtoken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {

	List<FcmToken> findByUserId(Long userId);

	Optional<FcmToken> findByToken(String token);

	void deleteByUserIdAndTokenIn(Long userId, List<String> tokens);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE FcmToken f SET f.lastUsedAt = :now WHERE f.token IN :tokens")
	int bulkUpdateLastUsedAt(@Param("tokens") List<String> tokens, @Param("now") LocalDateTime now);
}
