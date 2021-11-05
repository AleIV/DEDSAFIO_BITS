package me.aleiv.core.paper;

import lombok.Data;

@Data
public class TwitchMinecraftEvent {
    
    java.util.function.Consumer<Boolean> consumer;
    String name;

    public TwitchMinecraftEvent(String name, java.util.function.Consumer<Boolean> consumer){
        this.name = name;
        this.consumer = consumer;

    }
}
