package br.com.datum.prova.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerAdviceHandler.class)
@Import(ControllerAdviceHandler.class)
class ControllerAdviceHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "teste_aut", password = "teste_aut")
    void testHandleGenericException() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode", is("INTERNAL_SERVER_ERROR")))
                .andExpect(jsonPath("$.message", is("An unexpected error occurred")));
    }
}
