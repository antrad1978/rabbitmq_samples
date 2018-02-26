package samples;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.ReturnListener;

public class FailureReturnListener implements ReturnListener  {

	public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
			BasicProperties properties, byte[] body) throws IOException {
		
		System.out.println(replyText +":" +replyCode);
		System.out.println(new String(body));
	}
}
