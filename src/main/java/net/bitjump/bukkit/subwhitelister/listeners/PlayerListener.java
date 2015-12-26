package net.bitjump.bukkit.subwhitelister.listeners;

import net.bitjump.bukkit.subwhitelister.SubWhitelister;
import net.bitjump.bukkit.subwhitelister.util.WhitelistManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Texts;

public class PlayerListener
{
	@Listener
	public void onPlayerLogin(ClientConnectionEvent.Join e)
	{
		if(!SubWhitelister.instance.getConfigManager().isEnabled()) return;

		Player p = e.getTargetEntity();

		if(!p.hasPermission("subwhitelister.exempt"))
		{
			if(!WhitelistManager.getUsers().contains(p.getName().toLowerCase()))
			{
				p.kick(Texts.of("Not on the whitelist."));
			}
		}
	}
}
