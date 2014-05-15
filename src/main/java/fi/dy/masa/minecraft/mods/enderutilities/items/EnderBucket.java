package fi.dy.masa.minecraft.mods.enderutilities.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import fi.dy.masa.minecraft.mods.enderutilities.creativetab.CreativeTab;
import fi.dy.masa.minecraft.mods.enderutilities.reference.Reference;

public class EnderBucket extends Item
{
	public EnderBucket()
	{
		super();
		this.setMaxStackSize(1);
		this.setUnlocalizedName(Reference.NAME_ITEM_ENDER_BUCKET);
		this.setTextureName(Reference.MOD_ID + ":" + this.getUnlocalizedName()); // FIXME?
		this.setCreativeTab(CreativeTab.ENDER_UTILITIES_TAB);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		// FIXME the boolean flag does what exactly? In vanilla it seems to indicate that the bucket is empty.
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

		if (movingobjectposition == null)
		{
			System.out.println("null");
			return stack;
		}

        NBTTagCompound nbt = stack.getTagCompound();

		if (nbt != null)
		{
		}

		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		// Do nothing on the client side
		if (world.isRemote == true)
		{
			return false;
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		String fluid = "<empty>";
		short amount = 0;
		String pre = "" + EnumChatFormatting.BLUE;
		String rst = "" + EnumChatFormatting.RESET + EnumChatFormatting.GRAY;
		NBTTagCompound nbt = stack.getTagCompound();

		if (nbt != null)
		{
			amount	= nbt.getShort("amount");

			if (nbt.hasKey("fluid") == true && amount > 0)
			{
				fluid = pre + nbt.getString("fluid") + rst; // FIXME get the localized name
			}
		}

		list.add("Fluid: " + fluid);
		list.add(String.format("Amount: %d mB", amount));
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
	{
		return true;
	}
}
