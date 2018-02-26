package samples;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class TopicConsumer {
	public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
        	//factory.setUri("amqp://jonejzhn:eHy4UiXOZyT2u3KyK0-afQsQgNrQmetb@reindeer.rmq.cloudamqp.com/jonejzhn");
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            String myExchange = "topic_exchange";
            String routing = "com.boooo";
            
			channel.exchangeDeclare(myExchange, "topic", false, false, null);
			String myQueue = channel.queueDeclare().getQueue();
			
			channel.queueBind(myQueue,myExchange,routing);
			
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                    //channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };
            String consumerTag = channel.basicConsume(myQueue, true, consumer);
			System.out.println(consumerTag);
			
			System.in.read();
            //in real code add try catch and move this code out
    			channel.close();
    			connection.close();
        }catch (Exception e){

        }finally {
        	
        }
    }
}
