import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * Created by chenhao on 2/18/16.
 */
public class DataCrawler {

    public static AppStorePaidRankProcessor appStorePaidRankProcessor = new AppStorePaidRankProcessor();
    public static FloatUpRankPageProcessor floatUpRankPageProcessor = new FloatUpRankPageProcessor();
    public static AppInfoController appInfoController = new AppInfoController();

    public static void main(String args[]) {
        Spider.create(appStorePaidRankProcessor)
                .addUrl(AppStorePaidRankProcessor.PAGE_URL)
                .addPipeline(new PaidRankPipeline(appInfoController))
                .thread(1)
                .run();

        Spider.create(floatUpRankPageProcessor)
                .addUrl(FloatUpRankPageProcessor.PAGE_URL)
                .addPipeline(new UpRankPipeline(appInfoController))
                .thread(1)
                .run();

        List appIdList = appInfoController.getAppIdList();
        appIdList = Toolkit.removeDuplicate(appIdList);

        System.out.println("-----------------------");
        System.out.println("all id list");

        int i = 1;
        for (Object id : appIdList) {
            System.out.println(i + " " + id.toString());
            i++;
        }


        System.out.println("-----------------------");
        System.out.println("all app info list");
        List<AppData> dataList = appInfoController.fetchAppInfo();
        for (AppData appData : dataList) {
            System.out.println(appData.ranking + " " + appData.id + "  " + "  " + appData.averageUserRating + "  " + appData.userRatingCount + "  "
                    + appData.userRatingCountForCurrentVersion);
        }
    }


}