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

import java.util.HashMap;
import java.util.Map;

import org.jbpm.model.formapi.client.CommonGlobals;
import org.jbpm.model.formapi.client.effect.FBFormEffect;
import org.jbpm.model.formapi.client.form.FBFormItem;
import org.jbpm.formbuilder.parent.client.FormBuilderGlobals;
import org.jbpm.model.formbuilder.client.bus.UndoableEvent;
import org.jbpm.model.formbuilder.client.bus.UndoableHandler;
import org.jbpm.model.formbuilder.client.form.items.TableLayoutFormItem;

import com.google.gwt.event.shared.EventBus;
import com.gwtent.reflection.client.Reflectable;

@Reflectable
public class AddColumnFormEffect extends FBFormEffect {

    private final EventBus bus = CommonGlobals.getInstance().getEventBus();
    
    public AddColumnFormEffect() {
        super(CommonGlobals.getInstance().getI18n().AddColumnEffectLabel(), false);
    }
    
    @Override
    public boolean isValidForItem(FBFormItem item) {
        return super.isValidForItem(item) && item instanceof TableLayoutFormItem;
    }

    @Override
    protected void createStyles() {
        final Map<String, Object> dataSnapshot = new HashMap<String, Object>();
        dataSnapshot.put("selectedX", getParent().getAbsoluteLeft());
        dataSnapshot.put("item", getItem());
        bus.fireEvent(new UndoableEvent(dataSnapshot, new UndoableHandler() {
            @Override
            public void undoAction(UndoableEvent event) {
                TableLayoutFormItem item = (TableLayoutFormItem) event.getData("item");
                Integer selectedX = (Integer) event.getData("selectedX");
                int columnNumber = item.getColumnForXCoordinate(selectedX);
                item.removeColumn(columnNumber);
            }
            @Override
            public void onEvent(UndoableEvent event) { }
            @Override
            public void doAction(UndoableEvent event) {
                TableLayoutFormItem item = (TableLayoutFormItem) event.getData("item");
                Integer selectedX = (Integer) event.getData("selectedX");
                int columnNumber = item.getColumnForXCoordinate(selectedX);
                item.addColumn(columnNumber);
            }
        }));
    }
}
