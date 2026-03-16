package flipnote.notification.application.port;

import java.util.List;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;

public interface FcmSender {

	BatchResponse sendEachForMulticast(
		List<String> tokens,
		String title,
		String body
	) throws FirebaseMessagingException;
}
