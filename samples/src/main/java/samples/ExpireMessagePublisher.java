package samples;


import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ExpireMessagePublisher {
	public static final String routingKey = "java stats";
	/**
	 * @param args
	 *            [0] RabbitmqHost
	 */
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
			channel.exchangeDeclare("expire", "direct", false);
			channel.queueDeclare("expire_queue", false, false, false, null);
			channel.queueBind("expire_queue", "expire", routingKey);
			// set message expiration time to 20 seconds
			BasicProperties msgProperties = new BasicProperties.Builder().expiration("20000").build();
			
			channel.basicPublish("expire", routingKey, msgProperties, "TEST".getBytes());
			channel.close();
			System.out.println("Messages sent");
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

