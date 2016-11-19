/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.afzaln.pgshoutbox.util;

public interface BasePresenter<V> {
    /**
     * Load initial things into view
     * @param view The view to use
     * @param autoLoad Whether to load data automatically or not
     */
    void onViewAttached(V view, boolean autoLoad);

    /**
     * View was destroyed, hence detached
     */
    void onViewDetached();

    /**
     * Cleanup resources
     */
    void onDestroyed();
}
