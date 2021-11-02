package me.aleiv.core.paper;

import com.github.philippheuer.events4j.simple.domain.EventSubscriber;
import com.github.twitch4j.chat.events.channel.CheerEvent;
import com.github.twitch4j.chat.events.channel.DonationEvent;
import com.github.twitch4j.chat.events.channel.SubscriptionEvent;

public class TwitchEventsHandler {

    Core instance;

    public TwitchEventsHandler(Core instance){
        this.instance = instance;
    }

    @EventSubscriber
    public void onCheer(CheerEvent e){
        var game = instance.getGame();
        if(game.getActive()){
            instance.broadcastMessage("NAME " + e.getUser().getName() + " BITS " + e.getBits());
            
        }
    }

    @EventSubscriber
    public void onSub(SubscriptionEvent e){
        var game = instance.getGame();
        if(game.getActive()){
            instance.broadcastMessage("NAME " + e.getUser().getName() + " MONTHS " + e.getMonths());
            if(e.getGifted()){
                e.getGiftedBy().getName();
            }

            e.getUser().getName();//user
            e.getChannel().getName();//ded
            
        }
    }

    @EventSubscriber
    public void onDonation(DonationEvent e){
        e.getAmount();
    }
}
