package by.intexsoft.ccm.controller;

import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.services.PackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST Controller for packages managing
 */
@RestController
@RequestMapping(value = "/packs", produces = APPLICATION_JSON_VALUE)
public class PackController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackController.class);
    @Autowired
    private PackService packService;

    /**
     * Get list of all packs
     *
     * @param limit  - max count of packs
     * @param offset - for pagination
     * @return List of packs
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    private Iterable<Pack> load(@RequestParam(value = "limit", defaultValue = "9999") int limit,
                                @RequestParam(value = "offset", defaultValue = "0") int offset) {
        LOGGER.info("Load all packs with. Limit: {}, offset: {}", limit, offset);
        return packService.findAll(offset, limit);
    }

}
