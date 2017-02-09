package by.intexsoft.ccm.controller;

import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.model.db.PackHistory;
import by.intexsoft.ccm.model.transfer.ProvisionRequestItem;
import by.intexsoft.ccm.model.transfer.ProvisionResponseItem;
import by.intexsoft.ccm.services.PackHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * REST Controller for provision of packages
 */
@RestController
@RequestMapping(value = "/provision")
public class ProvisionController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProvisionController.class);
	private PackHistoryService packHistoryService;

	@Autowired
	private ProvisionController(PackHistoryService packHistoryService)
	{
		this.packHistoryService = packHistoryService;
	}

	/**
	 * Provision of {@link Pack}
	 * @return identity item of {@link PackHistory}
	 */
	@RequestMapping(value = "/packs", method = POST, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	private ProvisionResponseItem provision(@RequestBody ProvisionRequestItem body)
	{
		LOGGER.info("Add pack history for pack: {} and subscriber {}", body.packId, body.subscriberId);
		ProvisionResponseItem response = new ProvisionResponseItem();
		response.id = packHistoryService.create(createProvisionHistory(body)).identity;
		return response;
	}

	private PackHistory createProvisionHistory(ProvisionRequestItem provisionRequestItem)
	{
		Pack pack = new Pack();
		pack.identity = provisionRequestItem.packId;
		PackHistory packHistory = new PackHistory();
		packHistory.action = provisionRequestItem.actionType;
		packHistory.endDate = provisionRequestItem.deactivateDate;
		packHistory.startDate = provisionRequestItem.activateDate;
		packHistory.traceNumber = provisionRequestItem.traceNumber;
		packHistory.subsId = provisionRequestItem.subscriberId;
		packHistory.pack = pack;
		return packHistory;
	}

}
