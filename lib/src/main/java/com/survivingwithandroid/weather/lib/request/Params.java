/*
 * Copyright (C) 2014 Francesco Azzola
 *  Surviving with Android (http://www.survivingwithandroid.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.survivingwithandroid.weather.lib.request;


public class Params {

    /**
     * Radius units:
     *
     * NM Nautical Miles
     * KM Kilometers
     *
     * */
    public enum RAD_UNITS  {
        NM,
        KM}
    ;

    private float minLat;
    private float minLon;
    private float maxLat;
    private float maxLon;
    private float centerLat;
    private float centerLon;
    private float radius;
    private RAD_UNITS radUnits;
    private int imageWidth;
    private int imageHeight;
    private boolean newMap;
    private boolean rainSnow;
    private boolean smooth;
    private int frame;
    private int frameNumber ;
    private int delay ;
    private boolean timeLabel;
    private String imageType;
    private String satelliteImageType;


    private Params(float minLat, float minLon, float maxLat, float maxLon, float centerLat, float centerLon, float radius, RAD_UNITS radUnits, int imageWidth, int imageHeight, boolean newMap, boolean rainSnow, boolean smooth, int frame, int frameNumber, int delay, boolean timeLabel, String imageType, String satelliteImageType) {
        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
        this.centerLat = centerLat;
        this.centerLon = centerLon;
        this.radius = radius;
        this.radUnits = radUnits;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.newMap = newMap;
        this.rainSnow = rainSnow;
        this.smooth = smooth;
        this.frame = frame;
        this.frameNumber = frameNumber;
        this.delay = delay;
        this.timeLabel = timeLabel;
        this.imageType = imageType;
        this.satelliteImageType = satelliteImageType;
    }

    public String string() {
        StringBuffer buffer = new StringBuffer();
        if ( checkNumber(minLat))
            buffer.append("&minlat=" + minLat);
        if ( checkNumber(maxLat))
            buffer.append("&maxlat=" + maxLat);
        if ( checkNumber(minLon))
            buffer.append("&minLon=" + minLon);
        if ( checkNumber(maxLon))
            buffer.append("&maxlon=" + maxLon);
        if ( checkNumber(centerLat)) {
            if ("radar".equals(imageType))
              buffer.append("&centerlat=" + centerLat);
            else
              buffer.append("&lat=" + centerLat);
        }
        if ( checkNumber(centerLon)) {
            if ("radar".equals(imageType))
                buffer.append("&centerlon=" + centerLon);
            else
                buffer.append("&lon=" + centerLon);
        }
        if ( checkNumber(radius))
            buffer.append("&radius=" + radius);
        if ( checkNumber(imageWidth))
            buffer.append("&width=" + imageWidth);
        if ( checkNumber(imageHeight))
            buffer.append("&height=" + imageHeight);

        buffer.append("&newmaps=" + boolean2Int(newMap));
        buffer.append("&rainsnow=" + boolean2Int(rainSnow));
        buffer.append("&smooth=" + boolean2Int(smooth));
        buffer.append("&frame=" + frame);
        buffer.append("&num=" + frameNumber);
        buffer.append("&deley=" + delay);
        buffer.append("&timelabel=" + frameNumber);

        if ("satellite".equals(imageType))
            buffer.append("&key=" + satelliteImageType);

        String paramQuery = buffer.substring(1);

        return paramQuery;
    }

    private boolean checkNumber(float value) {
        if (value > Integer.MIN_VALUE)
            return true;

        return false;
    }

    private int boolean2Int(boolean val) {
        if (val)
            return 1;

        return 0;
    }

    public String getImageType() {
        return imageType;
    }

    public static class ParamsBuilder {
        private float minLat = Integer.MIN_VALUE;
        private float minLon = Integer.MIN_VALUE;
        private float maxLat = Integer.MIN_VALUE;
        private float maxLon = Integer.MIN_VALUE;
        private float centerLat = Integer.MIN_VALUE;
        private float centerLon = Integer.MIN_VALUE;
        private float radius = Integer.MIN_VALUE;
        private Params.RAD_UNITS radUnits;
        private int imageWidth;
        private int imageHeight;
        private boolean newMap;
        private boolean rainSnow;
        private boolean smooth;
        private int frame;
        private int frameNumber = 1;
        private int delay = 25;
        private boolean timeLabel;
        private IMAGE_TYPE imageType;
        private SATELLITE_IMAGE_TYPE satellite_image_type;

        public enum SATELLITE_IMAGE_TYPE {
            sat_ir4,
            sat_ir4_bottom,
            sat_vis,
            sat_vis_bottom
        }

        public enum IMAGE_TYPE {
            RADAR,
            SATELLITE,
            //RADAR_SATELLITE
        }

        public ParamsBuilder setMinLat(float minLat) {
            this.minLat = minLat;
            return this;
        }

        public ParamsBuilder setMinLon(float minLon) {
            this.minLon = minLon;
            return this;
        }

        public ParamsBuilder setMaxLat(float maxLat) {
            this.maxLat = maxLat;
            return this;
        }

        public ParamsBuilder setMaxLon(float maxLon) {
            this.maxLon = maxLon;
            return this;
        }

        public ParamsBuilder setCenterLat(float centerLat) {
            this.centerLat = centerLat;
            return this;
        }

        public ParamsBuilder setCenterLon(float centerLon) {
            this.centerLon = centerLon;
            return this;
        }

        public ParamsBuilder setRadius(float radius) {
            this.radius = radius;
            return this;
        }

        public ParamsBuilder setRadUnits(Params.RAD_UNITS radUnits) {
            this.radUnits = radUnits;
            return this;
        }

        public ParamsBuilder setImageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public ParamsBuilder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public ParamsBuilder setNewMap(boolean newMap) {
            this.newMap = newMap;
            return this;
        }

        public ParamsBuilder setRainSnow(boolean rainSnow) {
            this.rainSnow = rainSnow;
            return this;
        }

        public ParamsBuilder setSmooth(boolean smooth) {
            this.smooth = smooth;
            return this;
        }

        public ParamsBuilder setFrame(int frame) {
            this.frame = frame;
            return this;
        }

        public ParamsBuilder setFrameNumber(int frameNumber) {
            this.frameNumber = frameNumber;
            return this;
        }

        public ParamsBuilder setDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public ParamsBuilder setTimeLabel(boolean timeLabel) {
            this.timeLabel = timeLabel;
            return this;
        }

        public ParamsBuilder setImageType(IMAGE_TYPE type) {
            imageType = type;
            return this;
        }

        public ParamsBuilder setSatelliteImageType(SATELLITE_IMAGE_TYPE type) {
            satellite_image_type = type;
            return this;
        }

        public Params build() {
            String feature = null;
            if (imageType.equals(IMAGE_TYPE.RADAR))
                feature = "radar";
            else if (imageType.equals(IMAGE_TYPE.SATELLITE))
                feature = "satellite";
          //  else if (imageType.equals(IMAGE_TYPE.RADAR_SATELLITE))
          //      feature = "radar/satellite";

            String satImageType = getSatelliteImageType(satellite_image_type);

            return new Params(minLat, minLon, maxLat, maxLon, centerLat, centerLon, radius, radUnits, imageWidth, imageHeight, newMap, rainSnow, smooth, frame, frameNumber, delay, timeLabel, feature, satImageType);
        }

        private String getSatelliteImageType(SATELLITE_IMAGE_TYPE satellite_image_type) {
            if (satellite_image_type == null)
                return null;

            if (satellite_image_type.equals(SATELLITE_IMAGE_TYPE.sat_ir4))
                return "sat_ir4";
            else if (satellite_image_type.equals(SATELLITE_IMAGE_TYPE.sat_ir4_bottom))
                return "sat_ir4_bottom";
            else if (satellite_image_type.equals(SATELLITE_IMAGE_TYPE.sat_vis))
                return "sat_vis";
            else if (satellite_image_type.equals(SATELLITE_IMAGE_TYPE.sat_vis_bottom))
                return "sat_vis_bottom";

            return null;
        }

    }

}
