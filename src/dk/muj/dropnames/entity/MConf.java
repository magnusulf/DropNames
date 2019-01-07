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

	public boolean isEnabledItem = true;
	public boolean isEnabledExperienceOrb = true;

	public boolean displayItemAmount = false;
	public boolean displayItemRenamed = true;
	public boolean displayItemNotRenamed = true;

	public boolean displayExperienceOrbAmount = true;

	public WorldExceptionSet worldsEnabled = new WorldExceptionSet();
	
	public Map<Material, String> customNames = MUtil.map
			(
			Material.DIAMOND_SWORD, "Sword of diamond",
			Material.BOOK, "Book of wisdom"
			);

	public String experienceOrbName = "Exp";
	
}


