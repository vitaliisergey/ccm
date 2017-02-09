package by.intexsoft.ccm.services;

import by.intexsoft.ccm.model.db.Pack;

import java.util.List;

/**
 * Service for packs
 */
public interface PackService
{

	/**
	 * Find all {@link Pack}s
	 * @return {@link List} of {@link Pack}
	 */
	List<Pack> findAll(int offset, int limit);
}
