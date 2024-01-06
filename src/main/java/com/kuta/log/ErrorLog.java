package com.kuta.log;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.annotations.SerializedName;

public class ErrorLog {

    @SerializedName("cas")
    public String time;
    @SerializedName("id")
    public String id;
    @SerializedName("nazev_chyby")
    public String exceptionName;
    @SerializedName("text_chyby")
    public String exceptionText;
    @SerializedName("stacktrace")
    public String stacktrace;

    public ErrorLog(){

    }

    public ErrorLog(String id, String time, String exceptionName,String exceptionText,String stacktrace){
        this.id = id;
        this.exceptionName = exceptionName;
        this.exceptionText = exceptionText;
        this.stacktrace = stacktrace;
        this.time = time;




    }

    @Override
    public String toString() {
        return "ErrorLog [time=" + time + ", id=" + id + ", exceptionName=" + exceptionName + ", exceptionText="
                + exceptionText + ", stacktrace=" + stacktrace + "]";
    }

    

    
    
}
