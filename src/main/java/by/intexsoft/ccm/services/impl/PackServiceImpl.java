package by.intexsoft.ccm.services.impl;

import by.intexsoft.ccm.model.db.Pack;
import by.intexsoft.ccm.model.db.Pack_;
import by.intexsoft.ccm.repository.PackRepository;
import by.intexsoft.ccm.services.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements service for {@link PackService}
 */
@Service
public class PackServiceImpl implements PackService
{
	@Autowired
	private PackRepository packRepository;

	@Override
	public List<Pack> findAll(int offset, int limit)
	{
		PageRequest pageRequest = new PageRequest(offset, limit, Sort.Direction.ASC, Pack_.name.getName());
		return packRepository.findAll(pageRequest).getContent();
	}
}
