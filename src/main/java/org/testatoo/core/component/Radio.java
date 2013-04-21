/**
 * Copyright (C) 2013 Ovea <dev@testatoo.org>
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
package org.testatoo.core.component;

import org.testatoo.core.nature.Checkable;
import org.testatoo.core.nature.LabelSupport;

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
public class Radio extends Component implements Checkable, LabelSupport {

    public Radio(String id) {
        super(id);
    }
}
