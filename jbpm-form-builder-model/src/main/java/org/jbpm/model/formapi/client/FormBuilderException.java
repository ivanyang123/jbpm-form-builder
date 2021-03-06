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
package org.jbpm.model.formapi.client;

/**
 * Base exception from client side.
 */
public class FormBuilderException extends Exception {

    private static final long serialVersionUID = 3248011011993977193L;

    public FormBuilderException() {
        super();
    }

    public FormBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormBuilderException(String message) {
        super(message);
    }

    public FormBuilderException(Throwable cause) {
        super(cause);
    }
}
