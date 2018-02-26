package samples;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class TransactionPublisher {
	public static void main(String[] argv)
            throws java.io.IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        
        try {
        	//factory.setUri("amqp://jonejzhn:eHy4UiXOZyT2u3KyK0-afQsQgNrQmetb@reindeer.rmq.cloudamqp.com/jonejzhn");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            
            String myQueue = "transaction";
			boolean isDurable = true; // n.b.
			boolean isExclusive = false;
			boolean isAutoDelete = false;

			channel.queueDeclare(myQueue, isDurable, isExclusive, isAutoDelete,null);
			channel.txSelect();
            
            String message = "Hello Transaction!";
            channel.basicPublish("",myQueue, MessageProperties.PERSISTENT_TEXT_PLAIN ,message.getBytes());
            channel.txCommit();
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}
