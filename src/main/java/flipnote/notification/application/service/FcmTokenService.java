package flipnote.notification.application.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import flipnote.notification.application.dto.request.TokenRegisterRequest;
import flipnote.notification.domain.fcmtoken.FcmToken;
import flipnote.notification.domain.fcmtoken.FcmTokenRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FcmTokenService {

	private final FcmTokenRepository fcmTokenRepository;

	@Transactional
	public void registerFcmToken(Long userId, TokenRegisterRequest req) {
		Optional<FcmToken> existingToken = fcmTokenRepository.findByToken(req.token());

		if (existingToken.isPresent()) {
			FcmToken token = existingToken.get();

			if (Objects.equals(token.getUserId(), userId)) {
				token.updateLastUsedAt();
			} else {
				fcmTokenRepository.deleteById(token.getId());
				saveFcmToken(userId, req.token());
			}
		} else {
			saveFcmToken(userId, req.token());
		}
	}

	private void saveFcmToken(Long userId, String token) {
		FcmToken fcmToken = FcmToken.builder()
			.userId(userId)
			.token(token)
			.build();

		fcmTokenRepository.save(fcmToken);
	}
}
