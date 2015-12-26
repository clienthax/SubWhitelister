package net.bitjump.bukkit.subwhitelister;

import com.google.inject.Inject;
import net.bitjump.bukkit.subwhitelister.commands.ListCommandExecutor;
import net.bitjump.bukkit.subwhitelister.commands.ReloadCommandExecutor;
import net.bitjump.bukkit.subwhitelister.commands.ToggleCommandExecutor;
import net.bitjump.bukkit.subwhitelister.listeners.PlayerListener;
import net.bitjump.bukkit.subwhitelister.util.ConfigManager;
import net.bitjump.bukkit.subwhitelister.util.WhitelistManager;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(name = "SubWhitelister", id = "subwhitelister", version = "1.0")
public class SubWhitelister
{
	public static SubWhitelister instance;

	@Inject
	public Logger log;

	@Inject
	@DefaultConfig(sharedRoot = false)
	private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;

	ConfigManager configManager;

	@Listener
	public void onEnable(GameInitializationEvent event)
	{

		log.info("Plugin initializing...");

		configManager = new ConfigManager(configurationLoader);
		
		log.info("Setting up commands...");

		CommandSpec listCommand = CommandSpec.builder()
				.permission("subwhitelister.commands.list")
				.executor(new ListCommandExecutor())
				.build();

		CommandSpec reloadCommand = CommandSpec.builder()
				.permission("subwhitelister.commands.reload")
				.executor(new ReloadCommandExecutor())
				.build();

		CommandSpec toggleCommand = CommandSpec.builder()
				.permission("subwhitelister.commands.toggle")
				.executor(new ToggleCommandExecutor())
				.build();

		CommandSpec swCommandSpec = CommandSpec.builder()
				.child(listCommand, "list")
				.child(reloadCommand, "reload")
				.child(toggleCommand, "toggle")
				.build();
		Sponge.getGame().getCommandManager().register(this, swCommandSpec, "sw");
		
		log.info("Setting up listeners...");
		Sponge.getEventManager().registerListeners(this, new PlayerListener());
		
		instance = this;
		
		WhitelistManager.initialize();
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public static Logger getLog()
	{
		return instance.log;
	}
}
