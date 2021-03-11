/**
 * Copyright © 2015 - 2017 EntDIY JavaEE Development Framework
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
package com.entdiy.common.web.json;

public class JsonViews {

    public interface Public {
    }

    public interface ReadOnly extends Public {
    }

    public interface ReadWrite extends ReadOnly {
    }

    public interface AppReadOnly extends ReadOnly {
    }

    public interface AppReadWrite extends AppReadOnly,ReadWrite {
    }

    public interface AdminReadOnly extends AppReadOnly {
    }

    public interface AdminReadWrite extends AppReadWrite {
    }


}
