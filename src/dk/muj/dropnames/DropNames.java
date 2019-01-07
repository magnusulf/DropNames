package dk.muj.dropnames;

import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.util.Txt;
import dk.muj.dropnames.entity.MConf;
import dk.muj.dropnames.entity.MConfColl;
import dk.muj.dropnames.entity.migrator.MigratorMConf001Renames;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DropNames extends MassivePlugin
	{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static DropNames i;

	public static DropNames get() { return i; }

	public DropNames()
	{
		i = this;
		this.setVersionSynchronized(false);
	}

	// -------------------------------------------- //
	// OVERRIDE: PLUGIN
	// -------------------------------------------- //

	@Override
	public void onEnableInner()
	{
		activate(
			MConfColl.class,
			MigratorMConf001Renames.class
		);
	}

	// -------------------------------------------- //
	// LISTENER: ITEM
	// -------------------------------s------------- //

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void itemSpawn(ItemSpawnEvent event)
	{
		setName(event.getEntity());
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void itemMerge(ItemMergeEvent event)
	{
		Item item = event.getTarget();
		Bukkit.getScheduler().runTaskLater(this, () -> setName(item), 1L);
	}
	
	public static String getCustomNameItem(Item item)
	{
		if (!MConf.get().isEnabledItem) return null;

		ItemStack stack = item.getItemStack();
		String ret;

		ItemMeta meta = stack.getItemMeta();

		if (meta.hasDisplayName() && MConf.get().displayItemRenamed)
		{
			ret = meta.getDisplayName();
		}
		else if (MConf.get().displayItemNotRenamed)
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
			ret = Txt.parse(ret);
		}
		else
		{
			return null;
		}
		
		if (stack.getAmount() > 1 && MConf.get().displayItemAmount)
		{
			ret += " x" + String.valueOf(stack.getAmount());
		}

		return ret;
	}

	// -------------------------------------------- //
	// LISTENER: EXPERIENCE ORB
	// -------------------------------------------- //

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void entityDeath(EntityDeathEvent event)
	{
		updateOrbsNearby(event.getEntity().getLocation());
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void thrownBottle(ExpBottleEvent event)
	{
		updateOrbsNearby(event.getEntity().getLocation());
	}

	public void updateOrbsNearby(Location location)
	{
		Runnable runnable = () -> {
			EntityType type = EntityType.EXPERIENCE_ORB;
			location.getWorld().getNearbyEntities(location, 10.0, 10.0, 10.0, entity -> entity.getType() == type).stream().forEach(DropNames::setName);
		};

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, runnable, 1L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, runnable, 40L);
	}

	public static String getCustomNameOrb(ExperienceOrb orb)
	{
		if (!MConf.get().isEnabledExperienceOrb) return null;

		int amount = orb.getExperience();
		String ret = MConf.get().experienceOrbName;
		if (MConf.get().displayExperienceOrbAmount) ret += " x" + String.valueOf(amount);
		ret = Txt.parse(ret);
		return ret;
	}

	// -------------------------------------------- //
	// NAMING
	// -------------------------------------------- //

	public static void setName(Entity entity)
	{
		if (!MConf.get().isEnabled) return;
		if (!MConf.get().worldsEnabled.contains(entity.getLocation().getWorld())) return;

		String name = getCustomName(entity);
		if (name == null) return;

		entity.setCustomName(name);
		entity.setCustomNameVisible(true);
	}

	public static String getCustomName(Entity entity)
	{
		if (entity instanceof Item) return getCustomNameItem((Item) entity);
		if (entity instanceof ExperienceOrb) return getCustomNameOrb((ExperienceOrb) entity);

		throw new RuntimeException();
	}

}
