package hellocucumber;

import Dropbox.DropboxAPI;
import com.google.gson.Gson;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class DropboxFileManagementStepDefinition {
    private final Path TEST_FILE_PATH = Path.of("test.txt");
    private final Path DROPBOX_FILE_PATH = Path.of("dropboxFile.txt");

    private DropboxAPI dropboxAPI;
    private Map<String, String> dropboxFileMetadataMap = new HashMap<>();

    @Before
    public void setup(){
        try (InputStream config = Files.newInputStream(Paths.get("src/test/resources/config.properties"))){
            Properties properties = new Properties();

            properties.load(config);

            dropboxAPI = new DropboxAPI(properties.getProperty("ACCESS_TOKEN"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Given("I have a file")
    public void iHaveAFile(){
        try {
            Files.write(TEST_FILE_PATH, "test".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @When("I send request to upload file")
    public void iSendRequestToUploadFile() {
        dropboxAPI.uploadFile(TEST_FILE_PATH);
    }

    @And("I send request to get file metadata")
    public void iSendRequestToGetFileMetadata() {
        Optional<String> dropboxFileMetadata = dropboxAPI.getFileMetadata(TEST_FILE_PATH);
        if (dropboxFileMetadata.isPresent()){
            Gson gson = new Gson();
            dropboxFileMetadataMap = gson.fromJson(dropboxFileMetadata.get(), Map.class);
        }
    }

    @And("I send request to delete file")
    public void iSendRequestToDeleteFile() {
        dropboxAPI.deleteFile(TEST_FILE_PATH);
    }

    @Then("I should see my file uploaded")
    public void iShouldSeeMyFileUploaded() {
        Optional<Path> dropboxFile = dropboxAPI.downloadFile(TEST_FILE_PATH, DROPBOX_FILE_PATH);
        if (dropboxFile.isPresent()){
            try {
                byte[] f1 = Files.readAllBytes(TEST_FILE_PATH);
                byte[] f2 = Files.readAllBytes(dropboxFile.get());
                assertArrayEquals(f1, f2);
            } catch (IOException e) {
                fail("File not found");
            }
        }else {
            fail("File not found");
        }
    }

    @Then("I should see my file metadata")
    public void iShouldSeeMyFileMetadata(){
        if (dropboxFileMetadataMap.isEmpty()) fail("Metadata not found");
        assertEquals(TEST_FILE_PATH.toString(), dropboxFileMetadataMap.get("name"));
        assertEquals("/" + TEST_FILE_PATH, dropboxFileMetadataMap.get("path_display"));
    }

    @Then("I should not see my file")
    public void iShouldNotSeeMyFile() {
        Optional<Path> dropboxFile = dropboxAPI.downloadFile(TEST_FILE_PATH, Paths.get("files/dropboxFile.txt"));
        if (dropboxFile.isPresent()){
            fail("File not deleted");
        }
    }

    @After
    public void deleteFiles(){
        dropboxAPI.deleteFile(TEST_FILE_PATH);
        try {
            Files.delete(TEST_FILE_PATH);
        } catch (IOException ignore) {}
        try {
            Files.delete(DROPBOX_FILE_PATH);
        } catch (IOException ignore) {}
    }
}
