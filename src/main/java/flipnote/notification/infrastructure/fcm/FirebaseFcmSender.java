package flipnote.notification.infrastructure.fcm;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import flipnote.notification.application.port.FcmSender;

@Component
public class FirebaseFcmSender implements FcmSender {

	@Override
	public BatchResponse sendEachForMulticast(
		List<String> tokens,
		String title,
		String body
	) throws FirebaseMessagingException {
		Notification notification = Notification.builder()
			.setTitle(title)
			.setBody(body)
			.build();
		MulticastMessage message = MulticastMessage.builder()
			.addAllTokens(tokens)
			.setNotification(notification)
			.build();

		return FirebaseMessaging.getInstance().sendEachForMulticast(message);
	}
}
