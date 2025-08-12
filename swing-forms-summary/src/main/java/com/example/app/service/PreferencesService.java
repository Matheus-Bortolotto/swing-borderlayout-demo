
package com.example.app.service;

import java.util.prefs.Preferences;

public class PreferencesService {
    private final Preferences prefs;

    public PreferencesService(Class<?> nodeOwner) {
        this.prefs = Preferences.userNodeForPackage(nodeOwner);
    }

    public String getName()   { return prefs.get("name", ""); }
    public String getEmail()  { return prefs.get("email", ""); }
    public String getBody()   { return prefs.get("body", ""); }

    public void setName(String v)  { prefs.put("name", v == null ? "" : v); }
    public void setEmail(String v) { prefs.put("email", v == null ? "" : v); }
    public void setBody(String v)  { prefs.put("body", v == null ? "" : v); }
}
