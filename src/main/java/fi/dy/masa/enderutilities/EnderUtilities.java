package fi.dy.masa.enderutilities;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import fi.dy.masa.enderutilities.event.EntityAttack;
import fi.dy.masa.enderutilities.event.EntityInteract;
import fi.dy.masa.enderutilities.init.EnderUtilitiesBlocks;
import fi.dy.masa.enderutilities.init.EnderUtilitiesItems;
import fi.dy.masa.enderutilities.proxy.IProxy;
import fi.dy.masa.enderutilities.reference.Reference;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class EnderUtilities
{
	@Instance(Reference.MOD_ID)
	public static EnderUtilities instance;

	@SidedProxy(clientSide = Reference.CLASS_CLIENT_PROXY, serverSide = Reference.CLASS_COMMON_PROXY)
	public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// Initialize mod items
		EnderUtilitiesItems.init();

		// Init mod blocks
		EnderUtilitiesBlocks.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerEntities();
		proxy.registerTileEntities();
		proxy.registerRenderers();

		MinecraftForge.EVENT_BUS.register(new EntityAttack());
		MinecraftForge.EVENT_BUS.register(new EntityInteract());
	}

/*
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
*/
}