package com.kuta.log;

import com.google.gson.annotations.SerializedName;

/**
 * Class used to serialize and deserialize operation logs
 */
public class OperationLog {
    @SerializedName("cas")
    public String time;
    @SerializedName("input_soubor")
    public String inputFilePath;
    @SerializedName("status")
    public String status;
    @SerializedName("error_log_id")
    public String errorLogId;
    @SerializedName("velikost_input_souboru")
    public String inputFileSize;
    @SerializedName("velikost_komp_souboru")
    public String compressedFileSize;
    @SerializedName("procentualni_komprese")
    public String compressionPercentage;


    public OperationLog(String time, String inputFilePath, String status, String errorLogId, String inputFileSize,
            String compressedFileSize, String compressionPercentage) {
        this.time = time;
        this.inputFilePath = inputFilePath;
        this.status = status;
        this.errorLogId = errorLogId;
        this.inputFileSize = inputFileSize;
        this.compressedFileSize = compressedFileSize;
        this.compressionPercentage = compressionPercentage;
    }

    public OperationLog(){

    }

    @Override
    public String toString() {
        return "{\ncas:" + time + ",\ninput_soubor:" + inputFilePath + ",\nstatus=" + status
                + ",\nerror_log_id:" + errorLogId + ",\nvelikost_input_souboru" + inputFileSize + ",\nvelikost_komp_souboru:"
                + compressedFileSize + ",\nprocentualni_komprese" + compressionPercentage + "\n}";
    }




    
}
