package com.telemetron.client;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.VertxMetricsFactory;
import io.vertx.core.spi.metrics.VertxMetrics;

import javax.annotation.Nonnull;

/**
 * Factory that provides a TelemetronMetrics instance.
 */
public class TelemetronMetricsFactoryImpl implements VertxMetricsFactory {

    @Override
    public final VertxMetrics metrics(@Nonnull final Vertx vertx, @Nonnull final VertxOptions options) {
        final MetricsOptions metricsOptions = options.getMetricsOptions();

        final TelemetronMetricsOptions telemetronMetricsOptions;
        if (metricsOptions instanceof TelemetronMetricsOptions) {
            telemetronMetricsOptions = (TelemetronMetricsOptions) metricsOptions;
        } else {
            telemetronMetricsOptions = new TelemetronMetricsOptions();
        }

        return new VertxMetricsImpl(vertx, telemetronMetricsOptions);
    }

    @Override
    public final MetricsOptions newOptions() {
        return new TelemetronMetricsOptions();
    }
}
