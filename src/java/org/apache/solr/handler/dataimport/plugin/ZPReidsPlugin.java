package org.apache.solr.handler.dataimport.plugin;

import java.util.Map;

import org.apache.solr.handler.dataimport.DataImporter;
import org.apache.solr.handler.dataimport.DocBuilder;
import org.apache.solr.handler.dataimport.DocWrapper;
import org.apache.solr.handler.dataimport.config.DIHConfiguration;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ZPReidsPlugin {

  public static void process(DocBuilder docBuilder, DataImporter dataImporter, DIHConfiguration config, DocWrapper doc) {
    Map<String,String> cacheDS = config.getDataSources().get("cache");
    if(cacheDS==null || dataImporter.getJedisClient()==null){
      return;
    }
    
    String cha = dataImporter.getJedisClient().get("redis");
//    JSONObject obj = JsonUtils.json2Obj(cha, JSONObject.class);
//    if(null!=obj){
      docBuilder.addFieldToDoc(cha, "isCached", 1, false, doc);
//    }
    
  }
  
}
