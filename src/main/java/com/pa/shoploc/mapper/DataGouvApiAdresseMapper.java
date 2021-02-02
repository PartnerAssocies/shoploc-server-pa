package com.pa.shoploc.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataGouvApiAdresseMapper {

    List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature{
        Geometry geometry;

        public Feature() {
            this.geometry = geometry;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Geometry{
            double[] coordinates;

            public Geometry() {
                this.coordinates = coordinates;
            }

            public double[] getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(double[] coordinates) {
                this.coordinates = coordinates;
            }
        }
    }
}
