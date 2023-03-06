package grpc.demo.user;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import grpc.demo.user.UserServiceGrpc.UserServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class UserClient {
	
	private static final Logger logger = Logger.getLogger(UserClient.class.getName());
	
	public static void main(String[] args) throws Exception{
		String host = "localhost";
		int port = 50051;
		
		ManagedChannel channel = ManagedChannelBuilder.
				forAddress(host, port)
				.usePlaintext()
				.build();
		
		UserServiceBlockingStub blockingStub = UserServiceGrpc.newBlockingStub(channel);
		
		UserClient client = new UserClient();
		
		try {
			String name = "Divyaa";
			String password = "Password123";
			
			LoginRequest request = LoginRequest.newBuilder().setUsername(name).setPassword(password).build();
			
			LoginResponse response = blockingStub.login(request);
			
			logger.info(response.getResponseMessage());
			
		}
		catch (StatusRuntimeException e) {
		    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		    
		    return;		
		    
	    } finally {
	    	//shutdown channel
	    	channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	    } 
		
		
	}
}

