package com.teamB.Tshirtapp.Controller;

import com.teamB.Entity.TshirtOrder;
import com.teamB.Service.TshirtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TshirtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TshirtService tshirtService;

    @Test
    public void testOrderTshirtSuccess() throws Exception {
        TshirtOrder order = new TshirtOrder();
        order.setSize("M");
        order.setEmail("test@example.com");
        order.setName("John Doe");
        order.setAddress1("123 Main St");
        order.setCity("Anytown");
        order.setStateOrProvince("State");
        order.setPostalCode("12345");
        order.setCountry("Country");

        Mockito.when(tshirtService.orderTshirt(Mockito.any(TshirtOrder.class))).thenReturn(order);

        mockMvc.perform(post("/api/tshirts/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testOrderTshirtInvalidEmail() throws Exception {
        TshirtOrder order = new TshirtOrder();
        order.setSize("M");
        order.setEmail("invalid-email");
        order.setName("John Doe");
        order.setAddress1("123 Main St");
        order.setCity("Anytown");
        order.setStateOrProvince("State");
        order.setPostalCode("12345");
        order.setCountry("Country");

        mockMvc.perform(post("/api/tshirts/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").value("Invalid email address"));
    }

    @Test
    public void testOrderTshirtMissingRequiredFields() throws Exception {
        TshirtOrder order = new TshirtOrder();
        order.setSize("M");
        order.setEmail("test@example.com");
        order.setName("");
        order.setAddress1("");
        order.setCity("Anytown");
        order.setStateOrProvince("State");
        order.setPostalCode("12345");
        order.setCountry("Country");

        mockMvc.perform(post("/api/tshirts/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0]").value("Name is required"))
                .andExpect(jsonPath("$[1]").value("Address1 is required"));
    }

    @Test
    public void testOrderTshirtInternalServerError() throws Exception {
        TshirtOrder order = new TshirtOrder();
        order.setSize("M");
        order.setEmail("test@example.com");
        order.setName("John Doe");
        order.setAddress1("123 Main St");
        order.setCity("Anytown");
        order.setStateOrProvince("State");
        order.setPostalCode("12345");
        order.setCountry("Country");

        Mockito.when(tshirtService.orderTshirt(Mockito.any(TshirtOrder.class))).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(post("/api/tshirts/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(order)))
                .andExpect(status().isInternalServerError());
    }
}
