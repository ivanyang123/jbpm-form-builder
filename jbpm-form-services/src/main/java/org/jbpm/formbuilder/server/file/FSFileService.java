/*
 * Copyright 2012 JBoss by Red Hat.
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
package org.jbpm.formbuilder.server.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author salaboy
 */
public class FSFileService implements FileService, InitializingBean {

    private String baseUrl;
    private String fileSeparator = System.getProperty("file.separator");

    public FSFileService() {
    }

    public FSFileService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    
    
    public String storeFile(String packageName, String fileName, byte[] content) throws FileException {
        String url = baseUrl + fileSeparator + packageName + fileSeparator + fileName;
        File file = new File(url);
        try {
            FileUtils.writeByteArrayToFile(file, content);
        } catch (IOException ex) {
            throw new FileException(ex.getMessage(), ex);
        }
        return url;

    }

    public void deleteFile(String packageName, String fileName) throws FileException {
        File file = new File(baseUrl + fileSeparator + packageName + fileSeparator + fileName);
        FileUtils.deleteQuietly(file);
    }

    public void deleteFile(String url) throws FileException {
        File file = new File(url);
        FileUtils.deleteQuietly(file);
    }

    public List<String> loadFilesByType(String packageName, String fileType) throws FileException {
        File baseDir = new File(baseUrl);
        Collection<File> listFiles = FileUtils.listFiles(baseDir, new String[]{fileType}, true);
        List<String> files = new ArrayList<String>();
        for (File file : listFiles) {
            files.add(file.getAbsolutePath());
        }
        return files;
    }

    public byte[] loadFile(String packageName, String fileName) throws FileException {
        File file = new File(baseUrl + fileSeparator + packageName + fileSeparator + fileName);
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException ex) {
            throw new FileException(ex.getMessage(), ex);
        }
    }

    public void afterPropertiesSet() throws Exception {
        //do nothing
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
