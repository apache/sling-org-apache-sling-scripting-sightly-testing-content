/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sling.scripting.sightly.testing.adaptable.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.scripting.sightly.testing.adaptable.RequestAdapterUseObject;
import org.osgi.service.component.annotations.Component;

@Component(
        property = {
            AdapterFactory.ADAPTABLE_CLASSES + "=org.apache.sling.api.SlingHttpServletRequest",
            AdapterFactory.ADAPTER_CLASSES
                    + "=org.apache.sling.scripting.sightly.testing.adaptable.RequestAdapterUseObject"
        })
public class RequestAdapterFactory implements AdapterFactory {

    @Override
    public <AdapterType> AdapterType getAdapter(Object adaptable, Class<AdapterType> type) {
        if (type == RequestAdapterUseObject.class && adaptable instanceof SlingHttpServletRequest) {
            SlingHttpServletRequest request = (SlingHttpServletRequest) adaptable;
            Resource resource = request.getResource();
            ValueMap properties = resource.adaptTo(ValueMap.class);
            String title = (String) properties.get("jcr:title");
            return (AdapterType) new RequestAdapterUseObjectImpl(title);
        }
        return null;
    }
}
