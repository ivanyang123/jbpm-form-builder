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
package org.jbpm.formapi.shared.menu;

import java.util.List;
import java.util.Map;

import org.jbpm.model.formapi.shared.menu.MenuItemDescription;
import org.jbpm.model.formapi.shared.menu.MenuOptionDescription;
import org.jbpm.model.formapi.shared.menu.ValidationDescription;

public interface MenuService {

    List<MenuOptionDescription> listOptions() throws MenuServiceException;
    
    Map<String, List<MenuItemDescription>> listMenuItems() throws MenuServiceException;
    
    void saveMenuItem(String groupName, MenuItemDescription item) throws MenuServiceException;
    
    void deleteMenuItem(String groupName, MenuItemDescription item) throws MenuServiceException;

    Map<String, String> getFormBuilderProperties() throws MenuServiceException;

    List<ValidationDescription> listValidations() throws MenuServiceException;
}
