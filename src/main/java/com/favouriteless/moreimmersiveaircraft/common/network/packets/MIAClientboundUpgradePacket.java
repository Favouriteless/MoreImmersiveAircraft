package com.favouriteless.moreimmersiveaircraft.common.network.packets;

import com.favouriteless.moreimmersiveaircraft.common.network.MIAPacket;
import immersive_aircraft.item.UpgradeItem;
import immersive_aircraft.item.upgrade.AircraftStat;
import immersive_aircraft.item.upgrade.AircraftUpgrade;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MIAClientboundUpgradePacket implements MIAPacket {

	private final Map<ResourceLocation, Map<AircraftStat, Float>> upgrades;

	public MIAClientboundUpgradePacket(Map<ResourceLocation, Map<AircraftStat, Float>> upgrades) {
		this.upgrades = upgrades;
	}

	@Override
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(upgrades.size());
		for(ResourceLocation id : upgrades.keySet()) {
			buffer.writeResourceLocation(id);
			Map<AircraftStat, Float> upgradeMap = upgrades.get(id);
			buffer.writeInt(upgradeMap.size());

			for(AircraftStat stat : upgradeMap.keySet()) {
				buffer.writeInt(stat.ordinal());
				buffer.writeFloat(upgradeMap.get(stat));
			}
		}
	}

	public static MIAClientboundUpgradePacket decode(FriendlyByteBuf buffer) {
		Map<ResourceLocation, Map<AircraftStat, Float>> upgrades = new HashMap<>();
		int upgradeCount = buffer.readInt();

		for(int i = 0; i < upgradeCount; i++) {
			Map<AircraftStat, Float> values = new HashMap<>();

			ResourceLocation upgradeKey = buffer.readResourceLocation();
			int statCount = buffer.readInt();

			for(int j = 0; j < statCount; j++)
				values.put(AircraftStat.values()[buffer.readInt()], buffer.readFloat());

			upgrades.put(upgradeKey, values);
		}

		return new MIAClientboundUpgradePacket(upgrades);
	}

	@Override
	public void handle(Supplier<Context> context) {
		context.get().enqueueWork(() -> {

			for(ResourceLocation key : upgrades.keySet()) {
				if(ForgeRegistries.ITEMS.getValue(key) instanceof UpgradeItem upgradeItem) {
					Map<AircraftStat, Float> desiredUpgrades = upgrades.get(key);
					AircraftUpgrade upgrade = upgradeItem.getUpgrade();

					for(AircraftStat stat : desiredUpgrades.keySet())
						upgrade.set(stat, desiredUpgrades.get(stat));
				}
			}

		});
		context.get().setPacketHandled(true);
	}

}
