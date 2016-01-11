package com.telemetron.sender;

import com.google.common.collect.Lists;
import com.telemetron.client.TelemetronMetricsOptions;
import com.telemetron.metric.DataPoint;
import io.vertx.core.AsyncResult;
import io.vertx.core.AsyncResultHandler;
import io.vertx.core.Vertx;
import io.vertx.core.datagram.DatagramSocket;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(VertxUnitRunner.class)
public class UDPSenderTest {

    private static final String HOST = "0.0.0.0";
    private static final int PORT = 1239;
    private DatagramSocket receiver;

    private UDPSender victim;

    @Before
    public void setUp() throws Exception {
        Vertx vertx = Vertx.vertx();

        TelemetronMetricsOptions options = new TelemetronMetricsOptions();
        options.setPort(PORT).setHost(HOST);

        this.victim = new UDPSender(vertx, vertx.getOrCreateContext(), options);
        this.receiver = vertx.createDatagramSocket();
    }

    /**
     * Not using junit @after annotation since we want to wait for the servers to close
     *
     * @param async used to finish the test
     */
    private void teardown(Async async) {
        this.victim.close(event -> async.complete());
    }


    @Test
    public void testSend(TestContext context) throws Exception {
        Async async = context.async();

        final List<String> metricLines = Lists.newArrayList("line1", "line2");
        final List<DataPoint> dataPoints = metricLines.stream().map(DummyDataPoint::new).collect(Collectors.toList());

        // configure receiver and desired assertions
        this.receiver.listen(PORT, HOST, event -> {
            context.assertTrue(event.succeeded());
            receiver.handler(packet -> {
                context.assertNotNull(packet.data());
                // split the metric by the separator char
                final String packetData = packet.data().toString();
                final List<String> metrics = Arrays.asList(packetData.split("\n"));

                context.assertEquals(2, metrics.size());

                context.assertTrue(metrics.containsAll(metricLines));
                this.receiver.close(ignore -> this.teardown(async));
            });
            receiver.endHandler(end -> receiver.close());
            receiver.exceptionHandler(context::fail);
        });

        victim.send(dataPoints);
    }

    @Test
    public void testSendNoListenerSuccess(TestContext context) throws Exception {

        Async async = context.async();

        final List<String> metricLines = Lists.newArrayList("line1", "line2");

        List<DataPoint> dataPoints = metricLines.stream().map(DummyDataPoint::new).collect(Collectors.toList());
        victim.send(dataPoints, result -> {
            context.assertTrue(result.succeeded());
            teardown(async);
        });
    }

    private static final class DummyDataPoint implements DataPoint {

        private final String line;

        public DummyDataPoint(String line) {
            this.line = line;
        }

        @Override
        public String toMetricLine() {
            return this.line;
        }
    }
}