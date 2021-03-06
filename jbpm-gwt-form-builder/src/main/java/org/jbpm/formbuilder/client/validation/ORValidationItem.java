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
package org.jbpm.formbuilder.client.validation;

import org.jbpm.formbuilder.parent.client.validation.OtherValidationsAware;
import java.util.List;

import org.jbpm.model.formapi.client.FormBuilderException;
import org.jbpm.model.formapi.client.validation.FBValidationItem;
import org.jbpm.model.formapi.shared.api.FBValidation;
import org.jbpm.formapi.shared.api.validation.ORValidation;
import org.jbpm.formbuilder.parent.client.FormBuilderGlobals;
import org.jbpm.model.formbuilder.client.messages.I18NConstants;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.reflection.client.Reflectable;
import org.jbpm.model.formapi.client.CommonGlobals;

@Reflectable
public class ORValidationItem extends FBValidationItem implements OtherValidationsAware {

    private final I18NConstants i18n = CommonGlobals.getInstance().getI18n();
    
    private SubValidationsList subValidations = null;
    
    @Override
    public String getName() {
        return i18n.ORValidationName();
    }
    
    @Override
    public void setExistingValidations(List<FBValidationItem> existingValidations) {
        subValidations = new SubValidationsList("OR", existingValidations);
    }

    @Override
    public FBValidation createValidation() {
        ORValidation validation = getRepresentation(new ORValidation());
        if (subValidations != null && subValidations.getItems() != null) {
            for (FBValidationItem subValidationItem : subValidations.getItems()) {
                FBValidation subValidation = subValidationItem.createValidation();
                validation.addValidation(subValidation);
            }
        }
        return validation;
    }

    @Override
    public Widget createDisplay() {
        return subValidations;
    }

    @Override
    public FBValidationItem cloneItem() {
        ORValidationItem clone = new ORValidationItem();
        for (FBValidationItem item : this.subValidations.getItems()) {
            clone.subValidations.addItem(item.cloneItem());
        }
        return clone;
    }

    @Override
    public void populate(FBValidation validation) throws FormBuilderException {
        if (!(validation instanceof ORValidation)) {
            throw new FormBuilderException(i18n.RepNotOfType(validation.getClass().getName(), "ORValidation"));
        }
        subValidations.clearItems();
        ORValidation or = (ORValidation) validation;
        List<FBValidation> subVals = or.getValidations();
        for (FBValidation subVal : subVals) {
            FBValidationItem item = createValidation(subVal);
            subValidations.addItem(item);
        }
    }

}
