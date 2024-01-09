package com.kuta.vendor;

import com.google.gson.GsonBuilder;

/**
 * This is just a wrapper for the google/gson Java JSON serialization tool made by Google.
 * Visit on this {@link https://github.com/google/gson} link.
 */
public class Gson {
    public static com.google.gson.Gson gson = new GsonBuilder().setPrettyPrinting().create();
}
