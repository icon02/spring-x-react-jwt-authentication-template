package io.github.icon02.springbootauthenticationtemplate;

import io.github.icon02.springbootauthenticationtemplate.model.User;
import io.github.icon02.springbootauthenticationtemplate.testData.UserData;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringbootAuthenticationTemplateApplication.class
)
public class MvcAuthenticationTests {

    @Autowired
    private MockMvc mvc;
    private List<User> testUsers = new ArrayList<>(UserData.getTestUsers());

    @Test
    void testSimpleLogin() {
        User testUser = testUsers.get(0);
        String testAgentId = UUID.randomUUID().toString();

    }

    @Test
    void testAdminAuthentication() throws Exception {
        User admin = null;
        for(User u : testUsers) {
            if(u.getAuthorities().contains("admin")) {
                admin = u;
                break;
            }
        }
        if(admin == null) throw new Exception("Could not find any admin user in testUsers!");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String agentId = UUID.randomUUID().toString();
        params.add("agentId", agentId);
        params.add("email", admin.getEmail());
        params.add("password", admin.getPassword());

        mvc.perform(post("/auth/login").params(params))
                .andExpect(status().is(200))
                .andDo(mvcResult -> {});
    }
}
