package br.com.mateusulrich.recipeservice.ingredient.api.controller;

import br.com.mateusulrich.recipeservice.ControllerTest;
import br.com.mateusulrich.recipeservice.api.controller.IngredientController;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = IngredientController.class)
class AddIngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void givenAValidInput_whenCallsAddIngredient_thenReturnIngredientResponse() throws Exception {
        IngredientInputData inputData = new IngredientInputData("Salt", "Salt", IngredientCategory.CONDIMENTS, Set.of(1, 2));
        IngredientResponse response = new IngredientResponse(1, "http://image.url", "Salt", "Salt", IngredientCategory.CONDIMENTS);

        when(ingredientService.createIngredient(inputData)).thenReturn(response);
        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Salt",
                            "description": "Salt",
                            "category": "CONDIMENTS",
                            "possibleUnits": [1, 2]
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "imageUrl": "http://image.url",
                            "name": "Salt",
                            "description": "Salt",
                            "category": "CONDIMENTS"
                        }
                        """))
                .andExpect(result -> {
                    URI location = new URI(Objects.requireNonNull(result.getResponse().getHeader("Location")));
                    assert location.equals(new URI("/ingredients/1"));
                });
    }
//
//    @Test
//    void givenAMalformedJson_whenCallsAddIngredient_thenReturn400BadRequest() throws Exception {
//        // obs = Falta uma vírgula entre description e category
//        String malformedJson = """
//            {
//                "name": "Salt",
//                "description": "Salt",
//                "category": "CONDIMENTS",
//                "possibleUnits": [1, 2]
//            }
//    """;
//
//        String jsonResponse = """
//    {
//        "status": 400,
//        "title": "Malformed JSON request",
//        "detail": "The request body is not readable or is incorrectly formatted. Please ensure that the request body is properly structured in JSON format"
//    }
//    """;
//        mockMvc.perform(post("/ingredients")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(malformedJson))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.title").value("Malformed JSON request"))
//                .andExpect(jsonPath("$.status").value(400))
//                .andExpect(jsonPath("$.detail").value("The request body is not readable or is incorrectly formatted. Please ensure that the request body is properly structured in JSON format"))
//                .andExpect(jsonPath("$.path").value("/api/v1/ingredients"))
//                .andExpect(jsonPath("$.validationErrors").isEmpty());
//    }

}

