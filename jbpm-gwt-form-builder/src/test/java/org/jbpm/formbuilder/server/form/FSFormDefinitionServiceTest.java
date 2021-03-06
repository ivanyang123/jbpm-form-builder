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
package org.jbpm.formbuilder.server.form;


import java.io.File;
import java.util.List;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import org.jbpm.formapi.server.form.FormEncodingServerFactory;
import org.jbpm.model.formapi.shared.api.FormItemRepresentation;
import org.jbpm.model.formapi.shared.api.FormRepresentation;
import org.jbpm.model.formapi.shared.form.FormEncodingFactory;
import org.jbpm.formbuilder.server.RESTAbstractTest;
import org.jbpm.formbuilder.server.file.FSFileService;

public class FSFormDefinitionServiceTest extends TestCase {

    private String baseUrl = "/tmp";
    private String fileSeparator = System.getProperty("file.separator");
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FormEncodingFactory.register(FormEncodingServerFactory.getEncoder(), FormEncodingServerFactory.getDecoder());
        FileUtils.deleteDirectory(new File(baseUrl+fileSeparator+"somePackage"));
    }
    
    protected void tearDown() throws Exception {
        FileUtils.deleteDirectory(new File(baseUrl+fileSeparator+"somePackage"));
    }
    //test happy path for insert for GuvnorFormDefinitionService.saveForm(...)
    public void testSaveFormOK() throws Exception {
        
        
        FSFormDefinitionService service = createService(baseUrl);
        
        FormRepresentation form = RESTAbstractTest.createMockForm("form1", "oneParam");
        
        
        String formId = service.saveForm("somePackage", form);
        
        assertEquals(formId, form.getName());
        
        String formUrl = baseUrl+fileSeparator+"somePackage"+fileSeparator+"form1"+"AutoForm.formdef";
        assertTrue(new File(formUrl).exists());
        
        FileUtils.deleteQuietly(new File(formUrl));
        
        
    }
    






    public void testSaveFormItemOK() throws Exception {
        

        
        FSFormDefinitionService service = createService(baseUrl);
        
        FormItemRepresentation item = RESTAbstractTest.createMockForm("form1", "oneParam").getFormItems().iterator().next();
        
        
        String itemId = service.saveFormItem("somePackage", "item1", item);
        
        String itemUrl = baseUrl + fileSeparator + "somePackage" + fileSeparator + "formItemDefinition_item1.json";
        
        assertTrue(new File(itemUrl).exists());
        
        FileUtils.deleteQuietly(new File(itemUrl));
        
    }


    public void testGetFormOK() throws Exception {
        
        FormRepresentation form = RESTAbstractTest.createMockForm("form1", "oneParam");
        FSFormDefinitionService service = createService(baseUrl);
        
        String formId = service.saveForm("somePackage", form);
        
        String formUrl = baseUrl + fileSeparator + "somePackage" + fileSeparator + "form1AutoForm" +".formdef";
  
        FormRepresentation form1 = service.getForm("somePackage", "form1AutoForm");
        
        assertNotNull(form1);
        
        
    }
    



    public void testGetFormItemOK() throws Exception {
        
        FSFormDefinitionService service = createService(baseUrl);
        

        FormRepresentation form = RESTAbstractTest.createMockForm("myForm", "myParam");
        String formId = service.saveForm("somePackage", form);
        
        String formUrl = baseUrl + fileSeparator + "somePackage" + fileSeparator + "myFormAutoForm" +".formdef";
   
        assertTrue(new File(formUrl).exists());
        
        FormRepresentation form2 = service.getForm("somePackage", formId);
        
        assertNotNull(form2);
        
    }

    public void testGetFormsOK() throws Exception {
        
        FSFormDefinitionService service = createService(baseUrl);
        FormRepresentation form1 = RESTAbstractTest.createMockForm("form1", "oneParam"); // this add to the name AutoForm
        service.saveForm("somePackage", form1);
        FormRepresentation form2 = RESTAbstractTest.createMockForm("form2", "anotherParam");// this add to the name AutoForm
        service.saveForm("somePackage", form2);
        
       
        List<FormRepresentation> forms = service.getForms("somePackage");
        assertEquals(2, forms.size());
        boolean form1ok = false;
        boolean form2ok = false;
        if("form1AutoForm".equals(forms.get(0).getName()) || "form1AutoForm".equals(forms.get(1).getName())){
            form1ok = true;
        }
        if("form2AutoForm".equals(forms.get(0).getName()) || "form2AutoForm".equals(forms.get(1).getName())){
            form2ok = true;
        }
        assertTrue(form1ok);
        assertTrue(form2ok);
        
    }

    
    public void testDeleteFormOK() throws Exception {
        FSFormDefinitionService service = createService(baseUrl);
        FormRepresentation form1 = RESTAbstractTest.createMockForm("form1", "oneParam");
        String formUrl = service.saveForm("somePackage", form1);
        System.out.println("Form Url = "+formUrl);
        
        String deleteUrl = baseUrl + fileSeparator + "somePackage" + fileSeparator 
                + formUrl + ".formdef";
        assertTrue(new File(deleteUrl).exists());
        
        //service.deleteForm("somePackage", "form1"+"AutoForm"); you can do it in this way or
        service.deleteForm(deleteUrl);
        
        assertFalse(new File(deleteUrl).exists());
        
        
    }


    public void testDeleteFormItemOK() throws Exception {
        FSFormDefinitionService service = createService(baseUrl);
        
        FormItemRepresentation item = RESTAbstractTest.createMockForm("form1", "oneParam").getFormItems().iterator().next();
        
        
        String itemId = service.saveFormItem("somePackage", "item1", item);
        
        String itemUrl = baseUrl + fileSeparator + "somePackage" + fileSeparator + "formItemDefinition_"+itemId +".json";
        
        assertTrue(new File(itemUrl).exists());
        
        service.deleteFormItem("somePackage", "formItemDefinition_"+itemId);
        
        assertFalse(new File(itemUrl).exists());
        
    }
    

//    public void testSaveTemplateInsertOK() throws Exception {
//        GuvnorFormDefinitionService service = createService(baseUrl, "", "");
//        HttpClient client = EasyMock.createMock(HttpClient.class);
//
//        Map<String, String> responses2 = new HashMap<String, String>();
//        Map<String, String> responses3 = new HashMap<String, String>();
//        
//        Map<String, Integer> statuses1 = new HashMap<String, Integer>();
//        statuses1.put("GET " + helper.getApiSearchUrl("somePackage") + "template.txt", 404);
//        responses2.put("POST " + helper.getRestBaseUrl() + "somePackage/assets", "OK");
//        responses3.put("PUT " + helper.getRestBaseUrl() + "somePackage/assets/template/source", "OK");
//        EasyMock.expect(client.executeMethod(EasyMock.isA(MockGetMethod.class))).
//            andAnswer(new MockAnswer(statuses1)).once();
//        EasyMock.expect(client.executeMethod(EasyMock.isA(MockPostMethod.class))).
//            andAnswer(new MockAnswer(responses2, new IllegalArgumentException("unexpected call"))).once();
//        EasyMock.expect(client.executeMethod(EasyMock.isA(MockPutMethod.class))).
//            andAnswer(new MockAnswer(responses3, new IllegalArgumentException("unexpected call"))).once();
//        service.getHelper().setClient(client);
//        
//        EasyMock.replay(client);
//        service.saveTemplate("somePackage", "template.txt", "my template content");
//        EasyMock.verify(client);
//    }
//    
//    public void testSaveTemplateUpdateOK() throws Exception {
//        GuvnorFormDefinitionService service = createService(baseUrl, "", "");
//        HttpClient client = EasyMock.createMock(HttpClient.class);
//
//        Map<String, String> responses1 = new HashMap<String, String>();
//        Map<String, String> responses2 = new HashMap<String, String>();
//        
//        responses1.put("GET " + helper.getApiSearchUrl("somePackage") + "template.txt", "old template content");
//        responses2.put("PUT " + helper.getRestBaseUrl() + "somePackage/assets/template/source", "OK");
//        EasyMock.expect(client.executeMethod(EasyMock.isA(MockGetMethod.class))).
//            andAnswer(new MockAnswer(responses1, new IllegalArgumentException("unexpected call"))).once();
//        EasyMock.expect(client.executeMethod(EasyMock.isA(MockPutMethod.class))).
//            andAnswer(new MockAnswer(responses2, new IllegalArgumentException("unexpected call"))).once();
//        service.getHelper().setClient(client);
//        
//        EasyMock.replay(client);
//        service.saveTemplate("somePackage", "template.txt", "my template content");
//        EasyMock.verify(client);
//    }
//

    
   private FSFormDefinitionService createService(String baseUrl) {
        FSFormDefinitionService service = new FSFormDefinitionService();
        
        service.setBaseUrl(baseUrl);
        service.setFileService(new FSFileService(baseUrl));
        return service;
    }
}
