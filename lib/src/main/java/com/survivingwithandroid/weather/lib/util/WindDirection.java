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
package com.survivingwithandroid.weather.lib.util;

/**
 * Convert wind direction in degrees to a String
 * @author Francesco
 */
public enum WindDirection {

    N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW;

    /**
     * Creates the direction from the azimuth degrees.
     */
    public static WindDirection getDir(int deg) {
        int degPositive = deg;
        if (deg < 0) {
            degPositive += (-deg / 360 + 1) * 360;
        }
        int degNormalized = degPositive % 360;
        int degRotated = degNormalized + (360 / 16 / 2);
        int sector = degRotated / (360 / 16);
        switch (sector) {
            case 0:
                return WindDirection.N;
            case 1:
                return WindDirection.NNE;
            case 2:
                return WindDirection.NE;
            case 3:
                return WindDirection.ENE;
            case 4:
                return WindDirection.E;
            case 5:
                return WindDirection.ESE;
            case 6:
                return WindDirection.SE;
            case 7:
                return WindDirection.SSE;
            case 8:
                return WindDirection.S;
            case 9:
                return WindDirection.SSW;
            case 10:
                return WindDirection.SW;
            case 11:
                return WindDirection.WSW;
            case 12:
                return WindDirection.W;
            case 13:
                return WindDirection.WNW;
            case 14:
                return WindDirection.NW;
            case 15:
                return WindDirection.NNW;
            case 16:
                return WindDirection.N;
        }
        return WindDirection.N;
    }

}


