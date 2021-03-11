/**
 * Copyright © 2015 - 2021 EntDIY JavaEE Development Framework
 *
 * Site: https://www.entdiy.com, E-Mail: xautlx@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.entdiy.mdm.controller.admin;

import com.entdiy.common.constant.UriPrefix;
import com.entdiy.common.controller.BaseRestController;
import com.entdiy.mdm.entity.ConfigParam;
import com.entdiy.mdm.service.IConfigParamService;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "配置参数管理")
@RequestMapping("/mdm" + UriPrefix.ADMIN + "/config-param")
public class ConfigParamController extends BaseRestController<IConfigParamService, ConfigParam> {

    @Getter
    @Autowired
    private IConfigParamService baseService;

}
