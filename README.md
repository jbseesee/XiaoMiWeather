# XiaoMiWeather
仿写MIUI9之前的小米天气app,采用 MVP + RxJava2 + Retrofit2 架构的项目。
     
# 项目截图
![](https://github.com/jbseesee/XiaoMiWeather/blob/master/%E6%99%B4%E5%A4%A9.gif "晴天动画")
![](https://github.com/jbseesee/XiaoMiWeather/blob/master/%E9%98%B4%E5%A4%A9.gif "阴天动画")
![](https://github.com/jbseesee/XiaoMiWeather/blob/master/%E9%9B%A8%E5%A4%A9.gif "雨天动画")
![](https://github.com/jbseesee/XiaoMiWeather/blob/master/%E6%8A%98%E7%BA%BF%E5%9B%BE%E5%92%8C%E8%90%BD%E6%97%A5%E5%8A%A8%E7%94%BB.gif "折线图滑动效果和日落动画")
![](https://github.com/jbseesee/XiaoMiWeather/blob/master/4%E5%A4%A9%E9%A2%84%E6%B5%8B.png "4天预测页面")
![](https://github.com/jbseesee/XiaoMiWeather/blob/master/%E5%9F%8E%E5%B8%82%E6%B7%BB%E5%8A%A0.png "城市添加页面")
![](https://github.com/jbseesee/XiaoMiWeather/blob/master/%E5%9F%8E%E5%B8%82%E7%AE%A1%E7%90%86.png "城市管理页面")

# 特点

* 数据源API为彩云天气提供
* 定位服务为高德地图提供
* 使用ViewPager + Fragment 展示多个地区天气
* 根据天气状况展示不同的天气动画，目前有3种天气效果动画
* 折线图滑动时，其区间天气图标可自动跟随
* 使用RecyclerView展示和管理已添加城市
* 使用ListView展示搜索结果
* 热门城市布局里每个Item根据自己的宽度自动排列和换行

# 更新日志

# 开源库
* [Retrofit](https://github.com/square/retrofit)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [LitePal](https://github.com/LitePalFramework/LitePal)
* [RxPermissions](https://github.com/tbruyelle/RxPermissions)
