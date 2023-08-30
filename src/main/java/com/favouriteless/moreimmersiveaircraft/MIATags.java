package com.favouriteless.moreimmersiveaircraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MIATags {

	public static final TagKey<Item> AIRCRAFT_UGPRADES = TagKey.create(Registries.ITEM, new ResourceLocation(MoreImmersiveAircraft.MODID, "aircraft_upgrades"));

}
