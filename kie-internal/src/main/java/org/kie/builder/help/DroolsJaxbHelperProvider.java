/*
 * Copyright 2010 JBoss Inc
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

package org.kie.builder.help;

import java.io.IOException;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.kie.internal.KnowledgeBase;
import org.kie.builder.KnowledgeBuilder;
import org.kie.io.Resource;

import com.sun.tools.xjc.Options;

/**
 * DroolsJaxbHelperProvider is used by the DroolsJaxbHelperFactory to "provide" it's concrete implementation.
 * 
 * This class is not considered stable and may change, the user is protected from this change by using 
 * the Factory api, which is considered stable.
 *
 * 
 * <p>This api is experimental and thus the classes and the interfaces returned are subject to change.</p>
 */
public interface DroolsJaxbHelperProvider {
    /**
     * Generates pojos for a given XSD using XJC and adds them to the specified KnowlegeBase.
     * 
     * @deprecated
     *     This is now deprecated, as we now support XSD as a standard ResourceType. Although
     *     you'll still need the newJAXBContext method to create a JAXBContext from the KnowledgeBase. 
     * 
     * @param resource
     *     The resource to the XSD model
     * @param kbuilder
     *     the KnowledgeBuilder where the generated .class files will be placed
     * @param xjcOpts
     *     XJC Options
     * @param systemId
     *     XJC systemId
     * @return
     *     Returns an array of class names that where generated by the XSD.
     * @throws IOException
     */
    public String[] addXsdModel(Resource resource,
                                KnowledgeBuilder kbuilder,
                                Options xjcOpts,
                                String systemId) throws IOException;

    /**
     * Creates a new JAXBContext, from which the Marshaller and Unmarshaller can be created, which are used by the Transformer
     * pipeline stage.
     * 
     * @param classNames
     *     An array of class names that can be resolved by this JAXBContext
     * @param properties
     *     JAXB properties
     * @param kbase
     *     The KnowledgeBase
     * @return
     *     The JAXB Context
     * @throws JAXBException
     */
    public JAXBContext newJAXBContext(String[] classNames,
                                      Map<String, ? > properties,
                                      KnowledgeBase kbase) throws JAXBException;
}
