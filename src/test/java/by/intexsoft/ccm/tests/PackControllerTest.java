package by.intexsoft.ccm.tests;

import by.intexsoft.ccm.TestDataHelper;
import by.intexsoft.ccm.controller.PackController;
import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.services.PackService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PackControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PackService packService;

    @InjectMocks
    private PackController packController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(packController)
                .build();
    }

    @Test
    public void getAllPacksWithParamsTest() throws Exception {
        List<Pack> packs = TestDataHelper.getPackList(2);
        when(packService.findAll(0, 5)).thenReturn(packs);
        mockMvc.perform(get("/packs").param("limit", "5")
                .param("offset", "0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].identity", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test")))
                .andExpect(jsonPath("$[1].identity", is(1)))
                .andExpect(jsonPath("$[1].name", is("Test")));

        verify(packService, times(1)).findAll(0, 5);
        verifyNoMoreInteractions(packService);
    }

    @Test
    public void getAllPacksWithoutParamsTest() throws Exception {
        List<Pack> packs = TestDataHelper.getPackList(2);
        when(packService.findAll(0, 9999)).thenReturn(packs);
        mockMvc.perform(get("/packs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].identity", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test")))
                .andExpect(jsonPath("$[1].identity", is(1)))
                .andExpect(jsonPath("$[1].name", is("Test")));

        verify(packService, times(1)).findAll(0, 9999);
        verifyNoMoreInteractions(packService);
    }
}