package com.favouriteless.moreimmersiveaircraft;

import com.favouriteless.moreimmersiveaircraft.common.network.MIAPackets;
import com.mojang.logging.LogUtils;
import immersive_aircraft.Main;
import immersive_aircraft.item.UpgradeItem;
import immersive_aircraft.item.upgrade.AircraftStat;
import immersive_aircraft.item.upgrade.AircraftUpgrade;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(MoreImmersiveAircraft.MODID)
public class MoreImmersiveAircraft {

    public static final String MODID = "moreimmersiveaircraft";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreImmersiveAircraft() {
        ModLoadingContext.get().registerConfig(Type.COMMON, MIACommonConfig.SPEC);
        MIAPackets.registerPackets();
    }

}
