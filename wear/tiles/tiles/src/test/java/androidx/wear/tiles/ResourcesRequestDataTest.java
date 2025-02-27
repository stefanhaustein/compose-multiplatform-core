/*
 * Copyright 2021 The Android Open Source Project
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

package androidx.wear.tiles;

import static com.google.common.truth.Truth.assertThat;

import android.os.Parcel;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.wear.tiles.proto.RequestProto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.internal.DoNotInstrument;

@RunWith(AndroidJUnit4.class)
@DoNotInstrument
public final class ResourcesRequestDataTest {
    @Test
    public void toParcelAndBack() {
        RequestProto.ResourcesRequest request =
                RequestProto.ResourcesRequest.newBuilder().setVersion("v123").build();
        ResourcesRequestData wrapper =
                new ResourcesRequestData(
                        request.toByteArray(), ResourcesRequestData.VERSION_PROTOBUF);

        Parcel parcel = Parcel.obtain();
        wrapper.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        assertThat(ResourcesRequestData.CREATOR.createFromParcel(parcel)).isEqualTo(wrapper);
    }
}
