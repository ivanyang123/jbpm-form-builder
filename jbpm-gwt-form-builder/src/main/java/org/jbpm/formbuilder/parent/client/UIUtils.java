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
package org.jbpm.formbuilder.parent.client;

import org.gwt.mosaic.ui.client.DesktopPanel;
import org.gwt.mosaic.ui.client.WindowPanel;

public class UIUtils {

    public static WindowPanel createWindow(String title) {
        return new WindowPanel(new DesktopPanel() {
            @Override
            public void makeDraggable(WindowPanel w) {
                //do nothing to avoid gwt-dnd issue 43
            }
        }, title, false, true);
    }
}
