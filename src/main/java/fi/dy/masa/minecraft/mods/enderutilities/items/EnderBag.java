package fi.dy.masa.minecraft.mods.enderutilities.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		if (world.isRemote == false)
		{
			System.out.println("test");
		}
		return itemStack;
	}
}
