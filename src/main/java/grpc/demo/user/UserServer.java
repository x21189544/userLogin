package grpc.demo.user;

import java.io.IOException;
import java.util.logging.Logger;

import grpc.demo.user.UserServiceGrpc.UserServiceImplBase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class UserServer extends UserServiceImplBase{
	
	private static final Logger logger = Logger.getLogger(UserServer.class.getName());
	
	public static void main(String[] args) {
		UserServer loginserver = new UserServer();
		
		int port = 50051;
		
		try {
			Server server = ServerBuilder.forPort(port)
				    .addService(loginserver)
				    .build()
				    .start();
			
			logger.info("Server started, listening on " + port);
			
			server.awaitTermination();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}
	
	@Override
	public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
		System.out.println("receiving Login request");
		String name = request.getUsername();
		String password = request.getPassword();
		//System.out.println(name);
		//System.out.println(password);
		
		String loginresult;
		System.out.println(name);
		if(name == password){
			
			loginresult = "Login Success";
		}
		else {
			loginresult = "Login Failed";
		}
		
		LoginResponse reply = LoginResponse.newBuilder().setResponseMessage(request.getUsername() + " " + loginresult).build();
		responseObserver.onNext(reply);
	     
	    responseObserver.onCompleted();
	}
}