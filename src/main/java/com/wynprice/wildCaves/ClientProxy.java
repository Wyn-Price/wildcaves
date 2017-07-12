package com.wynprice.wildCaves;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;

public class ClientProxy extends ServerProxy{
    private static final String PREFIX = "cavetweaks";
    @Override
    public void registerModelBakery() {
    	mb(WildCaves.blockStoneStalactite, WildCaves.stalacs, "stone_");
    	mb(WildCaves.blockSandStalactite, WildCaves.sandStalacs, "sandstone_");
    	mb(WildCaves.blockDecorations, WildCaves.icicles, "icicle_");
   		mb(WildCaves.blockFlora, WildCaves.caps, "flora_");
   		mb(WildCaves.blockFossils, WildCaves.fossils, "fossil_");
    }

	private void mb(Block block, ArrayList<String> size, String Suffix)
	{
		
		ArrayList<ResourceLocation> ArrayNames = new ArrayList<ResourceLocation>();
    	Item item = Item.getItemFromBlock(block);
        for(int i = 0; i < size.size(); i++)
        	ArrayNames.add(new ResourceLocation(PREFIX, Suffix + i)); 
        	
        ResourceLocation[] names = new ResourceLocation[ArrayNames.size()];
        names = ArrayNames.toArray(names);
    	ModelBakery.registerItemVariants(Item.getItemFromBlock(WildCaves.blockStoneStalactite), names);
    	
	}
	
	
    private void regItemMesher(Block block, ArrayList<String> size, String Suffix)
    {
    	Item item = Item.getItemFromBlock(block);
        for(int i = 0; i < size.size(); i++) {
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(PREFIX, Suffix + Integer.toString(i)), "inventory"));
        }
    }     

    
	   @Override
	public void registerRenders() {
		   regItemMesher(WildCaves.blockStoneStalactite, WildCaves.stalacs, "stone_");
	   		regItemMesher(WildCaves.blockSandStalactite, WildCaves.sandStalacs, "sandstone_");
	   		regItemMesher(WildCaves.blockDecorations, WildCaves.icicles, "icicle_");
	   		regItemMesher(WildCaves.blockFlora, WildCaves.caps, "flora_");
	   		regItemMesher(WildCaves.blockFossils, WildCaves.fossils, "fossil_");
	}
      
	  @Override
     public void init() {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
                if(iBlockState != null){
                    if(WildCaves.blockFlora.getMetaFromState(iBlockState) < 6)
                        return iBlockAccess != null ? BiomeColorHelper.getFoliageColorAtPos(iBlockAccess, blockPos) : ColorizerFoliage.getFoliageColorBasic();
                }
                return -1;
            }
        }, WildCaves.blockFlora);
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack itemStack, int i) {
                if(itemStack.getMetadata() < 6)
                    return ColorizerFoliage.getFoliageColorBasic();
                return -1;
            }
        }, WildCaves.blockFlora);
    	ModBlocks.invtab();
    }

    @Override
    public void MUD(){
        try {
            Class.forName("mods.mud.ModUpdateDetector").getDeclaredMethod("registerMod", ModContainer.class, String.class, String.class).invoke(null,
                    FMLCommonHandler.instance().findContainerFor(this),
                    "https://raw.github.com/GotoLink/WildCaves3/master/update.xml",
                    "https://raw.github.com/GotoLink/WildCaves3/master/changelog.md"
            );
        } catch (Throwable e) {
        }
    }
}
