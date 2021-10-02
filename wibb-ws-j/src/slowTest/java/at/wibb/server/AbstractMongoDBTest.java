package at.wibb.server;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

public abstract class AbstractMongoDBTest {

    private static final MongodStarter MONGODB_STARTER = MongodStarter.getDefaultInstance();
    private static final int MONGODB_DEFAULT_PORT = 27019;

    private static MongodExecutable mongodbExecutable;
    private static MongodProcess mongodbProcess;

    private static int port;

    @BeforeAll
    static void init() throws Exception {
        port = MONGODB_DEFAULT_PORT;
        mongodbExecutable = MONGODB_STARTER.prepare(createMongodbConfig());
        mongodbProcess = mongodbExecutable.start();
    }

    private static IMongodConfig createMongodbConfig() throws IOException {
        return new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();
    }

    @AfterEach
    void tearDown() {
        mongodbProcess.stop();
        mongodbExecutable.stop();
    }

}
