package com.tjyw.atom.pub.fresco;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;

import timber.log.Timber;

/**
 * Created by stephen on 6/28/16.
 */
public class ImageFacadeControllerListener extends BaseControllerListener<ImageInfo> {

    static final String TAG = ImageFacadeControllerListener.class.getSimpleName();

    @Override
    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
        if (imageInfo == null) {
            return;
        }
        QualityInfo qualityInfo = imageInfo.getQualityInfo();
        Timber.tag(TAG).e("Final image received! Size %d x %d, Quality level %d, good enough: %s, full quality: %s",
                imageInfo.getWidth(),
                imageInfo.getHeight(),
                qualityInfo.getQuality(),
                qualityInfo.isOfGoodEnoughQuality(),
                qualityInfo.isOfFullQuality());
    }

    @Override
    public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
        Timber.tag(TAG).e("Intermediate image received");
    }

    @Override
    public void onFailure(String id, Throwable throwable) {
        Timber.tag(TAG).e(throwable, "Error loading %s", id);
    }
}
