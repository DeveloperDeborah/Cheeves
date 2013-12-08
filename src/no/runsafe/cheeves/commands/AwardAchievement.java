package no.runsafe.cheeves.commands;

import no.runsafe.cheeves.Achievement;
import no.runsafe.cheeves.AchievementFinder;
import no.runsafe.cheeves.AchievementHandler;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.player.IAmbiguousPlayer;
import no.runsafe.framework.api.player.IPlayer;

import java.util.Map;

public class AwardAchievement extends ExecutableCommand
{
	public AwardAchievement(AchievementHandler achievementHandler, AchievementFinder achievementFinder, IServer server)
	{
		super("awardach", "Awards an achievement to a player", "runsafe.cheeves.award", new PlayerArgument(), new RequiredArgument("achievementID"));
		this.achievementHandler = achievementHandler;
		this.achievementFinder = achievementFinder;
		this.server = server;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		IPlayer player = server.getPlayer(parameters.get("player"));

		if (player != null)
		{
			if (player instanceof IAmbiguousPlayer)
				return player.toString();

			Achievement achievement = this.achievementFinder.getAchievementByID(Integer.valueOf(parameters.get("achievementID")));
			if (achievement == null)
				return "&cNo achievement with that ID.";

			this.achievementHandler.awardAchievement(achievement, player);
			return null;
		}
		return "&cUnable to find player";
	}

	private final AchievementHandler achievementHandler;
	private final AchievementFinder achievementFinder;
	private final IServer server;
}
