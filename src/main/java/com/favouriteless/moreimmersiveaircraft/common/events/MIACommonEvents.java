package com.favouriteless.moreimmersiveaircraft.common.events;

import com.favouriteless.moreimmersiveaircraft.MIATags;
import com.favouriteless.moreimmersiveaircraft.MoreImmersiveAircraft;
import com.favouriteless.moreimmersiveaircraft.common.network.MIAPackets;
import com.favouriteless.moreimmersiveaircraft.common.network.packets.MIAClientboundUpgradePacket;
import immersive_aircraft.item.UpgradeItem;
import immersive_aircraft.item.upgrade.AircraftStat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid= MoreImmersiveAircraft.MODID, bus= Bus.FORGE)
public class MIACommonEvents {

	@SubscribeEvent
	public static void onDatapackSync(OnDatapackSyncEvent event) {
		Map<ResourceLocation, Map<AircraftStat, Float>> upgrades = new HashMap<>();

		for(Item item : ForgeRegistries.ITEMS.tags().getTag(MIATags.AIRCRAFT_UGPRADES)) {
			if(item instanceof UpgradeItem upgradeItem)
				upgrades.put(ForgeRegistries.ITEMS.getKey(item), upgradeItem.getUpgrade().getAll());
		}

		if(event.getPlayer() != null)
			MIAPackets.sendToPlayer(new MIAClientboundUpgradePacket(upgrades), event.getPlayer());
		else
			MIAPackets.sendToAllPlayers(new MIAClientboundUpgradePacket(upgrades));
	}

}
