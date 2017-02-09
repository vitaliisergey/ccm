package by.intexsoft.ccm.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for package
 */
@Entity
@Table(name = "PACKS")
public class Pack extends AbstractEntity
{

	/**
	 * Package name
	 */
	@Column(nullable = false, unique = true)
	public String name;
}