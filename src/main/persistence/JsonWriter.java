package persistence;

import model.User;
import org.json.JSONObject;

import java.io.*;

// JsonWriter class modelled from example on edx
// Represents a writer that writes JSON representation of User to file
public class JsonWriter {
    private static int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to a default destination file
    public JsonWriter() {
        this.destination = "./data/default.json";
    }

    // EFFECTS: constructs writer to write to a destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file can't be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(this.destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of User to file
    public void write(User user) {
        JSONObject json = user.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        this.writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    public void saveToFile(String json) {
        this.writer.print(json);
    }
}
