package samples;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import java.io.IOException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Consumer;

public class Subscriber {
	private final static String PS = "PS";

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
        	//factory.setUri("amqp://jonejzhn:eHy4UiXOZyT2u3KyK0-afQsQgNrQmetb@reindeer.rmq.cloudamqp.com/jonejzhn");
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.exchangeDeclare(PS, "fanout");
            String queue=channel.queueDeclare().getQueue();
            channel.queueBind(queue, PS, "");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(queue, true, consumer);
        }catch (Exception e){

        }finally {
        	
        }
    }
}
