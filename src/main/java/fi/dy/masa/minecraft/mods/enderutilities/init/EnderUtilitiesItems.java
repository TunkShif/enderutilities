package fi.dy.masa.minecraft.mods.enderutilities.init;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import fi.dy.masa.minecraft.mods.enderutilities.items.EnderArrow;
import fi.dy.masa.minecraft.mods.enderutilities.items.EnderBag;
import fi.dy.masa.minecraft.mods.enderutilities.items.EnderBow;
import fi.dy.masa.minecraft.mods.enderutilities.items.EnderBucket;
import fi.dy.masa.minecraft.mods.enderutilities.items.EnderLasso;
import fi.dy.masa.minecraft.mods.enderutilities.items.EnderPearlReusable;
import fi.dy.masa.minecraft.mods.enderutilities.reference.Reference;

public class EnderUtilitiesItems
{
	public static final Item enderArrow = new EnderArrow();
	public static final Item enderBag = new EnderBag();
	public static final Item enderBow = new EnderBow();
	public static final Item enderBucket = new EnderBucket();
	public static final Item enderLasso = new EnderLasso();
	public static final Item enderPearlReusable = new EnderPearlReusable();

	public static void init()
	{
		GameRegistry.registerItem(enderArrow, Reference.NAME_ITEM_ENDER_ARROW);
		GameRegistry.registerItem(enderBag, Reference.NAME_ITEM_ENDER_BAG);
		GameRegistry.registerItem(enderBow, Reference.NAME_ITEM_ENDER_BOW);
		GameRegistry.registerItem(enderBucket, Reference.NAME_ITEM_ENDER_BUCKET);
		GameRegistry.registerItem(enderLasso, Reference.NAME_ITEM_ENDER_LASSO);
		GameRegistry.registerItem(enderPearlReusable, Reference.NAME_ITEM_ENDER_PEARL_REUSABLE);
		ItemStack eye = new ItemStack(Items.ender_eye);
		ItemStack pearl = new ItemStack(Items.ender_pearl);
		ItemStack gold = new ItemStack(Items.gold_ingot);
		ItemStack powder = new ItemStack(Items.blaze_powder);
		ItemStack leather = new ItemStack(Items.leather);
		ItemStack string = new ItemStack(Items.string);
		ItemStack stick = new ItemStack(Items.stick);
		GameRegistry.addShapelessRecipe(new ItemStack(enderArrow), "EAG", 'E', eye, 'A', new ItemStack(Items.arrow), 'G', gold);
		GameRegistry.addRecipe(new ItemStack(enderBag), "BLB", "LEL", "BLB", 'B', powder, 'L', leather, 'E', eye);
		GameRegistry.addRecipe(new ItemStack(enderBow), "SE ", "S T", "SE ", 'S', string, 'T', stick, 'E', eye);
	}
}
