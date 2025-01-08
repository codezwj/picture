package com.juicew.juicepicbackend.api.imagesearch;

import com.juicew.juicepicbackend.api.imagesearch.model.ImageSearchResult;
import com.juicew.juicepicbackend.api.imagesearch.sub.GetImageFirstUrlApi;
import com.juicew.juicepicbackend.api.imagesearch.sub.GetImageListApi;
import com.juicew.juicepicbackend.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {
    //外观模式（门面模式）
  
    /**  
     * 搜索图片  
     *  
     * @param imageUrl  
     * @return  
     */  
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;  
    }  
  
    public static void main(String[] args) {  
        // 测试以图搜图功能  
        String imageUrl = "https://www.juicew.com/upload/blue.jpg";
        List<ImageSearchResult> resultList = searchImage(imageUrl);  
        System.out.println("结果列表" + resultList);  
    }  
}
