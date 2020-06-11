package com.mywebstore.users;

import com.mywebstore.users.model.AuthenticationResponse;
import com.mywebstore.users.model.CustomResponseObject;
import com.mywebstore.users.model.UserModel;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class UsersClientsServiceApplicationTests {

	private final String SERVER_URL = "http://localhost:";
	private final String USER_ENDPOINT = "/user";
	private final String LOGIN_ENDPOINT = "/login";
	private final String REGISTER_ENDPOINT = "/register";
	private final String DELETE_USER_ENDPOINT = "/deleteUser";
	private final String MODIFY_USER_ENDPOINT = "/modify";
	private final String AUTHENTICATE_ENDPOINT = "/authenticate";

	private final RestTemplate restTemplate;
	//private WebClient webClient;
	protected CustomResponseObject responseMessage;
	//protected Mono<CustomResponseObject> responseObject;
	protected ResponseEntity<AuthenticationResponse> responseEntity;
	@LocalServerPort
	protected int port;

	public UsersClientsServiceApplicationTests(){
		restTemplate = new RestTemplate();
		//webClient=WebClient.builder().build();
		//client = WebClient.create(userEndpoint());
	}

	private String userEndpoint(){return SERVER_URL+port+USER_ENDPOINT;}

	private String userLoginEndpoint(){
		return userEndpoint()+LOGIN_ENDPOINT;
	}

	private String userRegisterEndpoint(){
		return userEndpoint()+REGISTER_ENDPOINT;
	}

	private String removeUserEndpoint(){return userEndpoint()+DELETE_USER_ENDPOINT;}

	private String modifyUserEndpoint(){return userEndpoint()+MODIFY_USER_ENDPOINT;}

	private String authenticateEndpoint(){return SERVER_URL+port+AUTHENTICATE_ENDPOINT;}

	public void executeAuthenticate(String username, String password){
		Map<String,String> entry = new HashMap<>();
		entry.put("username",username);
		entry.put("password",password);
		try {
			responseEntity = restTemplate.postForEntity(authenticateEndpoint(), entry, AuthenticationResponse.class);
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			responseEntity.getBody();

		}
	}

	public HttpHeaders generateAuthenticationHeader(String jwtToken){
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	public void executeLogin(String username, String password,String jwtToken){

		Map<String,String> entry = new HashMap<>();
		entry.put("username",username);
		entry.put("password",password);

		HttpEntity<Map<String,String>> request = new HttpEntity<>(entry,generateAuthenticationHeader(jwtToken));
		//ResponseEntity<CustomResponseObject> response = restTemplate.exchange();
		ResponseEntity<CustomResponseObject> response = restTemplate.postForEntity(userLoginEndpoint(), request, CustomResponseObject.class);

		responseMessage=response.getBody();

	}

	public int getUsers(String jwtToken){
		HttpEntity<String> request = new HttpEntity<>("entry",generateAuthenticationHeader(jwtToken));
		Integer numUsers = restTemplate.exchange(userEndpoint()+"/all",HttpMethod.GET,request,Integer.class).getBody();
		return numUsers!=null?numUsers:0;
	}

	public void executeCreateUser(UserModel userModel,String jwtToken){
		HttpEntity<UserModel> request = new HttpEntity<>(userModel,generateAuthenticationHeader(jwtToken));
		responseMessage=restTemplate.exchange(userRegisterEndpoint(),HttpMethod.POST,request,CustomResponseObject.class).getBody();
		/*responseMessage=webClient.post()
				.uri(userRegisterEndpoint())
				.bodyValue(userModel)
				.retrieve()
				.bodyToMono(CustomResponseObject.class)
				.block();*/

	}

	void executeRemoveUser(String username, String password, String idCard,String jwtToken){
		Map<String,String> body = Map.of(
				"userName",username,
				"password",password,
				"idCard",idCard
		);
		HttpEntity<Map<String,String>> request = new HttpEntity<>(body,generateAuthenticationHeader(jwtToken));
		responseMessage=restTemplate.exchange(removeUserEndpoint(),HttpMethod.POST,request,CustomResponseObject.class).getBody();
		/*responseMessage=webClient.post()
				.uri(removeUserEndpoint())
				.bodyValue(body)
				.retrieve()
				.bodyToMono(CustomResponseObject.class)
				.block();*/
	}

	public void executeModifyUser(UserModel userModel,String jwtToken){
		HttpEntity<UserModel> request = new HttpEntity<>(userModel,generateAuthenticationHeader(jwtToken));
		responseMessage=restTemplate.exchange(modifyUserEndpoint(),HttpMethod.POST,request,CustomResponseObject.class).getBody();
		/*responseMessage=webClient.patch()
				.uri(modifyUserEndpoint())
				.bodyValue(userModel)
				.retrieve()
				.bodyToMono(CustomResponseObject.class)
				.block();*/

	}

	void assertCode(int code){
		assertThat(responseMessage.getCode().value()).isEqualTo(code);
	}

	void cleanLogin(){restTemplate.delete(userLoginEndpoint());responseMessage=null;}

	void cleanRegister(){restTemplate.delete(userRegisterEndpoint());responseMessage=null;}

	void clean(){
		responseMessage=null;
		try {
			cleanLogin();
		}catch (Exception e){e.printStackTrace();}
		try {
			cleanRegister();
		}catch (Exception ex){ex.printStackTrace();}
	}




}
