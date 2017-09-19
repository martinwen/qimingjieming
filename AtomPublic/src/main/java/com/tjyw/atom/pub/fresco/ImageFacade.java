package com.tjyw.atom.pub.fresco;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import me.relex.photodraweeview.PhotoDraweeView;

/**
 * Created by stephen on 6/28/16.
 */
public class ImageFacade {

    public static void loadImage(int resId, SimpleDraweeView draweeView) {
        draweeView.setImageURI(ImageRequestBuilder.newBuilderWithResourceId(resId).build().getSourceUri());
    }

    public static void loadImage(String url, SimpleDraweeView draweeView) {
        if (TextUtils.isEmpty(url)) {
            draweeView.setImageURI((String) null);
        } else {
            draweeView.setImageURI(Uri.parse(url));
        }
    }

    /**
     * 加载图片+回调
     */
    public static void loadImage(Uri uri, SimpleDraweeView draweeView, ControllerListener controllerListener) {
        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder().setUri(uri);
        if (null != controllerListener) {
            controllerBuilder.setControllerListener(controllerListener);
        } else {
            controllerBuilder.setControllerListener(new ImageFacadeControllerListener());
        }
        draweeView.setController(controllerBuilder.build());
    }

    /** 显示url指定的网络图片：如果是PhotoDraweeView(缩放对象)，设置监听修改更新图片宽高，使手势缩放生效 */
    public static void loadImageFromController(String url, final SimpleDraweeView draweeView) {
        PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setUri(Uri.parse(url));

        if (draweeView instanceof PhotoDraweeView) {
            builder.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (null != imageInfo) {
                        ((PhotoDraweeView) draweeView).update(imageInfo.getWidth(), imageInfo.getHeight());
                    }
                }
            });
        }

        draweeView.setController(builder.build());
    }

    /** 加载并显示本地图片：其中url支持(+为支持；－为不支持)
     * +1.本地文件，file://
     * -2.ContentProvider，content://
     * -3.asset资源，asset://
     * -4.res资源,res://
     * */
    public static void loadImageWithFile(String path, final SimpleDraweeView draweeView, int widthPx, int heightPx) {
        loadImageWithFileAutoRotate(path, draweeView, true, widthPx, heightPx);
    }

    public static void loadImageWithFileAutoRotate(final String path, final SimpleDraweeView draweeView, boolean rotate, int widthPx, int heightPx) {
        if (widthPx > 0 && heightPx > 0) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(UriUtil.LOCAL_FILE_SCHEME + "://" + path))
                    .setResizeOptions(new ResizeOptions(widthPx, heightPx)).setAutoRotateEnabled(rotate).build();
            PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder().setOldController(draweeView.getController()).setImageRequest(request);
            if (draweeView instanceof PhotoDraweeView) {
                builder.setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (null != imageInfo) {
                            ((PhotoDraweeView) draweeView).update(imageInfo.getWidth(), imageInfo.getHeight());
                        }
                    }
                });
            }

            draweeView.setController(builder.build());
        } else if (widthPx == -1 || heightPx == -1) {
            draweeView.setImageURI(Uri.parse(UriUtil.LOCAL_FILE_SCHEME + "://" + path));
        }
    }

}
