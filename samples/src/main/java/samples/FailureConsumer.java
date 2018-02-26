package samples;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class FailureConsumer {
	public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
        	//factory.setUri("amqp://jonejzhn:eHy4UiXOZyT2u3KyK0-afQsQgNrQmetb@reindeer.rmq.cloudamqp.com/jonejzhn");
            Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            // this call raises an exception if the queue doesn't exist
         	channel.queueDeclarePassive("hello");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                    //channel.basicAck(envelope.getDeliveryTag(), false);
                }
                
                @Override
                public void handleCancel(String consumerTag) throws IOException {
                	// TODO Auto-generated method stub
                	super.handleCancel(consumerTag);
                	System.out.println(consumerTag);
                }
            };
            String consumerTag = channel.basicConsume("hello", false, consumer);
            channel.basicCancel(consumerTag);
            Thread.sleep(30000);
            
            channel.close();
			connection.close();
        }catch (Exception e){

        }finally {
        	
        }
    }
}
