package fi.dy.masa.minecraft.mods.enderutilities.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import fi.dy.masa.minecraft.mods.enderutilities.creativetab.CreativeTab;
import fi.dy.masa.minecraft.mods.enderutilities.reference.Reference;

public class EnderBag extends Item
{
	public EnderBag()
	{
		super();
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(Reference.NAME_ITEM_ENDER_BAG);
		this.setTextureName(Reference.MOD_ID + ":" + this.getUnlocalizedName()); // FIXME?
		this.setCreativeTab(CreativeTab.ENDER_UTILITIES_TAB);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
/*
		System.out.println("onItemRightClick");
		if (world.isRemote == false)
		{
			System.out.println("world.isRemote == false");
		}
		else
		{
			System.out.println("world.isRemote == true");
		}
*/
		return itemStack;
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		// Do nothing on the client side
		if (world.isRemote == true)
		{
			return false;
		}

		// FIXME debug code below!!
		//System.out.println("onItemUseFirst");

		Block block = world.getBlock(x, y, z);

		if (block instanceof BlockChest)
		{
			System.out.println("Is a Chest!");
		}

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null)
		{
			System.out.println("Is Tile Entity");
		}
		else
		{
			System.out.println("Not a Tile Entity");
		}
		return false;
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{
		return false;
	}
}
