package net.bitjump.bukkit.subwhitelister.commands;

import net.bitjump.bukkit.subwhitelister.SubWhitelister;
import net.bitjump.bukkit.subwhitelister.util.ConfigManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;

public class ToggleCommandExecutor implements CommandExecutor {
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		ConfigManager configManager = SubWhitelister.instance.getConfigManager();
		configManager.setEnabled(!configManager.isEnabled());
		src.sendMessage(Texts.of("Remote whitelist " + (configManager.isEnabled() ? "enabled!" : "disabled!")));
		return CommandResult.success();
	}
}
