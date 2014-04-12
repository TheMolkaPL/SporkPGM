package io.sporkpgm.objective.core;

import com.google.common.collect.Lists;
import io.sporkpgm.map.event.BlockChangeEvent;
import io.sporkpgm.module.ModuleInfo;
import io.sporkpgm.module.builder.Builder;
import io.sporkpgm.module.extras.InitModule;
import io.sporkpgm.objective.ObjectiveModule;
import io.sporkpgm.region.Region;
import io.sporkpgm.team.SporkTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.event.EventHandler;

import java.util.List;

import static org.bukkit.Material.*;

@ModuleInfo(name = "CoreObjective", description = "Objective where liquid flowing from a region is tracked until a specified distance")
public class CoreObjective extends ObjectiveModule implements InitModule {

	Liquid liquid;
	Material material;
	ChatColor color;
	Region region;
	int leak;

	boolean completed;

	public CoreObjective(String name, Material material, Region region, SporkTeam team, int leak) {
		super(name, team);
		this.color = team.getColor();
		this.material = material;
		this.region = region;
		this.leak = leak;
	}

	public Material getMaterial() {
		return material;
	}

	public Region getRegion() {
		return region;
	}

	@Override
	public boolean isComplete() {
		return completed;
	}

	@Override
	public void setComplete(boolean complete) {
		this.completed = complete;
		if(!complete) {
			return;
		}

		SporkTeam opposite = team.getOpposite();
		Bukkit.broadcastMessage(opposite.getColoredName() + "'s " + ChatColor.DARK_AQUA + name + ChatColor.GRAY + " has leaked!");
		update();
	}

	public void start() {
		this.liquid = Liquid.getLiquid(region, team.getMap().getWorld());
	}

	public void stop() { /* nothing */ }

	@EventHandler
	public void onBlockChange(BlockChangeEvent event) {
		if(isComplete()) {
			return;
		}

		if(!event.isPlace()) {
			return;
		}

		if(event.hasPlayer()) {
			return;
		}

		if(!Liquid.matches(liquid, event.getNewState().getType())) {
			return;
		}

		double distance = region.distance(event.getRegion());
		if(distance > leak) {
			setComplete(true);
		}
	}

	@Override
	public OfflinePlayer getPlayer() {
		return player;
	}

	@Override
	public ChatColor getStatusColour() {
		return (isComplete() ? ChatColor.GREEN : ChatColor.RED);
	}

	@Override
	public Class<? extends Builder> builder() {
		return CoreBuilder.class;
	}

	public enum Liquid {

		LAVA(new Material[]{Material.LAVA, STATIONARY_LAVA}),
		WATER(new Material[]{Material.WATER, STATIONARY_WATER}),
		NONE(new Material[0]);

		Material[] materials;

		Liquid(Material[] materials) {
			this.materials = materials;
		}

		public Material[] getMaterials() {
			return materials;
		}

		public boolean matches(Material material) {
			return Lists.newArrayList(materials).contains(material);
		}

		public boolean contains(Region region, World world) {
			for(Material material : materials) {
				if(region.isInside(material, world)) {
					return true;
				}
			}

			return false;
		}

		public static Liquid getLiquid(Region region, World world) {
			for(Liquid liquid : values()) {
				if(liquid.contains(region, world)) {
					return liquid;
				}
			}

			return NONE;
		}

		public static boolean matches(Liquid liquid, Material material) {
			return liquid.matches(material);
		}

	}

}
