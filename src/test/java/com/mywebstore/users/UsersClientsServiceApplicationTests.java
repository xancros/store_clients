package com.mywebstore.users;

import com.mywebstore.users.model.CustomResponseObject;
import com.mywebstore.users.model.UserModel;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

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


	private RestTemplate restTemplate;
	//private WebClient webClient;
	protected CustomResponseObject responseMessage;
	//protected Mono<CustomResponseObject> responseObject;
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

	public void executeLogin(String username, String password){

		Map<String,String> entry = new HashMap<>();
		entry.put("username",username);
		entry.put("password",password);
		ResponseEntity<CustomResponseObject> response = restTemplate.postForEntity(userLoginEndpoint(), entry, CustomResponseObject.class);
		responseMessage=response.getBody();

	}

	public int getUsers(){
		Integer numUsers = restTemplate.getForObject(userEndpoint()+"/all",Integer.class);
		return numUsers!=null?numUsers:0;
	}

	public void executeCreateUser(UserModel userModel){

		responseMessage=restTemplate.postForEntity(userRegisterEndpoint(),userModel,CustomResponseObject.class).getBody();
		/*responseMessage=webClient.post()
				.uri(userRegisterEndpoint())
				.bodyValue(userModel)
				.retrieve()
				.bodyToMono(CustomResponseObject.class)
				.block();*/

	}

	void executeRemoveUser(String username, String password, String idCard){
		Map<String,String> body = Map.of(
				"userName",username,
				"password",password,
				"idCard",idCard
		);

		responseMessage = restTemplate.postForEntity(removeUserEndpoint(),body,CustomResponseObject.class).getBody();
		/*responseMessage=webClient.post()
				.uri(removeUserEndpoint())
				.bodyValue(body)
				.retrieve()
				.bodyToMono(CustomResponseObject.class)
				.block();*/
	}

	public void executeModifyUser(UserModel userModel){
		responseMessage = restTemplate.postForEntity(modifyUserEndpoint(),userModel,CustomResponseObject.class).getBody();
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
