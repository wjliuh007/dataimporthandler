package org.apache.solr.handler.dataimport.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.handler.dataimport.DataImporter;
import org.apache.solr.handler.dataimport.DocBuilder;
import org.apache.solr.handler.dataimport.DocWrapper;
import org.apache.solr.handler.dataimport.config.DIHConfiguration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
  
  public static void process(DocBuilder docBuilder, DataImporter dataImporter, DIHConfiguration config, DocWrapper doc,Long productid) {
    int isActivity = 0;
    Map<String,String> cacheDS = config.getDataSources().get("cache");
    if(cacheDS!=null && dataImporter.getJedisClient()!=null){
      String key = String.format("{prod}_product_all_activity_%s", productid);
      String activityInfo = dataImporter.getJedisClient().get(key);
      Map<String, String> allMap = JSON.parseObject(activityInfo, Map.class);
      if(null!=allMap){
        List<String> all = new ArrayList<String>();
        all.add(allMap.get("{prod}_activity_prod_"));
        all.add(allMap.get("{prod}_full_discount_flag_"));
        all.add(allMap.get("{prod}_full_free_flag_"));
        all.add(allMap.get("{prod}_alterPrice_flag_"));
        for(String info:all){
          if ((info != null)) {
            JSONObject parse = (JSONObject) JSONObject.parse(info);
            if (containsTime(parse.getInteger("startTime"),parse.getInteger("endTime"))) {
              isActivity = 1;
              break;
            }
          }
        }
      }
    }
    
    docBuilder.addFieldToDoc(isActivity, "is_activity", 1, false, doc);
    
  }
  
  /**
   * @author wangyangyang
   * @date 2017-3-30上午9:42:17
   */
  private static boolean containsTime(Integer startTime,Integer endTime) {
    Long nowTime = System.currentTimeMillis() / 1000;
    int now= Integer.valueOf(nowTime.toString());
    if (now <= endTime && now>=startTime) {
      return true;
    }
    return false;
  }
  
}
