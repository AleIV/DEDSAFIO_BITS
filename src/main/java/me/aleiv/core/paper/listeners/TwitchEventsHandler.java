package me.aleiv.core.paper.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.CheerEvent;
import com.github.twitch4j.chat.events.channel.DonationEvent;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;

import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

public class TwitchEventsHandler {

    Core instance;

    String PURPLE = ChatColor.of("#5b51c2") + "";
    String GREEN = ChatColor.of("#43d07e") + "";

    String RED = ChatColor.of("#ff1418") + "";
    String WHITE = ChatColor.WHITE + "";

    String ORANGE = ChatColor.of("#ff9514") + "";
    String LIME = ChatColor.of("#95ff14") + "";

    public TwitchEventsHandler(Core instance){
        this.instance = instance;
    }

    @EventSubscriber
    public void onCheer(CheerEvent e){
        var game = instance.getGame();
        if(game.getActive()){
            var user = e.getUser().getName();
            var canje = "";
            var bits = e.getBits();
            switch (bits) {
                case 100 ->{

                    
                }
            }

            instance.broadcastMessage(GREEN + user + PURPLE + " biteo " + GREEN + "x" + e.getBits() + PURPLE + canje);
        }
    }

    @EventSubscriber
    public void onSub(SubscriptionEvent e){
        var game = instance.getGame();
        if(game.getActive()){
            var canje = "";

            if(e.getGifted()){
                var user = e.getGiftedBy().getName();
                var subs = e.getGiftMonths();

                instance.broadcastMessage(GREEN + user + PURPLE + " gift sub " + GREEN + "x" + subs + PURPLE + canje);
                
            }else{
                var user = e.getUser().getName();
                
                instance.broadcastMessage(GREEN + user + PURPLE + " sub! " + GREEN + canje);
            }
            
        }
    }

    @EventSubscriber
    public void onDonation(DonationEvent e){

    }
}
