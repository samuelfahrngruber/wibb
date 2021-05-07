package at.wibb.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultResource {

    private static final Logger CLIENT_ERROR_LOGGER = LoggerFactory.getLogger("ClientErrorLogger");

    @PostMapping("/api/errors")
    public void postError(@RequestBody String errorJson) {
        CLIENT_ERROR_LOGGER.error("Client error occurred and reported:\n" + errorJson);
    }
}
