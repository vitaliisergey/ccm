package by.intexsoft.ccm.tests;

import by.intexsoft.ccm.TestDataHelper;
import by.intexsoft.ccm.controller.ProvisionController;
import by.intexsoft.ccm.model.db.PackHistory;
import by.intexsoft.ccm.services.PackHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProvisionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PackHistoryService packHistoryService;

    @InjectMocks
    private ProvisionController provisionController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(provisionController)
                .build();
    }

    @Test
    public void createPackHistoryTest() throws Exception {
        String jsonContent = "{\"actionType\": \"ACTIVATE\", \"subscriberId\": \"1\", \"traceNumber\": \"1\", \"packId\": \"1\"}";
        PackHistory packHistory = TestDataHelper.createPackHistory();
        when(packHistoryService.create(any(PackHistory.class))).thenReturn(packHistory);
        mockMvc.perform(post("/provision/packs").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                jsonContent
        ))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("1"));
        verify(packHistoryService, times(1)).create(any(PackHistory.class));
        verifyNoMoreInteractions(packHistoryService);
    }
}