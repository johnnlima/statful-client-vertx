package com.telemetron.client;

import com.google.common.collect.Lists;
import io.vertx.core.json.JsonObject;
import io.vertx.core.metrics.MetricsOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * Vert.x Telemetron metrics configuration
 */
public class TelemetronMetricsOptions extends MetricsOptions {

    /**
     * Default Telemetron host constant
     */
    private static final String DEFAULT_HOST = "127.0.0.1";

    /**
     * Default port Telemetron port
     */
    private static final int DEFAULT_PORT = 2013;

    /**
     * Default value for a secure connection (https)
     */
    private static final Boolean DEFAULT_SECURE = true;

    /**
     * Default timeout value to be used by the client to connect to Telemetron
     */
    private static final int DEFAULT_TIMEOUT = 2000;

    /**
     * Default value for dry run configuration
     */
    private static final Boolean DEFAULT_DRY_RUN = false;

    /**
     * Maximum value allowed for the sample rate
     */
    private static final Integer MAX_SAMPLE_RATE = 100;

    /**
     * Minimum value allowed for the sample rate
     */
    private static final Integer MIN_SAMPLE_RATE = 1;

    /**
     * Default sample rate to applied to all metrics
     */
    private static final Integer DEFAULT_SAMPLE_RATE = MAX_SAMPLE_RATE;

    /**
     * Default namespace to be applied in all metrics
     */
    private static final String DEFAULT_NAMESPACE = "application";

    /**
     * Default size of elements that the buffer can old before flushing
     */
    private static final Integer DEFAULT_FLUSH_SIZE = 10;

    /**
     * Default flush interval at which metrics are sent
     */
    private static final long DEFAULT_FLUSH_INTERVAL = 30000;

    /**
     * Default transport definition
     */
    private static final Transport DEFAULT_TRANSPORT = Transport.UDP;

    /**
     * Default tags to be applied for Timer metrics
     */
    private static final ArrayList<Aggregation> DEFAULT_TIMER_AGGREGATIONS = Lists.newArrayList(Aggregation.AVG, Aggregation.P90, Aggregation.COUNT);

    /**
     * Default value for Aggregations Frequency for Timer metrics
     */
    private static final AggregationFreq DEFAULT_TIMER_FREQUENCY = AggregationFreq.FREQ_10;

    /**
     * Telemetron host, default value {@value #DEFAULT_HOST}
     */
    private Optional<String> host = Optional.empty();

    /**
     * Optional Telemetron default port, default value {@value #DEFAULT_PORT}
     */
    private Optional<Integer> port = Optional.empty();

    /**
     * Prefix to be added to all metrics.
     */
    private String prefix;

    /**
     * Defines the transport to be used to set which type of transport will be used to push the metrics.
     */
    private Transport transport = DEFAULT_TRANSPORT;

    /**
     * Defines whether to use https or not, default value {@value #DEFAULT_SECURE}
     */
    private Optional<Boolean> secure = Optional.empty();

    /**
     * Defines timeout for the client reporter (http / tcp transports), default value {@value #DEFAULT_TIMEOUT}
     */
    private Optional<Integer> timeout = Optional.empty();

    /**
     * Application token, to be used by the http transport
     */
    private Optional<String> token = Optional.empty();

    /**
     * Optional value to mapp to an extra TAG defining the application
     */
    private Optional<String> app = Optional.empty();

    /**
     * Optional configuration to not send any metrics when flushing the buffer, default value {@value #DEFAULT_DRY_RUN}
     */
    private boolean dryrun = DEFAULT_DRY_RUN;

    /**
     * Tags to be applied, default value {@link Collections#emptyList()}
     */
    private List<String> tags = Collections.emptyList();

    /**
     * List of aggregations to be applied on Timer metrics
     */
    private List<Aggregation> timerAggregations = DEFAULT_TIMER_AGGREGATIONS;

    /**
     * Frequency of aggregation to be applied on Timer metrics
     */
    private AggregationFreq timerFrequency = DEFAULT_TIMER_FREQUENCY;

    /**
     * Global rate sampling. Valid range [1-100], default value {@link #DEFAULT_SAMPLE_RATE}
     */
    private int sampleRate = DEFAULT_SAMPLE_RATE;

    /**
     * Optional name space to to to be applied to the to all metrics, can be overridden in method calls,
     * default value {@value #DEFAULT_NAMESPACE}
     */
    private Optional<String> namespace = Optional.empty();

    /**
     * Defined the periodicity (number of elements collected) of buffer flushes, default value {@value #DEFAULT_FLUSH_SIZE}
     */
    private int flushSize = DEFAULT_FLUSH_SIZE;

    /**
     * Defines the interval at which metrics should be flushed / sent to telemetron
     */
    private long flushInterval = DEFAULT_FLUSH_INTERVAL;

    /**
     * Empty constructor that provides default values, all of which should be overridable
     */
    public TelemetronMetricsOptions() {
    }

    /**
     * Copy based constructor
     *
     * @param other The other {@link MetricsOptions} to copy from
     */
    public TelemetronMetricsOptions(final TelemetronMetricsOptions other) {
        super(other);

        this.host = other.host;
        this.port = other.port;
        this.prefix = other.prefix;
        this.transport = other.transport;
        this.secure = other.secure;
        this.timeout = other.timeout;
        this.token = other.token;
        this.app = other.app;
        this.dryrun = other.dryrun;
        this.tags = other.tags;
        this.sampleRate = other.sampleRate;
        this.namespace = other.namespace;
        this.flushSize = other.flushSize;
        this.flushInterval = other.flushInterval;
    }


    /**
     * Constructor to create a configuration based on a json object
     *
     * @param json {@link JsonObject} to read the configuration from
     */
    public TelemetronMetricsOptions(final JsonObject json) {
        super(json);
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * @param host target telemetron host
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setHost(@Nonnull final String host) {
        this.host = Optional.of(requireNonNull(host));
        return this;
    }

    /**
     * Gets defined host or the default value if none was set
     *
     * @return string with host
     */
    @Nonnull
    public String getHost() {
        return host.orElse(DEFAULT_HOST);
    }

    /**
     * @param port target telemetron port
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setPort(@Nonnull final Integer port) {
        this.port = Optional.of(requireNonNull(port));
        return this;
    }

    /**
     * Get defined port or the default value if none was set
     *
     * @return Integer with port
     */
    @Nonnull
    public Integer getPort() {
        return port.orElse(DEFAULT_PORT);
    }

    /**
     * @param prefix to be applied
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setPrefix(@Nonnull final String prefix) {
        this.prefix = requireNonNull(prefix);
        return this;
    }

    /**
     * Get defined prefix
     *
     * @return String with the prefix
     * @throws NullPointerException if prefix is undefined
     */
    @Nonnull
    public String getPrefix() {
        requireNonNull(prefix, "Prefix must be set");
        return prefix;
    }

    /**
     * @param transport to be used to send metrics to telemetron
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setTransport(@Nonnull final Transport transport) {
        this.transport = requireNonNull(transport);
        return this;
    }

    /**
     * Get defined transport
     *
     * @return {@link Transport} defined
     * @throws NullPointerException if transport is undefined
     */
    @Nonnull
    public Transport getTransport() {
        requireNonNull(transport, "Transport must be defined");
        return transport;
    }

    /**
     * @param secure to be used with HTTP transport and define it it should use a secure connection
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setSecure(final boolean secure) {
        this.secure = Optional.of(secure);
        return this;
    }

    /**
     * Get if secure transport is to be used
     *
     * @return true if secure is to be used, false otherwise
     */
    @Nonnull
    public Boolean isSecure() {
        return secure.orElse(DEFAULT_SECURE);
    }

    /**
     * @param timeout defines timeout for the client reporter (http / tcp transports)
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setTimeout(final int timeout) {
        this.timeout = Optional.of(timeout);
        return this;
    }

    /**
     * Get the timeout to be used for the http and tcp transports
     *
     * @return int value in milliseconds
     */
    public int getTimeout() {
        return timeout.orElse(DEFAULT_TIMEOUT);
    }

    /**
     * @param token set application token
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setToken(@Nonnull final String token) {
        this.token = Optional.of(requireNonNull(token));
        return this;
    }

    /**
     * gets the token defined
     *
     * @return String token
     * @throws IllegalArgumentException if no token is configured
     */
    public String getToken() {
        return token.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * @param app defines application name to add to the metrics
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setApp(@Nullable final String app) {
        this.app = Optional.ofNullable(app);
        return this;
    }

    /**
     * Optional value to add to the tag list
     *
     * @return {@link Optional#empty()} if nothing is defined, string value if defined
     */
    public Optional<String> getApp() {
        return app;
    }

    /**
     * @param dryrun set the system to not send any metrics when flushing the buffer
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setDryrun(final boolean dryrun) {
        this.dryrun = dryrun;
        return this;
    }

    /**
     * Value that defines if the metrics should be sent or not
     *
     * @return true if metrics should be not be sent false otherwise
     */
    public boolean isDryrun() {
        return dryrun;
    }

    /**
     * Performs a shallow copy of the input list
     *
     * @param tags sets a list of tags to be applied
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setTags(@Nonnull final List<String> tags) {
        this.tags = new ArrayList<>(tags);
        return this;
    }

    /**
     * @return List of tags to be applied
     */
    @Nonnull
    public List<String> getTags() {
        return tags;
    }

    /**
     * @param sampleRate set rate sampling. Valid range [1-100]
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setSampleRate(final int sampleRate) {
        if (sampleRate < MIN_SAMPLE_RATE || sampleRate > MAX_SAMPLE_RATE) {
            throw new IllegalArgumentException("Invalid sample rate. Valid values between [1-100]. Provided:" + sampleRate);
        }
        this.sampleRate = sampleRate;
        return this;
    }

    /**
     * @return int value with the defined sample rate
     */
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * @param namespace set namespace to be applied to the to all metrics, can be overridden in method calls
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setNamespace(@Nonnull final String namespace) {
        this.namespace = Optional.of(requireNonNull(namespace));
        return this;
    }

    /**
     * @return String with the namespace value to apply to metrics
     */
    @Nonnull
    public String getNamespace() {
        return namespace.orElse(DEFAULT_NAMESPACE);
    }

    /**
     * @param flushSize Defined the periodicity (number of elements collected) of buffer flushes, default value {@value #DEFAULT_FLUSH_SIZE}
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setFlushSize(final int flushSize) {
        this.flushSize = flushSize;
        return this;
    }

    /**
     * @return int value with the size of the flush buffer
     */
    public int getFlushSize() {
        return flushSize;
    }

    /**
     * @return int value  of the milliseconds between buffer flushes
     */
    public long getFlushInterval() {
        return flushInterval;
    }

    /**
     * @param flushInterval long value  of the milliseconds between buffer flushes
     * @return a reference to this, so the API can be used fluently
     */
    public TelemetronMetricsOptions setFlushInterval(final long flushInterval) {
        this.flushInterval = flushInterval;
        return this;
    }

    /**
     * @return a copy of the aggregations applied on timer metrics
     */
    @Nonnull
    public List<Aggregation> getTimerAggregations() {
        return Lists.newArrayList(timerAggregations);
    }

    /**
     * @param timerAggregations list of aggregations to apply
     * @return a reference to this, so the API can be used fluently
     */
    @Nonnull
    public TelemetronMetricsOptions setTimerAggregations(@Nonnull final List<Aggregation> timerAggregations) {
        this.timerAggregations = Lists.newArrayList(Objects.requireNonNull(timerAggregations));
        return this;
    }

    /**
     * @return the frequency to be applied on timer metrics
     */
    @Nonnull
    public AggregationFreq getTimerFrequency() {
        return timerFrequency;
    }

    /**
     * @param timerFrequency the frequency to apply on the aggregations
     * @return a reference to this, so the API can be used fluently
     */
    @Nonnull
    public TelemetronMetricsOptions setTimerFrequency(@Nonnull final AggregationFreq timerFrequency) {
        this.timerFrequency = Objects.requireNonNull(timerFrequency);
        return this;
    }
}