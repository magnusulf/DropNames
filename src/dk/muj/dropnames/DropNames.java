package dk.muj.dropnames;

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
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void onEnable()
	{
		if ( ! preEnable()) return;
		
		MConfColl.get().init();
		
		this.postEnable();
	}
	
	// -------------------------------------------- //
	// LISTENER
	// -------------------------------------------- //
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void setItemName(ItemSpawnEvent event)
	{
		if ( ! MConf.get().isEnabled) return;
		if ( ! MConf.get().worldEnabled.contains(event.getEntity().getLocation().getWorld())) return;
		
		Item item = event.getEntity();
		ItemStack stack = item.getItemStack();
		ItemMeta meta = stack.getItemMeta();
		
		String name;
		if (meta.hasDisplayName()) name = meta.getDisplayName();
		else name = MConf.get().getDefaultName(stack.getType());
		
		if (stack.getAmount() > 1 && MConf.get().displayAmount)
		{
			name += " x" + String.valueOf(stack.getAmount());
		}
		
		item.setCustomName(name);
		item.setCustomNameVisible(true);
	}
	
	
}
