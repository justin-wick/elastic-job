/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package com.dangdang.ddframe.job.internal.schedule;

import com.dangdang.ddframe.job.internal.execution.ExecutionService;
import com.dangdang.ddframe.job.internal.sharding.ShardingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class JobTriggerListenerTest {
    
    @Mock
    private ExecutionService executionService;
    
    @Mock
    private ShardingService shardingService;
    
    private JobTriggerListener jobTriggerListener;
    
    @Before
    public void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);
        jobTriggerListener = new JobTriggerListener(executionService, shardingService);
    }
    
    @Test
    public void assertGetName() {
        assertThat(jobTriggerListener.getName(), is("JobTriggerListener"));
    }
    
    @Test
    public void assertTriggerMisfired() {
        when(shardingService.getLocalHostShardingItems()).thenReturn(Collections.singletonList(0));
        jobTriggerListener.triggerMisfired(null);
        verify(shardingService).getLocalHostShardingItems();
        verify(executionService).setMisfire(Collections.singletonList(0));
    }
}
