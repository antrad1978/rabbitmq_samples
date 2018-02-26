package samples;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class DeadLetterPublisher {
	public static void main(String[] args) {
		String RabbitmqHost = "localhost";
		if (args.length > 0)
			RabbitmqHost = args[0];
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(RabbitmqHost);
		try 
		{
			Connection connection = factory.newConnection();
			System.out.println("Connected: " + RabbitmqHost);
			Channel channel = connection.createChannel();
			/* preparing the queue and the exchange*/
			channel.exchangeDeclare("exchange", "direct", false);
			channel.exchangeDeclare("exchange_dead_letter", "direct", false);
			Map<String, Object> arguments = new HashMap<String, Object>();
			arguments.put("x-dead-letter-exchange","exchange_dead_letter");
			arguments.put("x-message-ttl", 10000);
			channel.queueDeclare("queue_master", false, false, false, arguments);
			channel.queueBind("queue_master", "exchange", "");

			channel.basicPublish("exchange", "", null, "hello".getBytes());
			channel.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

