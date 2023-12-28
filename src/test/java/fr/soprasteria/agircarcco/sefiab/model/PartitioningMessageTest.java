package fr.soprasteria.agircarcco.sefiab.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartitioningMessageTest {

    @Test
    public void testGetMinValue() {
        int minValue = 10;
        int maxValue = 20;
        PartitioningMessage message = new PartitioningMessage(minValue, maxValue);
        assertEquals(minValue, message.getMinValue());
    }

    @Test
    public void testGetMaxValue() {
        int minValue = 10;
        int maxValue = 20;
        PartitioningMessage message = new PartitioningMessage(minValue, maxValue);
        assertEquals(maxValue, message.getMaxValue());
    }

    @Test
    public void testSetMinValue() {
        int minValue = 10;
        int newValue = 30;
        PartitioningMessage message = new PartitioningMessage(minValue, 20);
        message.setMinValue(newValue);
        assertEquals(newValue, message.getMinValue());
    }

    @Test
    public void testSetMaxValue() {
        int maxValue = 20;
        int newValue = 40;
        PartitioningMessage message = new PartitioningMessage(10, maxValue);
        message.setMaxValue(newValue);
        assertEquals(newValue, message.getMaxValue());
    }

    @Test
    public void testToString() {
        int minValue = 10;
        int maxValue = 20;
        PartitioningMessage message = new PartitioningMessage(minValue, maxValue);
        String expectedString = "PartitioningMessage{minValue=10, maxValue=20}";
        assertEquals(expectedString, message.toString());
    }
}

