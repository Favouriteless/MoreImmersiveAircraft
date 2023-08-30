package com.favouriteless.moreimmersiveaircraft;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid=MoreImmersiveAircraft.MODID, bus= Bus.FORGE)
public class MIAData {

	public static final UpgradeReloadListener UPGRADES = new UpgradeReloadListener("aircraft_upgrades");

	@SubscribeEvent
	public static void addReloadListenerEvent(AddReloadListenerEvent event) {
		event.addListener(UPGRADES);
	}

}
