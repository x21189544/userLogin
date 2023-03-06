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
		System.out.println(name);
		System.out.println(password);
		
		String loginResult;
		int loginErrorCode;
		if((name.equals("Divyaa")) && (password.equals("Password123")) ) {
			loginResult = "Login Success";
			loginErrorCode = 1;
		}
		else {
			loginResult = "Login Failure";
			loginErrorCode = 2;
		}
	
		LoginResponse reply = LoginResponse.newBuilder().setResponseMessage("Username: " + request.getUsername() + " -- " + loginResult).setResponseCode(loginErrorCode).build();
		responseObserver.onNext(reply);
	     
	    responseObserver.onCompleted();
	}
	
	public void logout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
		System.out.println("receiving Logout request");
		String name = request.getUsername();
		
		String logoutResult;
		int logoutErrorCode;
		if(name.equals("Divyaa")) {
			logoutResult = "Logout Success";
			logoutErrorCode = 1;
		}
		else {
			logoutResult = "User Not Logged In";
			logoutErrorCode = 2;
		}
		System.out.println("Logout Error Code is: " + logoutErrorCode);
		LogoutResponse reply = LogoutResponse.newBuilder().setResponseMessage(logoutResult).setResponseCode(logoutErrorCode).build();
		
		responseObserver.onNext(reply);
	     
	    responseObserver.onCompleted();
	}
}