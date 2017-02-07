package by.intexsoft.ccm.services.impl;

import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.model.db.PackHistory;
import by.intexsoft.ccm.model.db.Pack_;
import by.intexsoft.ccm.model.transfer.PackHistoryItem;
import by.intexsoft.ccm.repository.PackHistoryRepository;
import by.intexsoft.ccm.services.PackHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements service for {@link PackHistoryService}
 */
@Service
public class PackHistoryServiceImpl implements PackHistoryService {

    @Autowired
    private PackHistoryRepository packHistoryRepository;

    @Override
    public PackHistory create(PackHistory packHistory) {
        return packHistoryRepository.save(packHistory);
    }

    @Override
    public List<PackHistory> find(long subscriberId, int offset, int limit) {
        PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.ASC, Pack_.name.getName());
        return packHistoryRepository.findBySubsId(subscriberId, pageRequest).getContent();
    }

    @Override
    public List<PackHistoryItem> findHistoryItems(long subscriberId, int offset, int limit) {
        List<Number> longs = packHistoryRepository.find(subscriberId, offset, limit);
        List<Long> identities = longs.stream().map(item -> item.longValue()).collect(Collectors.toList());
        List<PackHistory> packHistories = packHistoryRepository.find(identities);
        return packHistories.stream().map(packHistory -> {
            PackHistoryItem packHistoryItem = new PackHistoryItem();
            packHistoryItem.name = packHistory.pack.name;
            packHistoryItem.packId = packHistory.pack.identity;
            packHistoryItem.start = packHistory.startDate;
            packHistoryItem.end = packHistory.endDate;
            packHistoryItem.type = packHistory.action;
            return packHistoryItem;
        }).collect(Collectors.toList());
    }
}
