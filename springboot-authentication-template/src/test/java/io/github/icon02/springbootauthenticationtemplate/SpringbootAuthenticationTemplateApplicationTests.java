package io.github.icon02.springbootauthenticationtemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.icon02.springbootauthenticationtemplate.controller.AuthenticationController;
import io.github.icon02.springbootauthenticationtemplate.model.User;
import io.github.icon02.springbootauthenticationtemplate.testData.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringbootAuthenticationTemplateApplication.class
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpringbootAuthenticationTemplateApplicationTests {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
    private List<User> testUsers = new ArrayList<>(UserData.getTestUsers());

    private User adminUser = testUsers.get(0); // @see testUsers.class
    private String adminAgentId = "Admin agent ID\nNOTE: not a real agentID!";
    private String adminJwt;
    private String adminSut;

    private User standardUser;
    private String standardAgentId = "Standard agentID\nNOTE: not a real agentID!";
    private String standardJwt;
    private String standardSut;

	/*
	private User guestUser;
	private String guestJwt;
	private String guestSut;
	 */

    @BeforeAll
    void setup() throws Exception {
        // init standard user;
        standardUser = new User();
        standardUser.setEmail("volleyball-hirschbach@gmail.com");
        standardUser.setPassword("test-hirschbach");

        // log in admin user
        AuthenticationController.TokenPayload payload = login(adminUser.getEmail(), adminUser.getPassword(), adminAgentId);
        adminJwt = payload.getJwt();
        adminSut = payload.getSut();

        // create standard user
		String standardPassword = standardUser.getPassword();
        standardUser = createAccount(standardUser.getEmail(), standardUser.getPassword());
        standardUser.setPassword(standardPassword);
        // log in standard user
		System.out.println("email: " + standardUser.getEmail());
		System.out.println("password: " + standardUser.getPassword());
		System.out.println("agentId: " + standardAgentId);
        payload = login(standardUser.getEmail(), standardUser.getPassword(), standardAgentId);
        standardJwt = payload.getJwt();
        standardSut = payload.getSut();
    }

    private User createAccount(String email, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("password", password);

        final String[] contentArr = new String[1];
        mvc.perform(post("/account/create").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(result -> {
                    contentArr[0] = result.getResponse().getContentAsString();
                });
        String content = contentArr[0];

        return objectMapper.readValue(content, User.class);
    }

    private AuthenticationController.TokenPayload login(String email, String password, String agentId) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", email);
        params.add("password", password);
        params.add("agentId", agentId);

        final String[] contentArr = new String[1];
        mvc.perform(post("/auth/login").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(result -> {
                    contentArr[0] = result.getResponse().getContentAsString();
                });
        String content = contentArr[0];

        return objectMapper.readValue(content, AuthenticationController.TokenPayload.class);
    }

    @Test
    void testTriggerArrayIndexOutOfBounds() throws Exception {
        mvc.perform(
                get("/test/triggerArrayIndexOutOfBounds")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(500))
                .andExpect(jsonPath("cErr").value(25));
    }

    @Test
    void testStandardUserSecureController() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.put("Authorization", Collections.singletonList("Bearer " + standardJwt));

		// without jwt authorization
		mvc.perform(get("/secure"))
				// .andDo(print())
		.andExpect(status().is(403));

		// with jwt authorization
		mvc.perform(get("/secure").headers(headers))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("value").value("Secure"));
    }

    //@Test
    void testChangeEmail() throws Exception {
        mvc.perform(
                post("/account/testAcc/action=\"updateEmail\"")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void testAdminAuthentication() throws Exception {
        User admin = testUsers.get(0);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String agentId = UUID.randomUUID().toString();
        params.add("agentId", agentId);
        params.add("email", admin.getEmail());
        params.add("password", admin.getPassword());

        final String[] responseArr = new String[1];
        mvc.perform(post("/auth/login").params(params))
                .andDo(print())
                .andExpect(status().is(200))
                .andDo(mvcResult -> {
                    responseArr[0] = mvcResult.getResponse().getContentAsString();
                });
        String response = responseArr[0];
        AuthenticationController.TokenPayload payload = objectMapper.readValue(response, AuthenticationController.TokenPayload.class);

        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", Collections.singletonList("Bearer " + payload.getJwt()));

        // test admin without bearer token
        mvc.perform(get("/admin"))
                // .andDo(print())
                .andExpect(status().is(403));

        // test admin with bearer token
        mvc.perform(get("/admin").headers(headers))
                // .andDo(print())
                .andExpect(status().is(200));

        // test secure without bearer token
        mvc.perform(get("/secure"))
                // .andDo(print())
                .andExpect(status().is(403));

        // test secure with bearer token
        mvc.perform(get("/secure").headers(headers))
                // .andDo(print())
                .andExpect(status().is(200));

    }
}
