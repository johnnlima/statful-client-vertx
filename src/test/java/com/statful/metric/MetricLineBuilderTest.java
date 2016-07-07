package com.statful.metric;

import com.google.common.collect.Lists;
import com.statful.client.Aggregation;
import com.statful.client.AggregationFreq;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;


public class MetricLineBuilderTest {

    private MetricLineBuilder victim;

    @Before
    public void setup() {
        victim = new MetricLineBuilder();
        victim.withPrefix("prefix");
        victim.withNamespace("namespace");
        victim.withMetricType("timer");
        victim.withMetricName("execution");
        victim.withTag("tagName", "tagValue");
        victim.withTimestamp(1);
        victim.withValue("value");
        victim.withApp(Optional.empty());
    }

    @Test
    public void testBuildWithoutAggregations() {
        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,tagName=tagValue value 1";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithoutAggregationsWithoutTags() {

        victim = new MetricLineBuilder();
        victim.withPrefix("prefix");
        victim.withNamespace("namespace");
        victim.withMetricType("timer");
        victim.withMetricName("execution");
        victim.withTimestamp(1);
        victim.withValue("value");
        victim.withApp(Optional.empty());

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution value 1";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithoutAggregationsWithFrequencies() {

        victim.withAggregationFrequency(AggregationFreq.FREQ_120);

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,tagName=tagValue value 1";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithAggregationsAndFrequencies() {

        victim.withAggregations(Lists.newArrayList(Aggregation.AVG));
        victim.withAggregationFrequency(AggregationFreq.FREQ_120);

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,tagName=tagValue value 1 avg,120";
        assertEquals(expected, result);
    }
}