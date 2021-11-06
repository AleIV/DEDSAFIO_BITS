package me.aleiv.core.paper.listeners;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.CheerEvent;
import com.github.twitch4j.chat.events.channel.DonationEvent;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;

import me.aleiv.core.paper.Core;
import net.md_5.bungee.api.ChatColor;

public class TwitchEventsHandler {

    Core instance;

    String PURPLE = "<#5b51c2>";
    String GREEN = "<#43d07e>";

    String RED = "<#ff1418>";
    String WHITE = ChatColor.WHITE + "";

    String ORANGE = "<#ff9514>";
    String LIME = "<#95ff14>";

    String GOLD = "<#eaa72a>";

    String LIGHT_WHITE = "<#BABA9B>";

    public TwitchEventsHandler(Core instance){
        this.instance = instance;
    }

    @EventSubscriber
    public void onCheer(CheerEvent e){
        var game = instance.getGame();
        if(game.getActive()){
            var twitchEvents = game.getTwitchEvents();
            var user = e.getUser().getName();
            var canje = "";
            var bits = e.getBits();
            
            if(twitchEvents.containsKey(bits)){
                var event = twitchEvents.get(bits);
                event.getConsumer().accept(true);
                canje = event.getName();
            }

            if(bits >= 5000){
                instance.broadcastMessage(RED + user + LIGHT_WHITE + " biteo " + RED + "x" + e.getBits() + GOLD + " " +  canje);

            }else{
                instance.broadcastMessage(GREEN + user + PURPLE + " biteo " + GREEN + "x" + e.getBits() + GOLD + " " +  canje);
            }
            
            
        }
    }

    //@EventSubscriber
    public void onSub(SubscriptionEvent e){
        var game = instance.getGame();
    
        if(game.getActive()){

            var canje = "";
            var typeSub = "";

            switch (e.getSubPlan()) {
                case TIER1->{
                    typeSub = "Tier 1";

                }
                case TIER2->{
                    typeSub = "Tier 2";

                }
                case TIER3->{
                    typeSub = "Tier 3";

                }
                case TWITCH_PRIME->{
                    typeSub = "Prime";
                    
                }

                case NONE -> throw new UnsupportedOperationException("Unimplemented case: " + e.getSubPlan());
                default -> throw new IllegalArgumentException("Unexpected value: " + e.getSubPlan());
            }

            if(e.getGifted()){
                var user = e.getGiftedBy().getName();
                var subs = e.getGiftMonths();

                instance.broadcastMessage(GREEN + user + PURPLE + " gift sub "  + typeSub + GREEN + "x" + subs + GOLD + canje);
                
            }else{
                var user = e.getUser().getName();
                
                instance.broadcastMessage(GREEN + user + PURPLE + " sub " + typeSub + "! " + GOLD + canje);
            }
            
        }
    }

    

    //@EventSubscriber
    public void onDonation(DonationEvent e){

    }
}
