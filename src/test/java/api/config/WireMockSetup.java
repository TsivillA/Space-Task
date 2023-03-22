package api.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockSetup {
    private static final String RESPONSES_FILE = "src/test/resources/mockResponses/responses.json";
    public static WireMockServer wm = new WireMockServer(WireMockConfiguration.options().port(8080));

    public static void setup() {
        wm.start();
        createStub();
    }

    public static void closeServer() {
        wm.stop();
    }

    private static void createStub() {
        String responses = "";
        try {
            responses = new String(Files.readAllBytes(Paths.get(RESPONSES_FILE)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stubFor(
                get("/api/activity")
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(responses)
                        )
        );
    }
}

