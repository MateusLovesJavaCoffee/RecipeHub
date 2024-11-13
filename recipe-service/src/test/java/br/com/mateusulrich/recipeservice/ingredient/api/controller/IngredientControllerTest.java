package br.com.mateusulrich.recipeservice.ingredient.api.controller;

import br.com.mateusulrich.recipeservice.api.controller.IngredientController;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = IngredientController.class)
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void listAllIngredients_shouldReturnIngredientResponsePaginated() throws Exception {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id"));
        IngredientResponse ingredient1 = new IngredientResponse(1, null,"Sugar", "Sugar", IngredientCategory.CONDIMENTS);
        IngredientResponse ingredient2 = new IngredientResponse(2, null, "Salt", "Salt", IngredientCategory.CONDIMENTS);

        Page<IngredientResponse> ingredientPage = new PageImpl<>(List.of(ingredient1, ingredient2), pageable, 2);

        when(ingredientService.listAllIngredients(any(), any()))
                .thenReturn(ingredientPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/ingredients")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "content": [
                                {
                                    "id": 1,
                                    "name": "Sugar",
                                    "description": "Sugar",
                                    "category": "CONDIMENTS"
                                },
                                {
                                    "id": 2,
                                    "name": "Salt",
                                    "description": "Salt",
                                    "category": "CONDIMENTS"
                                }
                            ],
                            "pageable": {
                                "sort": {
                                    "empty": false,
                                    "sorted": true,
                                    "unsorted": false
                                },
                                "offset": 0,
                                "pageNumber": 0,
                                "pageSize": 20,
                                "paged": true,
                                "unpaged": false
                            },
                            "last": true,
                            "totalElements": 2,
                            "totalPages": 1,
                            "size": 20,
                            "number": 0,
                            "sort": {
                                "empty": false,
                                "sorted": true,
                                "unsorted": false
                            },
                            "first": true,
                            "numberOfElements": 2,
                            "empty": false
                        }
                        """));
    }
    @Test
    void doNothing_whenListAllIngredients_shouldReturnAEmptyList() throws Exception {
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "id"));
        Page<IngredientResponse> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(ingredientService.listAllIngredients(any(), any(Pageable.class)))
                .thenReturn(emptyPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/ingredients")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "id,asc"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "content": [],
                            "pageable": {
                                "sort": {
                                    "empty": false,
                                    "sorted": true,
                                    "unsorted": false
                                },
                                "offset": 0,
                                "pageNumber": 0,
                                "pageSize": 20,
                                "paged": true,
                                "unpaged": false
                            },
                            "last": true,
                            "totalElements": 0,
                            "totalPages": 0,
                            "size": 20,
                            "number": 0,
                            "sort": {
                                "empty": false,
                                "sorted": true,
                                "unsorted": false
                            },
                            "first": true,
                            "numberOfElements": 0,
                            "empty": true
                        }
                        """));


    }
}

