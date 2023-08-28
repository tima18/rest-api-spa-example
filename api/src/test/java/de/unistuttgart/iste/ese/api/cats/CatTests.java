package de.unistuttgart.iste.ese.api.cats;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CatTests {

    @Autowired
    private MockMvc mockMvc;

    // Example of how to mock repository to test only the controller. The Spring
    // annotation that depends on mockito framework
    @MockBean
    private CatRepository catRepository;

    @Test
    @DisplayName("Expect two elements in the returned JSON array. JPA repository is mocked")
    void testReturningTheListOfCats_dbMocked() throws Exception {

        List<Cat> allCats = new ArrayList<Cat>();
        Cat octocat = new Cat("Octocat", 42, "");
        Cat grumpyCat = new Cat("Grumpy Cat", 10, "");
        allCats.add(octocat);
        allCats.add(grumpyCat);

        // mocks the underneath call to JPA repository
        BDDMockito.given(catRepository.findAll()).willReturn(allCats);

        // performs a get request
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cats"));

        // expected status code
        result.andExpect(MockMvcResultMatchers.status().isOk());
        // expected string in the content
        result.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Octocat")));
        result.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Grumpy Cat")));
        // $ <- indicates the json root element
        result.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)));
    }
}
