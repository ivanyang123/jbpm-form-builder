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
package org.jbpm.formbuilder.client.menu.items;

import java.util.List;

import org.jbpm.model.formapi.client.effect.FBFormEffect;
import org.jbpm.model.formapi.client.form.FBFormItem;
import org.jbpm.formapi.client.menu.FBMenuItem;
import org.jbpm.formbuilder.parent.client.FormBuilderGlobals;
import org.jbpm.model.formbuilder.client.form.items.RangeFieldFormItem;
import org.jbpm.model.formbuilder.client.resources.FormBuilderResources;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Label;
import com.gwtent.reflection.client.Reflectable;
import org.jbpm.model.formapi.client.CommonGlobals;

@Reflectable
public class RangeFieldMenuItem extends FBMenuItem {

    public RangeFieldMenuItem() {
        super();
    }
    
    public RangeFieldMenuItem(List<FBFormEffect> formEffects) {
        super(formEffects);
    }
    
    @Override
    public Label getDescription() {
        return new Label(CommonGlobals.getInstance().getI18n().MenuItemRangeField());
    }

    @Override
    protected ImageResource getIconUrl() {
        return FormBuilderResources.INSTANCE.rangeField();
    }

    @Override
    public FBMenuItem cloneWidget() {
        return clone(new RangeFieldMenuItem());
    }

    @Override
    public FBFormItem buildWidget() {
        return build(new RangeFieldFormItem());
    }

}
