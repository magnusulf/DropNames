package dk.muj.dropnames.entity.migrator;


import com.massivecraft.massivecore.store.migrator.MigratorFieldRename;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import dk.muj.dropnames.entity.MConf;

public class MigratorMConf001Renames extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static MigratorMConf001Renames i = new MigratorMConf001Renames();
	public static MigratorMConf001Renames get() { return i; }
	private MigratorMConf001Renames()
	{
		super(MConf.class);
		this.addInnerMigrator(MigratorFieldRename.get("displayAmount", "displayItemAmount"));
		this.addInnerMigrator(MigratorFieldRename.get("displayRenamed", "displayItemRenamed"));
		this.addInnerMigrator(MigratorFieldRename.get("displayNotRenamed", "displayItemNotRenamed"));
	}
	
}
