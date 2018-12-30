package dk.muj.dropnames;

import com.massivecraft.massivecore.util.Txt;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.massivecraft.massivecore.MassivePlugin;

import dk.muj.dropnames.entity.MConf;
import dk.muj.dropnames.entity.MConfColl;

public class DropNames extends MassivePlugin
{	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static DropNames i;
	public static DropNames get() { return i; }
	public DropNames() { i = this; }
	
	// -------------------------------------------- //
	// OVERRIDE: PLUGIN
	// -------------------------------------------- //
	
	@Override
	public void onEnableInner()
	{
		activate(
			MConfColl.class
		);
	}
	
	// -------------------------------------------- //
	// LISTENER
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void setItemName(ItemSpawnEvent event)
	{
		if ( ! MConf.get().isEnabled) return;
		if ( ! MConf.get().worldsEnabled.contains(event.getEntity().getLocation().getWorld())) return;
		
		Item item = event.getEntity();
		ItemStack stack = item.getItemStack();

		String name = getCustomName(stack);
		if (name == null) return;
		
		item.setCustomName(name);
		item.setCustomNameVisible(true);
	}
	
	public static String getCustomName(ItemStack stack)
	{
		String ret;

		ItemMeta meta = stack.getItemMeta();

		if (meta.hasDisplayName() && MConf.get().displayRenamed)
		{
			ret = meta.getDisplayName();
		}
		else if (MConf.get().displayNotRenamed)
		{
			String rename = MConf.get().customNames.get(stack.getType());
			if (rename != null)
			{
				ret = rename;
			}
			else
			{
				ret = Txt.getMaterialName(stack.getType());
			}
		}
		else
		{
			return null;
		}
		
		if (stack.getAmount() > 1 && MConf.get().displayAmount)
		{
			ret += " x" + String.valueOf(stack.getAmount());
		}
		
		return ret;
	}
	
}
