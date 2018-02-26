package samples;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class TopicPublisher {
	public static void main(String[] argv)
            throws java.io.IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        try {
        	//factory.setUri("amqp://jonejzhn:eHy4UiXOZyT2u3KyK0-afQsQgNrQmetb@reindeer.rmq.cloudamqp.com/jonejzhn");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            String myExchange = "topic_exchange";
            channel.exchangeDeclare(myExchange, "topic", false, false, null);
            
            String routing="com.rabbitmq";
            
            String message = "Hello Topic!";
            channel.basicPublish(myExchange, routing, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}

