package net.bitjump.bukkit.subwhitelister.util;

import net.bitjump.bukkit.subwhitelister.SubWhitelister;
import org.spongepowered.api.Sponge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;

public class WhitelistManager 
{
	private static final HashSet<String> users = new HashSet<String>();
	
	public static void initialize()
	{
		Sponge.getGame().getScheduler().createTaskBuilder().async().execute(WhitelistManager::updateRemoteWhitelists).intervalTicks(SubWhitelister.instance.getConfigManager().getDelay() * 20L).submit(SubWhitelister.instance);
	}
	
	public static HashSet<String> getUsers()
	{
		return users;
	}
	
	public static void updateRemoteWhitelists()
	{
		for(String s : SubWhitelister.instance.getConfigManager().getUrls())
		{
			BufferedReader in = null;
			try 
			{
				in = new BufferedReader(new InputStreamReader(new URL("http://whitelist.twitchapps.com/list.php?id=" + s).openStream()));
				String l;
				
				while ((l = in.readLine()) != null)
				{
					users.add(l.trim().toLowerCase());
				}
			}
			catch (IOException e)
			{
				SubWhitelister.getLog().error("[SubWhitelister] Uh oh. The website could be down. We'll keep trying.");
				SubWhitelister.getLog().error("[SubWhitelister] Give the following crash log to @Bitjump_ for diagnostics!");
				e.printStackTrace();
			}
			finally
			{
				if(in != null)
				{
					try
					{
						in.close();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

}
