/**
 * Copyright (C) 2008 Ovea <dev@ovea.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.testatoo.core.config

import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */

final class Shutdown {

    private static final Shutdown SHUTDOWN = new Shutdown()

    private final List<Hook> hooks = new CopyOnWriteArrayList<>()

    private Shutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread("Testatoo Module cleaner") {
            @Override
            public void run() {
                for (Hook hook : hooks) {
                    try {
                        hook.onShutdown()
                    } catch (Throwable ignored) {
                    }
                }
            }
        });
    }

    public static void addHook(Hook callable) {
        SHUTDOWN.hooks.add(callable)
    }

    public static interface Hook {
        void onShutdown() throws Throwable
    }
}
