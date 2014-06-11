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
package com.survivingwithandroid.weather.lib.provider;

/**
* This interface provides the description of the weather provider. For each weather provider implemented,
* there must be a class that implements this interface and provides the fully qualified class name of the provider and
* the fully qualified of the code provider
*
* @author Francesco Azzola
* */
public interface IProviderType {

    public String getProviderClass();

    public String getCodeProviderClass();

}
