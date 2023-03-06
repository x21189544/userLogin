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
	
	private static UserServiceGrpc.UserServiceBlockingStub blockingStub;
	
	public static void main(String[] args) throws Exception{
		//define host and port variables
		String host = "localhost";
		int port = 50051;
		
		//build channel
		ManagedChannel channel = ManagedChannelBuilder.
				forAddress(host, port)
				.usePlaintext()
				.build();
		//stubs
		blockingStub = UserServiceGrpc.newBlockingStub(channel);
		
		UserClient client = new UserClient();
		
		//call methods
		login();
		logout();

		//shutdown channel
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		
	}
	
	public static void login() {
		try {
			String name = "Divyaa";
			String password = "Password123";
			
			LoginRequest request = LoginRequest.newBuilder().setUsername(name).setPassword(password).build();
			
			LoginResponse response = blockingStub.login(request);
			
			logger.info(response.getResponseMessage());
			System.out.println("Error code: " + response.getResponseCode());
			
			
		}
		catch (StatusRuntimeException e) {
		    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		    
		    return;		
		    
	    } 
	}
	
	public static void logout(){
		try {
			String name = "Divyaa";
			
			LogoutRequest request = LogoutRequest.newBuilder().setUsername(name).build();
			
			LogoutResponse response = blockingStub.logout(request);
			
			logger.info(response.getResponseMessage());
			System.out.println("Error code: " + response.getResponseCode());
		}
		catch (StatusRuntimeException e) {
		    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		    
		    return;		
		    
	    } 
		
	}
	
}

