package Dropbox;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class DropboxAPI {
    private final HttpClient client;
    private final String ACCESS_TOKEN;
    public DropboxAPI(String ACCESS_TOKEN){
        this.ACCESS_TOKEN = ACCESS_TOKEN;
        client = HttpClient.newBuilder().build();
    }

    public void uploadFile(Path path){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://content.dropboxapi.com/2/files/upload"))
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .header("Dropbox-API-Arg",
                            "{\"autorename\":false," +
                                    "\"mode\":\"add\"," +
                                    "\"mute\":false," +
                                    "\"path\":\"" + "/" + path + "\"," +
                                    "\"strict_conflict\":false}")
                    .header("Content-Type", "application/octet-stream")
                    .POST(HttpRequest.BodyPublishers.ofFile(path))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Path> downloadFile(Path from, Path to) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://content.dropboxapi.com/2/files/download"))
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .header("Dropbox-API-Arg", "{\"path\":\"" + "/" + from + "\"}")
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200){
                Files.write(to, response.body().getBytes());
                return Optional.of(to);
            }
            return Optional.empty();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getFileMetadata(Path path){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.dropboxapi.com/2/files/get_metadata"))
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"include_deleted\":false," +
                            "\"include_has_explicit_shared_members\":false," +
                            "\"include_media_info\":false," +
                            "\"path\":\"" + "/" + path + "\"}"
                    )).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) return Optional.of(response.body());
            else return Optional.empty();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(Path path){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.dropboxapi.com/2/files/delete_v2"))
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"path\":\"" + "/" + path + "\"}"))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
