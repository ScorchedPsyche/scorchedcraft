/*
 * Copyright (c) 2021 ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtil
{
    public static boolean isResultSetNullOrEmpty(ResultSet rs) throws SQLException
    {
        // isBeforeFirst() returns
        //   - true if the cursor is before the first row;
        //   - false if the cursor is at any other  position or the result set contains no rows
        return !rs.isBeforeFirst();
    }
}

