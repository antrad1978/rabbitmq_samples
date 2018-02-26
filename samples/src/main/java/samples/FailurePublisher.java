package samples;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class FailurePublisher {
	public static void main(String[] argv)
            throws java.io.IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        try {
        	//factory.setUri("amqp://jonejzhn:eHy4UiXOZyT2u3KyK0-afQsQgNrQmetb@reindeer.rmq.cloudamqp.com/jonejzhn");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            channel.addReturnListener(new FailureReturnListener());
            String myExchange = "failure";
			channel.exchangeDeclare(myExchange, "direct", false, false, null);
            
			boolean isMandatory = true; // if true the message will be handled by HandlingReturnListener
			// if false the message will be dropped! 
            
            String message = "Hello Route!";
            channel.basicPublish(myExchange, "",isMandatory,null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            //Used for wait error log
            Thread.sleep(5000);
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}


