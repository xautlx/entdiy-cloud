/**
 * Copyright © 2015 - 2020 EntDIY JavaEE Development Framework
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
package com.entdiy.mdm.service.impl;

import com.entdiy.common.service.BaseServiceImpl;
import com.entdiy.mdm.entity.ConfigParam;
import com.entdiy.mdm.mapper.ConfigParamMapper;
import com.entdiy.mdm.service.IConfigParamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigParamServiceImpl extends BaseServiceImpl<ConfigParamMapper, ConfigParam> implements IConfigParamService {

}
