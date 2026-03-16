package flipnote.notification.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import flipnote.notification.domain.fcmtoken.FcmToken;
import flipnote.notification.domain.fcmtoken.FcmTokenRepository;

public interface JpaFcmTokenRepository extends JpaRepository<FcmToken, Long>, FcmTokenRepository {

	@Override
	List<FcmToken> findByUserId(Long userId);

	@Override
	void deleteByUserIdAndTokenIn(Long userId, List<String> tokens);

	@Override
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE FcmToken f SET f.lastUsedAt = :now WHERE f.token IN :tokens")
	int bulkUpdateLastUsedAt(@Param("tokens") List<String> tokens, @Param("now") LocalDateTime now);

	@Override
	Optional<FcmToken> findByToken(String token);
}
