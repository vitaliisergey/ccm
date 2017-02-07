package by.intexsoft.ccm.tests;

import by.intexsoft.ccm.TestDataHelper;
import by.intexsoft.ccm.exception.ActionException;
import by.intexsoft.ccm.model.db.Mapper;
import by.intexsoft.ccm.model.db.Product;
import by.intexsoft.ccm.repository.MapperRepository;
import by.intexsoft.ccm.services.impl.SubscriberServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Mockito.when;

public class SubscriberServiceTest {

    private MockMvc mockMvc;

    @Mock
    private MapperRepository mapperRepository;

    @InjectMocks
    private SubscriberServiceImpl subscriberService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(subscriberService)
                .build();
    }

    @Test(expected = ActionException.class)
    public void activateProductStartAfterActivationTest() throws Exception {
        Mapper mapper = new Mapper();
        Product product = new Product();
        product.start = TestDataHelper.convertJsonDateToDate("2017-05-23T18:25:43.511Z");
        mapper.product = product;
        Date activationDate = TestDataHelper.convertJsonDateToDate("2017-04-23T18:25:43.511Z");
        Date deactivationDate = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
        when(mapperRepository.find(1)).thenReturn(mapper);
        subscriberService.activate(1, 1, activationDate, deactivationDate);
    }

    @Test(expected = ActionException.class)
    public void activateProductEndEqualsDeactivationTest() throws Exception {
        Mapper mapper = new Mapper();
        Product product = new Product();
        product.start = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
        product.end = TestDataHelper.convertJsonDateToDate("2017-02-23T18:25:43.511Z");
        mapper.product = product;
        Date activationDate = TestDataHelper.convertJsonDateToDate("2017-04-23T18:25:43.511Z");
        Date deactivationDate = TestDataHelper.convertJsonDateToDate("2017-02-23T18:25:43.511Z");
        when(mapperRepository.find(1)).thenReturn(mapper);
        subscriberService.activate(1, 1, activationDate, deactivationDate);
    }

    @Test(expected = ActionException.class)
    public void activateProductEndBeforeDeactivationTest() throws Exception {
        Mapper mapper = new Mapper();
        Product product = new Product();
        product.start = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
        product.end = TestDataHelper.convertJsonDateToDate("2017-02-23T18:25:43.511Z");
        mapper.product = product;
        Date activationDate = TestDataHelper.convertJsonDateToDate("2017-04-23T18:25:43.511Z");
        Date deactivationDate = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
        when(mapperRepository.find(1)).thenReturn(mapper);
        subscriberService.activate(1, 1, activationDate, deactivationDate);
    }

    @Test(expected = ActionException.class)
    public void deactivateTest() throws Exception {
        Mapper mapper = new Mapper();
        Product product = new Product();
        product.end = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
        mapper.product = product;
        when(mapperRepository.find(1)).thenReturn(mapper);
        Date deactivationDate = TestDataHelper.convertJsonDateToDate("2017-03-23T18:25:43.511Z");
        subscriberService.deactivate(1, 1, 1, deactivationDate);
    }
}