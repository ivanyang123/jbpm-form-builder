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
package org.jbpm.formdisplay.client;

import org.jbpm.model.formapi.client.CommonGlobals;
import org.jbpm.model.formapi.client.FormBuilderException;
import org.jbpm.formapi.client.form.FormEncodingClientFactory;
import org.jbpm.model.formapi.shared.form.FormEncodingException;
import org.jbpm.model.formapi.shared.form.FormEncodingFactory;
import org.jbpm.formbuilder.parent.client.FormBuilderGlobals;
import org.jbpm.formbuilder.parent.client.FormBuilderService;
import org.jbpm.formbuilder.parent.client.JsonLoadInput;
import org.jbpm.formbuilder.parent.client.bus.ui.RepresentationFactoryPopulatedEvent;
import org.jbpm.formbuilder.parent.client.bus.ui.RepresentationFactoryPopulatedHandler;
import org.jbpm.model.formbuilder.client.form.FBForm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 */
public class FormDisplayController {

    public FormDisplayController(RootPanel formInfo, final RootPanel formDisplay) {
        EventBus eventBus = CommonGlobals.getInstance().getEventBus();
        FormBuilderService server = FormBuilderGlobals.getInstance().getService();
        FormEncodingFactory.register(FormEncodingClientFactory.getEncoder(), FormEncodingClientFactory.getDecoder());
        try {
            final String innerJson = formInfo.getElement().getInnerHTML();
            formInfo.getElement().setInnerHTML("");
            formInfo.getElement().getStyle().setHeight(1, Unit.PX);
            eventBus.addHandler(RepresentationFactoryPopulatedEvent.TYPE, new RepresentationFactoryPopulatedHandler() {
                @Override
                public void onEvent(RepresentationFactoryPopulatedEvent event) {
                    try {
                        JsonLoadInput input  = JsonLoadInput.parse(innerJson);
                        
                        if (input != null && input.getForm() != null) {
                            FBForm formUI = new FBForm();
                            formUI.populate(input.getForm());
                            formDisplay.add(formUI.asFormPanel(input.getFormData()));
                        } 
                    } catch (FormEncodingException e) {
                        String error = "Couldn't interpretate form: " + e.getMessage();
                        
                        for(StackTraceElement element: e.getStackTrace()){
                            error += " - " +element.getClassName() + element.getMethodName() +":" + element.getLineNumber() +"\n";
                        }
                        Window.alert(error);
                        GWT.log("Couldn't interpretate form", e);
                    } catch (FormBuilderException e) {
                        String error = "Couldn't populate display FormBuilderException: " + e.getMessage();
                        
                        for(StackTraceElement element: e.getStackTrace()){
                            error += " - " +element.getClassName() + element.getMethodName() +":" + element.getLineNumber() +"\n";
                        }
                        Window.alert(error);
                        GWT.log("Couldn't populate display", e);
                    } catch (Exception e) {
                        String error = "Couldn't populate display generic ex: " + e.getMessage();
                        
                        for(StackTraceElement element: e.getStackTrace()){
                            error += " - " +element.getClassName() + element.getMethodName() +":" + element.getLineNumber() +"\n";
                        }
                        Window.alert(error);
                        GWT.log("Couldn't populate display", e);
                    }
                }
            });
            server.populateRepresentationFactory();
        } catch (FormBuilderException e) {
            Window.alert("Couldn't populate representation factory: " + e.getMessage());
            GWT.log("Couldn't populate representation factory", e);
        }
    }

    
}
