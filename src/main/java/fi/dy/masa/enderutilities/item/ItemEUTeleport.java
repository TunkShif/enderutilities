package fi.dy.masa.enderutilities.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import fi.dy.masa.enderutilities.util.ItemNBTHelper;
import fi.dy.masa.enderutilities.util.TooltipHelper;

public class ItemEUTeleport extends ItemEU
{
	public ItemEUTeleport()
	{
		super();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		ItemNBTHelper target = new ItemNBTHelper();

		if (nbt != null && target.readTargetTagFromNBT(nbt) != null)
		{
			String desc;

			// Vanilla dimensions
			if (target.dimension >= -1 && target.dimension <= 1)
			{
				desc = super.getItemStackDisplayName(stack) + " (" + TooltipHelper.getLocalizedDimensionName(target.dimension) + ")";
			}
			// Non-vanilla dimensions, only show the dimension ID
			else
			{
				desc = super.getItemStackDisplayName(stack) + " (DIM: " + target.dimension + ")";
			}

			return desc;
		}

		return super.getItemStackDisplayName(stack);
	}
}
