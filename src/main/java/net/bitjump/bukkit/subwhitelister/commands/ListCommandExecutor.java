package net.bitjump.bukkit.subwhitelister.commands;

import net.bitjump.bukkit.subwhitelister.util.WhitelistManager;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;

public class ListCommandExecutor implements CommandExecutor {
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		String mess = "Users: ";
		mess += StringUtils.join(WhitelistManager.getUsers(), ", ");
		src.sendMessage(Texts.of(mess));
		return CommandResult.success();
	}
}
