package by.intexsoft.ccm.tests;

import by.intexsoft.ccm.TestDataHelper;
import by.intexsoft.ccm.controller.SubscriberController;
import by.intexsoft.ccm.model.transfer.PackHistoryItem;
import by.intexsoft.ccm.services.PackHistoryService;
import by.intexsoft.ccm.services.SubscriberService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SubscriberControllerTest
{

	private MockMvc mockMvc;

	@Mock
	private SubscriberService subscriberService;

	@Mock
	private PackHistoryService packHistoryService;

	@InjectMocks
	private SubscriberController subscriberController;

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(subscriberController).build();
	}

	@Test
	public void getAllPacksOfSubscriberWithParamsTest() throws Exception
	{
		List<PackHistoryItem> historyPacks = TestDataHelper.getPackHistoryItemList(2);
		when(packHistoryService.findHistoryItems(1, 0, 5)).thenReturn(historyPacks);
		mockMvc.perform(get("/subscriber/{id}/packs", 1).param("limit", "5").param("offset", "0")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].packId", is(1))).andExpect(jsonPath("$[0].name", is("Test")))
				.andExpect(jsonPath("$[1].packId", is(1))).andExpect(jsonPath("$[1].name", is("Test")));
		verify(packHistoryService, times(1)).findHistoryItems(1, 0, 5);
		verifyNoMoreInteractions(packHistoryService);
	}

	@Test
	public void getAllPacksOfSubscriberWithoutParamsTest() throws Exception
	{
		List<PackHistoryItem> historyPacks = TestDataHelper.getPackHistoryItemList(2);
		when(packHistoryService.findHistoryItems(1, 0, 9999)).thenReturn(historyPacks);
		mockMvc.perform(get("/subscriber/{id}/packs", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].packId", is(1))).andExpect(jsonPath("$[0].name", is("Test")))
				.andExpect(jsonPath("$[1].packId", is(1))).andExpect(jsonPath("$[1].name", is("Test")));
		verify(packHistoryService, times(1)).findHistoryItems(1, 0, 9999);
		verifyNoMoreInteractions(packHistoryService);
	}

	@Test
	public void connectPackToSubscriberTest() throws Exception
	{
		String jsonResponse = "{\"id\":1,\"conflict\":null}";
		String jsonContent = "{\n" + "\"packId\": 1,\n" + "\"activateDate\": \"2017-04-23T18:25:43.511Z\",\n" +
				"\"deactivateDate\": \"2017-03-23T18:25:43.511Z\"\n" + "}";
		Date activationDate = TestDataHelper.convertJsonDateToDate("2017-04-23T18:25:43.511Z");
		Date deactivationDate = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
		when(subscriberService.activate(1, 1, activationDate, deactivationDate)).thenReturn(1L);
		mockMvc.perform(
				post("/subscriber/{id}/packs/activate", "1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonContent))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(jsonResponse));
		verify(subscriberService, times(1)).activate(1, 1, activationDate, deactivationDate);
		verifyNoMoreInteractions(subscriberService);
	}

	@Test
	public void disconnectPackFromSubscriberTest() throws Exception
	{
		String jsonResponse = "{\"id\":1,\"conflict\":null}";
		String jsonContent =
				"{\n" + "\"packId\": 1,\n" + "\"traceNumber\": 1,\n" + "\"deactivateDate\": \"2017-03-23T18:25:43.511Z\"\n" + "}";
		Date deactivationDate = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
		when(subscriberService.deactivate(1, 1, 1, deactivationDate)).thenReturn(1L);
		mockMvc.perform(
				post("/subscriber/{id}/packs/deactivate", "1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonContent))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().json(jsonResponse));
		verify(subscriberService, times(1)).deactivate(1, 1, 1, deactivationDate);
		verifyNoMoreInteractions(subscriberService);
	}
}