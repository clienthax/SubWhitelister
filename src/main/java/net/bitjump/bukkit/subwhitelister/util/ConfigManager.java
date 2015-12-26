package net.bitjump.bukkit.subwhitelister.util;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.util.Collections;
import java.util.List;

public class ConfigManager
{

	CommentedConfigurationNode rootNode;
	boolean enabled = true;
	int delay = 60;
	List<String> urls = Collections.singletonList("replace_me");

	public ConfigManager(ConfigurationLoader<CommentedConfigurationNode> configurationLoader) {
		try {
			rootNode = configurationLoader.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
			enabled = rootNode.getNode("enabled").getBoolean(enabled);
			delay = rootNode.getNode("whitelist").getNode("delay").setComment("The delay, in seconds, between the updating of the remote whitelist.").getInt(delay);
			urls = rootNode.getNode("whitelist").getNode("urls").setComment("The unique IDs of the whitelists to pull from.").getList(new TypeToken<String>() {}, urls);
			configurationLoader.save(rootNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public int getDelay() {
		return delay;
	}

	public List<String> getUrls() {
		return urls;
	}

}