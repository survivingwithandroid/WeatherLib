package com.survivingwithandroid.weather.lib.request;

/**
 * ${copyright}.
 */
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

    private Params(float minLat, float minLon, float maxLat, float maxLon, float centerLat, float centerLon, float radius, RAD_UNITS radUnits, int imageWidth, int imageHeight, boolean newMap, boolean rainSnow, boolean smooth, int frame, int frameNumber, int delay, boolean timeLabel) {
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
        if ( checkNumber(centerLat))
            buffer.append("&centerlat=" + centerLat);
        if ( checkNumber(centerLon))
            buffer.append("&centerlon=" + centerLon);
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

        public Params build() {
            return new Params(minLat, minLon, maxLat, maxLon, centerLat, centerLon, radius, radUnits, imageWidth, imageHeight, newMap, rainSnow, smooth, frame, frameNumber, delay, timeLabel);
        }
    }
}
