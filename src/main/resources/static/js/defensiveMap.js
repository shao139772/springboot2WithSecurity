var mapUtil = {
    obj: {
        map: null
    },
    initMap: function (mapId) {
        // 百度地图API功能
        var mp = new BMap.Map(mapId); //创建地图实例
        mp.centerAndZoom(new BMap.Point(116.3964, 39.9093), 15);
        mp.setMapStyle({
            style: "midnight"
        });
        //地图初始化，设置中心点坐标和地图级别。地图必须经过初始化才可以执行其他操作
        //启用鼠标滚轮放大缩小
        mp.enableScrollWheelZoom();
        mapUtil.obj.map = mp;
    },
    addOverlays:function () {
        var mySiteOverlay = new SiteOverlay(new BMap.Point(116.407845, 39.914101));
        mapUtil.obj.map.addOverlay(mySiteOverlay); //将标注添加到地图中

        //5、 为自定义覆盖物添加点击事件
        mySiteOverlay.addEventListener('click', function () {
            alert("点击图标");
        });
    }
};

//1、定义构造函数并继承Overlay
//定义自定义覆盖物的构造函数  
function SiteOverlay(point) {
    this._point = point;
}
// 继承API的BMap.Overlay  
SiteOverlay.prototype = new BMap.Overlay();
//2、初始化自定义覆盖物
// 实现初始化方法  
SiteOverlay.prototype.initialize = function (map) {
    // 保存map对象实例 
    this._map = map;
    // 创建div元素，作为自定义覆盖物的容器  
    var div = this._div = document.createElement("div");
    div.style.position = "absolute";
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat); //聚合功能?
    // 可以根据参数设置元素外观
    div.style.height = "35px";
    div.style.width = "35px";

    var arrow = this._arrow = document.createElement("img");
    arrow.src = "http://www.yantiansf.cn/mapImage/1.gif";
    arrow.style.width = "35px";
    arrow.style.height = "35px";
    arrow.style.top = "22px";
    arrow.style.left = "10px";
    div.appendChild(arrow);

    // 将div添加到覆盖物容器中  
    mapUtil.obj.map.getPanes().labelPane.appendChild(div); //getPanes(),返回值:MapPane,返回地图覆盖物容器列表  labelPane呢???
    // 需要将div元素作为方法的返回值，当调用该覆盖物的show、  
    // hide方法，或者对覆盖物进行移除时，API都将操作此元素。
    return div;

}

//3、绘制覆盖物
// 实现绘制方法
SiteOverlay.prototype.draw = function () {
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
    this._div.style.top = pixel.y - 30 + "px";
}
//4、自定义覆盖物添加事件方法
SiteOverlay.prototype.addEventListener = function (event, fun) {
    this._div['on' + event] = fun;
}
