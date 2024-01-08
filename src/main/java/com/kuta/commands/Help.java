package com.kuta.commands;

import com.kuta.CommandHandler;

public class Help implements ConsoleCommand{
    
    private CommandHandler myHandler;

    public Help(CommandHandler myHandler){
        this.myHandler = myHandler; 
    }
    
    @Override
    public String help() {
        String returnString = "Použijte pro ziskání listu příkazů, a nebo pro konkrétní přikazy."; 
        return returnString;
    }

    @Override
    public void execute() {
        String returnString = "";

    }

    
}
