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
package org.testatoo.core.component

import org.testatoo.core.*
import org.testatoo.core.evaluator.Evaluator
import org.testatoo.core.property.Property
import org.testatoo.core.property.PropertyEvaluator
import org.testatoo.core.property.matcher.PropertyMatcher
import org.testatoo.core.state.State
import org.testatoo.core.state.StateEvaluator
import org.testatoo.core.state.Visible

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
class Component {

    private static final PropertyEvaluator DEFAULT_PE = {} as PropertyEvaluator
    private static final StateEvaluator DEFAULT_SE = {} as StateEvaluator

    private Map<Class<? extends Property>, PropertyEvaluator> _supportedProperties = new IdentityHashMap<>()
    private Map<Class<? extends State>, StateEvaluator> _supportedStates = new IdentityHashMap<>()

    CachedMetaData meta = new CachedMetaData()

    Component() {}

    Component(Evaluator evaluator, IdProvider idProvider) {
        meta = new CachedMetaData(
            evaluator: evaluator,
            idProvider: idProvider
        )
    }

    String getId() throws ComponentException { meta.getId(this) }

    Evaluator getEvaluator() { meta.evaluator }

    Block is(State matcher) { block 'is', matcher }

    Block contains(Component... components) {
        Blocks.block "matching ${this} contains ${components}", {
            List ret = evaluator.getJson("testatoo.ext.contains('${id}', [${components.collect {"'${it.id}'"}.join(', ')}])")
            if(ret) {
                throw new AssertionError("Component ${this} does not contains expected component(s): ${components.findAll { it.id in ret } }");
            }
        }
    }

    Block displays(Component... components) {
        Blocks.block "matching ${this} displays ${components}", {
            List ret = evaluator.getJson("testatoo.ext.contains('${id}', [${components.collect {"'${it.id}'"}.join(', ')}])")
            if(ret) {
                throw new AssertionError("Component ${this} does not contains expected component(s): ${components.findAll { it.id in ret } }");
            } else {
                components.findAll { !it.is(new Visible()) }
            }
        }
    }

    Block has(PropertyMatcher matcher) { block 'has', matcher }

    private block(String type, Matcher m) { Blocks.block "matching ${this} ${type} ${m}", { m.matches(this) } }

    @Override
    String toString() {
        getClass().simpleName + ":${try { id } catch (Throwable ignored) { meta.metaInfo }}"
    }

    Object asType(Class clazz) {
        if (Component.isAssignableFrom(clazz)) {
            Component c = (Component) clazz.newInstance()
            c.meta = this.meta
            return c
        }
        return super.asType(clazz)
    }

    void support(Class<?>... types) {
        for (Class<?> type : types) {
            if (Property.isAssignableFrom(type)) {
                support(type as Class<? extends Property>, DEFAULT_PE)
            } else if (State.isAssignableFrom(type)) {
                support(type as Class<? extends State>, DEFAULT_SE)
            }
        }
    }

    void support(Class<? extends Property> type, PropertyEvaluator e) {
        _supportedProperties.put(type, e)
    }

    void support(Class<? extends State> type, StateEvaluator e) {
        _supportedStates.put(type, e)
    }

    void support(Class<?> type, Closure<?> c) {
        if (Property.isAssignableFrom(type)) {
            _supportedProperties.put(type as Class<? extends Property>, c as PropertyEvaluator)
        } else if (State.isAssignableFrom(type)) {
            _supportedStates.put(type as Class<? extends State>, c as StateEvaluator)
        }
    }

    Object getValue(Property property) {
        PropertyEvaluator pe = _supportedProperties.get(property.class)
        if (pe == null) {
            throw new ComponentException("Component ${this} does not support property ${property.class.simpleName}")
        }
        return (pe == DEFAULT_PE ? property.evaluator : pe).getValue(this)
    }

    boolean getState(State state) {
        StateEvaluator se = _supportedStates.get(state.class)
        if (se == null) {
            throw new ComponentException("Component ${this} does not support state ${state.class.simpleName}")
        }
        return (se == DEFAULT_SE ? state.evaluator : se).getState(this)
    }

    static class CachedMetaData {

        @Delegate
        private MetaInfo metaInfo

        private Evaluator evaluator

        IdProvider idProvider

        String getId(Component c) throws ComponentException {
            if (!metaInfo) {
                MetaInfo info = idProvider.getMetaInfo(evaluator)

                def hierarchy= []
                def s = c.class
                while(s != null) {
                    hierarchy << s.simpleName; s = s.superclass
                }
                hierarchy.pop()
                if (!hierarchy.contains(info.type)) {
                    println "===============> ${hierarchy}"
                    throw new ComponentException("The Component hierarchy ${hierarchy} doesn't contain the type ${info.type} for component with id ${info.id}")
                }
                metaInfo = info
            }
            return metaInfo.id
        }
    }

    static Component $(String jQuery, long timeout = 2000) { new Component(Testatoo.evaluator, new jQueryIdProvider(jQuery, timeout)) }

}
