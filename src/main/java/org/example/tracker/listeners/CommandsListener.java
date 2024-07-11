package org.example.tracker.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.util.concurrent.*;
import org.example.tracker.lol.SummonerService;

import java.time.Duration;
import java.util.Properties;

public class CommandsListener extends ListenerAdapter {

    SummonerService summonerService;

    public CommandsListener(Properties properties) {
        summonerService = new SummonerService(properties);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "ping":
                event.reply("pong!").addActionRow(
                        Button.primary("hollow purple", Emoji.fromFormatted("<a:GojoHey:832588532416512071>"))
                ).queue();
                break;
            case "stats":
                event.deferReply().queue();
                event.getHook().sendMessage(summonerService.summonerStatsHandler(event)).queue();
                break;
            case "freechamps":
                event.reply(summonerService.championHandler()).queue();
                break;
            case "leaderboard":
                event.deferReply().queue();
                String reply = summonerService.leaderboard(event);
                event.getHook().editOriginal(reply).queue();
                break;
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("hollow purple")) {
            event.editMessage(":rightwards_pushing_hand_tone2::blue_circle:")
                    .delay(Duration.ofMillis(500))
                    .flatMap((it) -> it.editOriginal(":rightwards_pushing_hand_tone2::blue_circle::red_circle::leftwards_pushing_hand_tone2:"))
                    .delay(Duration.ofMillis(500))
                    .flatMap((it) -> it.editMessage(":pray_tone2:"))
                    .delay(Duration.ofMillis(500))
                    .flatMap((it) -> it.editMessage(":pinched_fingers_tone2::purple_circle:"))
                    .delay(Duration.ofMillis(500))
                    .flatMap((it) -> it.editMessage(":palm_up_hand_tone2:                                   :purple_circle:"))
                    .queue();
        }
    }
}
