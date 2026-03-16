package flipnote.notification.application.dto.message;

public record GroupInviteMessage(
	Long groupId,
	Long inviteeId,
	String groupName
) {
}
