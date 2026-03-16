package flipnote.notification.application.dto.message;

import java.util.List;

public record GroupJoinRequestMessage(
	Long groupId,
	List<Long> receiverIds,
	Long requesterId,
	String requesterNickname
) {
}
