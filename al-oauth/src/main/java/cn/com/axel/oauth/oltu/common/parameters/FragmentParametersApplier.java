/**
 * Copyright 2010 Newcastle University
 * <p>
 * http://research.ncl.ac.uk/smart/
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.axel.oauth.oltu.common.parameters;

import cn.com.axel.oauth.oltu.common.OAuth;
import cn.com.axel.oauth.oltu.common.message.OAuthMessage;
import cn.com.axel.oauth.oltu.common.utils.OAuthUtils;

import java.util.Map;

public class FragmentParametersApplier implements OAuthParametersApplier {

    public OAuthMessage applyOAuthParameters(OAuthMessage message, Map<String, Object> params) {

        String messageUrl = message.getLocationUri();
        if (messageUrl != null) {
            StringBuilder url = new StringBuilder(messageUrl);
            params.remove(OAuth.OAUTH_REFRESH_TOKEN);
            String fragmentQuery = OAuthUtils.format(params.entrySet(), "UTF-8");
            if (!OAuthUtils.isEmpty(fragmentQuery)) {
                if (!params.isEmpty()) {
                    url.append("#").append(fragmentQuery);
                }
            }
            message.setLocationUri(url.toString());
        }
        return message;
    }
}
