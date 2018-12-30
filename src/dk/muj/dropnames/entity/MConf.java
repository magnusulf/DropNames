package dk.muj.dropnames.entity;

import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;

import java.util.Map;

public class MConf extends Entity<MConf>
{
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	protected static transient MConf i;
	public static MConf get() { return i; }

	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public boolean isEnabled = true;
	public boolean displayAmount = false;
	public boolean displayRenamed = true;
	public boolean displayNotRenamed = true;
	
	public WorldExceptionSet worldsEnabled = new WorldExceptionSet();
	
	public Map<Material, String> customNames = MUtil.map
			(
			Material.DIAMOND_SWORD, "Sword of diamond",
			Material.BOOK, "Book of wisdom"
			);
	
}


