package samples;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;


public class Publisher {
    private final static String PS = "PS";

    public static void main(String[] argv)
            throws java.io.IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        try {
        	//factory.setUri("amqp://jonejzhn:eHy4UiXOZyT2u3KyK0-afQsQgNrQmetb@reindeer.rmq.cloudamqp.com/jonejzhn");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.exchangeDeclare(PS, "fanout");
            String message = "Hello World!";
            channel.basicPublish(PS, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            
            System.in.read();

            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}



