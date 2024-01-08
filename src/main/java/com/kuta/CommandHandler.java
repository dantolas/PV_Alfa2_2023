package com.kuta;

import java.util.ArrayList;

import com.kuta.commands.ConsoleCommand;
import com.kuta.commands.Help;

public class CommandHandler {
   private ArrayList<ConsoleCommand> commands;
   
   
    public CommandHandler(){
        commands = new ArrayList<>(){{
            add(new Help());
        }};
    }
}
