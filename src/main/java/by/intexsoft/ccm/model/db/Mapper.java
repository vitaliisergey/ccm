package by.intexsoft.ccm.model.db;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

/**
 * Mapper represent relations between {@link Pack} and {@link Product}
 */
@Entity
@Table
@IdClass(MapperID.class)
public class Mapper implements Serializable
{

	private static final long serialVersionUID = -2744916996702137663L;

	/**
	 * {@link Pack} object
	 */
	@Id
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "PACK_ID")
	public Pack pack;

	/**
	 * {@link Product} object
	 */
	@Id
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	public Product product;
}

/**
 * IdClass for {@link Mapper}
 */
class MapperID implements Serializable
{
	private static final long serialVersionUID = -2744916996702137662L;
	private long pack;
	private long product;

	@Override
	public boolean equals(Object object)
	{
		if (object instanceof MapperID)
		{
			MapperID pk = (MapperID) object;
			return pack == pk.pack && product == pk.product;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(pack, product);
	}
}