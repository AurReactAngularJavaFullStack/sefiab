package fr.soprasteria.agircarcco.sefiab.model;

public class PartitioningMessage {
    private int minValue;
    private int maxValue;

    public PartitioningMessage(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String toString() {
        return "PartitioningMessage{" +
                "minValue=" + minValue +
                ", maxValue=" + maxValue +
                '}';
    }
}

