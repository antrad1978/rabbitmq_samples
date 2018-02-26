package samples;

import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class DeadLetterConsumer {
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
			channel.exchangeDeclare("exchange_dead_letter", "direct", false);
			
			channel.queueDeclare("queue_dead_letter", false, false, false, null);
			channel.queueBind("queue_dead_letter", "exchange_dead_letter", "");
			// get just one message.
			//GetResponse response = channel.basicGet("queue_dead_letter", true );
			Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume("queue_dead_letter", true, consumer);
			
            System.in.read();
            //in real code add try catch and move this code out
    			channel.close();
    			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
