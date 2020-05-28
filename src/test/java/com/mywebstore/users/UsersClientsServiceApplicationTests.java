package com.mywebstore.users;

import com.mywebstore.users.entity.User;
import com.mywebstore.users.model.UserModel;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class UsersClientsServiceApplicationTests {

	private final String SERVER_URL = "http://localhost:";
	private final String USER_ENDPOINT = "/user";
	private final String LOGIN_ENDPOINT = "/login";
	private final String REGISTER_ENDPOINT = "/register";


	private RestTemplate restTemplate;
	private WebClient client;
	protected CustomResponseObject responseMessage;

	@LocalServerPort
	protected int port;

	public UsersClientsServiceApplicationTests(){
		restTemplate = new RestTemplate();
		client = WebClient.create();
	}

	private String userEndpoint(){return SERVER_URL+port+USER_ENDPOINT;}

	private String userLoginEndpoint(){
		return userEndpoint()+LOGIN_ENDPOINT;
	}

	private String userRegisterEndpoint(){
		return userEndpoint()+REGISTER_ENDPOINT;
	}

	public void executeLogin(String username, String password){
		Map<String, String> entry = new HashMap<>();
		entry.put(username,password);
		WebClient.Builder builder = WebClient.builder().baseUrl(userLoginEndpoint()).
				defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		WebClient webClient = builder.build();
		Mono<CustomResponseObject> respuesta1 = webClient.post().body(BodyInserters.fromValue(entry)).exchange().flatMap( x ->
		{
			if ( ! x.statusCode().is2xxSuccessful()){
				responseMessage = new CustomResponseObject();
				responseMessage.setCode(x.statusCode());
				responseMessage.setMessage("status code is incorrect : "+x.statusCode()+"\n");
				return 	Mono.just(responseMessage);
			}
			return x.bodyToMono(CustomResponseObject.class);
		});

	}

	public int getUsers(){
		return restTemplate.getForObject(userEndpoint(),Integer.class);
	}

	public void executeCreateUser(UserModel userModel){

		WebClient.Builder builder = WebClient.builder().baseUrl(userRegisterEndpoint()).
				defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		WebClient webClient = builder.build();
		Mono<CustomResponseObject> respuesta1 = webClient.post().body(BodyInserters.fromValue(userModel)).exchange().flatMap( x ->
		{
			if ( ! x.statusCode().is2xxSuccessful()){
				responseMessage = new CustomResponseObject();
				responseMessage.setCode(x.statusCode());
				responseMessage.setMessage("status code is incorrect : "+x.statusCode()+"\n");
				return 	Mono.just(responseMessage);
			}
			return x.bodyToMono(CustomResponseObject.class);
		});


	}

	void cleanLogin(){restTemplate.delete(userLoginEndpoint());}

	void cleanRegister(){restTemplate.delete(userRegisterEndpoint());}

	void clean(){
		responseMessage=null;
		cleanLogin();
		cleanRegister();
	}




}
