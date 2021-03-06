/*
 * Copyright 2011 JBoss Inc 
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
package org.jbpm.formbuilder.parent.client.effect;

import org.jbpm.model.formapi.client.CommonGlobals;
import org.jbpm.model.formapi.client.effect.FBFormEffect;
import org.jbpm.model.formapi.client.form.FBFormItem;
import org.jbpm.formbuilder.parent.client.FormBuilderGlobals;

import com.gwtent.reflection.client.Reflectable;

/**
 * Allows the copy action from the right click menu of
 * the related {@link FBFormItem}
 */
@Reflectable
public class CopyFormEffect extends FBFormEffect {

    public CopyFormEffect() {
        super(CommonGlobals.getInstance().getI18n().CopyFormEffectLabel(), false);
    }

    @Override
    protected void createStyles() {
        CommonGlobals.getInstance().copy().append(getItem()).execute();
    }

}
