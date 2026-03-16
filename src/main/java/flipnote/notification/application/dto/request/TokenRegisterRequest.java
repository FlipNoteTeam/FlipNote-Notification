package flipnote.notification.application.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record TokenRegisterRequest(
	@NotEmpty
	String token
) {
}
