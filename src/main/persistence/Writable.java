package persistence;

import org.json.JSONObject;

// Writable class modelled from example on edx
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}