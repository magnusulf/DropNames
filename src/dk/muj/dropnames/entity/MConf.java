package dk.muj.dropnames.entity;

import java.util.Map;

import org.bukkit.Material;

import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;

public class MConf extends Entity<MConf>
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	protected static transient MConf i;
	public static MConf get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE: ENTITY
	// -------------------------------------------- //
	
	@Override
	public MConf load(MConf that)
	{
		if (that == null || that == this) return that;
		
		this.isEnabled = that.isEnabled;
		this.displayAmount = that.displayAmount;
		this.worldEnabled = that.worldEnabled;
		
		return this;
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public boolean isEnabled = true;
	public boolean displayAmount = false;
	public boolean allowCustomNames = true;
	
	public WorldExceptionSet worldEnabled = new WorldExceptionSet();
	
	private Map<Material, String> defaultCustomNames = MUtil.map
			(
			Material.DIAMOND_SWORD, "Sword of diamond",
			Material.BOOK, "Book of wisdom"
			);
	
	public String getDefaultName(Material type)
	{
		if (type == null) throw new IllegalArgumentException("tpye mustn't be null");
		String ret = this.defaultCustomNames.get(type);
		if (ret == null) ret = Txt.getMaterialName(type);
		return ret;
	}
	
}


