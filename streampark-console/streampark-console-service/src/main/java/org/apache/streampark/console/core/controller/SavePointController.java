/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.streampark.console.core.controller;

import org.apache.streampark.console.base.domain.RestRequest;
import org.apache.streampark.console.base.domain.RestResponse;
import org.apache.streampark.console.base.exception.InternalException;
import org.apache.streampark.console.core.annotation.PermissionScope;
import org.apache.streampark.console.core.entity.Application;
import org.apache.streampark.console.core.entity.SavePoint;
import org.apache.streampark.console.core.service.SavePointService;
import org.apache.streampark.console.core.service.application.ApplicationManageService;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;

@Slf4j
@Validated
@RestController
@RequestMapping("flink/savepoint")
public class SavePointController {

    @Autowired
    private ApplicationManageService applicationManageService;

    @Autowired
    private SavePointService savePointService;

    @PostMapping("history")
    @PermissionScope(app = "#sp.appId", team = "#sp.teamId")
    public RestResponse history(SavePoint sp, RestRequest request) {
        IPage<SavePoint> page = savePointService.getPage(sp, request);
        return RestResponse.success(page);
    }

    @PostMapping("delete")
    @RequiresPermissions("savepoint:delete")
    @PermissionScope(app = "#sp.appId", team = "#sp.teamId")
    public RestResponse delete(SavePoint sp) throws InternalException {
        SavePoint savePoint = savePointService.getById(sp.getId());
        Application application = applicationManageService.getById(savePoint.getAppId());
        Boolean deleted = savePointService.remove(sp.getId(), application);
        return RestResponse.success(deleted);
    }

    @PostMapping("trigger")
    @PermissionScope(app = "#savePoint.appId", team = "#savePoint.teamId")
    @RequiresPermissions("savepoint:trigger")
    public RestResponse trigger(
                                Long appId, @Nullable String savepointPath, @Nullable Boolean nativeFormat) {
        savePointService.trigger(appId, savepointPath, nativeFormat);
        return RestResponse.success(true);
    }
}
