package me.rickylafleur.joinmessages.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.rickylafleur.joinmessages.provider.MessageProvider;
import org.bukkit.entity.Player;

@CommandAlias("jm|joinmessage|joinmessages")
public class JoinMessageCommand extends BaseCommand {

    @Default
    private void onDefault(Player sender) {
        MessageProvider.open(sender);
    }
}
