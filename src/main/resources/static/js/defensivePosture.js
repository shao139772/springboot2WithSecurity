 var UDEventsUtil = {
     dom: {
         UDEventsTodayDom: null,
         UDEventsHistoryDom: null
     },
     data: {
         UDEventsTodayData: [],
         UDEventsHistoryData: []
     },
     processUDEvents: function () {
         UDEventsUtil.initUDEvents();
         UDEventsUtil.loadUDEvents();
         UDEventsUtil.rendUDEventsList();
     },
     initUDEvents: function () {
         //初始化dom/data
         UDEventsUtil.dom.UDEventsTodayDom = document.querySelector("#UDEventsToday");
         UDEventsUtil.dom.UDEventsHistoryDom = document.querySelector("#UDEventsHistory");
     },
     loadUDEvents: function () {
         UDEventsUtil.loadTodayUDEvents();
         UDEventsUtil.loadHostoryUDEvents();
     },
     loadTodayUDEvents: function () {
         //加载JSON
         let UDEventsTodayJson = {};
         UDEventsUtil.data.UDEventsToadyData = [{
             createdTime: "14:30",
             ttl: 200,
             name: "精灵",
             droneid: "v266373738",
             whiteList: true
         }, {
             createdTime: "14:30",
             ttl: 200,
             name: "精灵",
             droneid: "v266373738",
             whiteList: false
         }, ];
     },
     loadHostoryUDEvents: function () {
         //加载JSON
         let UDEventsHistoryJson = {};
         UDEventsUtil.data.UDEventsHistoryData = [{
             createdTime: "11:30",
             ttl: 200,
             name: "精灵",
             droneid: "v266373738",
             whiteList: true
         }, {
             createdTime: "11:30",
             ttl: 200,
             name: "精灵",
             droneid: "v266373738",
             whiteList: false
         }, ];
     },
     rendUDEventsList: function () {
         UDEventsUtil.rendTodayUDEventsList();
         UDEventsUtil.rendHistoryUDEventsList();
     },
     rendTodayUDEventsList: function () {
         let rowHeaderUl = document.createElement("ul");
         rowHeaderUl.classList.add("mltTou");
         rowHeaderUl.innerHTML = "<li>发生时间</li><li>持续时间</li><li>机型</li><li>识别码</li>";
         UDEventsUtil.dom.UDEventsTodayDom.appendChild(rowHeaderUl);
         if (UDEventsUtil.data.UDEventsToadyData.length > 0) {
             UDEventsUtil.data.UDEventsToadyData.forEach(function (rowData) {
                 let rowBoayUl = document.createElement("ul");
                 let rowContent = "<li>" + rowData.createdTime + "</li><li>" + rowData.ttl + "s</li><li>" + rowData.name + "</li><li>" + rowData.droneid + "</li>";
                 rowBoayUl.innerHTML = rowContent;
                 if (rowData.whiteList) {
                     rowBoayUl.classList.add("safe");
                 } else {
                     rowBoayUl.classList.add("danger");
                 }
                 UDEventsUtil.dom.UDEventsTodayDom.appendChild(rowBoayUl);
             });
         }
     },
     rendHistoryUDEventsList: function () {
         let rowHeaderUl = document.createElement("ul");
         rowHeaderUl.classList.add("mlbTou");
         rowHeaderUl.innerHTML = "<li>发生时间</li><li>持续时间</li><li>机型</li><li>识别码</li>";
         UDEventsUtil.dom.UDEventsHistoryDom.appendChild(rowHeaderUl);
         if (UDEventsUtil.data.UDEventsToadyData.length > 0) {
             UDEventsUtil.data.UDEventsToadyData.forEach(function (rowData) {
                 let rowBoayUl = document.createElement("ul");
                 let rowContent = "<li>" + rowData.createdTime + "</li><li>" + rowData.ttl + "s</li><li>" + rowData.name + "</li><li>" + rowData.droneid + "</li>";
                 rowBoayUl.innerHTML = rowContent;
                 rowBoayUl.classList.add("mlbShen");
                 if (rowData.whiteList) {
                     rowBoayUl.classList.add("safe");
                 } else {
                     rowBoayUl.classList.add("danger");
                 }
                 UDEventsUtil.dom.UDEventsHistoryDom.appendChild(rowBoayUl);
             });
         }
     }
 };

 var UDSitesUtil = {
     dom: {
         UDSitesTbodyDom: null,
     },
     data: {
         UDSitesData: [],
     },
     processUDSites: function () {
         UDSitesUtil.initUDSites();
         UDSitesUtil.loadUDSites();
         UDSitesUtil.rendUDSites();
     },
     initUDSites: function () {
         UDSitesUtil.dom.UDSitesTbodyDom = document.querySelector("#UDSitesTBodyDiv");
     },
     loadUDSites: function () {
         UDSitesUtil.data.UDSitesData = [{
             siteName: "朝阳区站点",
             equipmentCode: "239116",
             engine_ip: "192.168.1.2",
             ttl: 1
         }, {
             siteName: "海淀区站点",
             equipmentCode: "239116",
             engine_ip: "192.168.1.2",
             ttl: 2
         }, {
             siteName: "丰台区站点",
             equipmentCode: "239116",
             engine_ip: "192.168.1.2",
             ttl: 0
         }];
     },
     rendUDSites: function () {
         if (UDSitesUtil.data.UDSitesData.length > 0) {
             UDSitesUtil.data.UDSitesData.forEach(function (rowData) {
                 let rowBoayUl = document.createElement("ul");
                 rowBoayUl.classList.add("mrName");
                 let loadLevelClass = "underLoad";
                 if (rowData.ttl == 0) {
                     loadLevelClass = "highLoad";
                 }
                 let rowContent = "<li>" + rowData.siteName + "</li><li>" + rowData.equipmentCode + "</li><li>" + rowData.engine_ip + "</li><li><div class=\"" + loadLevelClass + "\"></div></li>";
                 rowBoayUl.innerHTML = rowContent;
                 UDSitesUtil.dom.UDSitesTbodyDom.appendChild(rowBoayUl);
             });
         }
     }
 };
 var eventsDstrChartUtil = {
     dom: {
         eventsDstrChartDom: null
     },
     data: {
         eventsDstrChartData: []
     },
     processChart: function () {
         eventsDstrChartUtil.initChart();
         eventsDstrChartUtil.loadChart();
     },
     initChart: function () {
         eventsDstrChartUtil.dom.eventsDstrChartDom = document.querySelector("#footerLeft");
     },
     loadChart: function () {
         eventsDstrChartUtil.rendChart([7, 17, 19, 18, 16, 10, 4]);
     },
     rendChart: function (data) {
         var footerLeft = echarts.init(eventsDstrChartUtil.dom.eventsDstrChartDom);
         option = {
             // 标题
             title: {
                 text: '事件分布时间表',
                 textStyle: {
                     color: "#14D7DA"
                 }
             },
             //说明
             legend: {
                 textStyle: {
                     color: "#fff"
                 }
             },
             tooltip: {
                 formatter: '{b}发生事件{c}个'
             },
             xAxis: {
                 type: 'category',
                 name: '(时)',
                 data: ['0:00', '4:00', '8:00', '12:00', '16:00', '20:00', '24:00'],
                 axisLine: {
                     lineStyle: {
                         color: "#fff" //X轴字体颜色
                     }
                 }
             },
             yAxis: {
                 type: 'value',
                 name: '(个)',
                 axisLine: {
                     lineStyle: {
                         color: "#fff" //X轴字体颜色
                     }
                 }
             },
             series: [{
                 data: data,
                 type: 'bar',
                 color: "#87CAE4", //折现区域颜色
                 barCategoryGap: "60%"
             }]
         };
         footerLeft.setOption(option);
     },
 };

 var bandsDstrChartUtil = {
     dom: {
         bandsDstrChartDom: null
     },
     data: {
         bandsDstrChartData: []
     },
     processChart: function () {
         bandsDstrChartUtil.initChart();
         bandsDstrChartUtil.loadChart();
     },
     initChart: function () {
         bandsDstrChartUtil.dom.bandsDstrChartDom = document.querySelector("#footerCenter");
     },
     loadChart: function () {
         bandsDstrChartUtil.rendChart([{
                 value: 164,
                 name: '大疆占15%'
             },
             {
                 value: 230,
                 name: '精灵占30%'
             },
             {
                 value: 467,
                 name: '特洛占65%'
             }
         ]);
     },
     rendChart: function (data) {
         var footerCenter = echarts.init(bandsDstrChartUtil.dom.bandsDstrChartDom);
         option = {
             title: {
                 text: '入侵无人机品牌分布',
                 textStyle: {
                     color: "#15DADC"
                 }
             },
             tooltip: {},
             series: [{
                 type: 'pie',
                 radius: '55%',
                 center: ['50%', '60%'],
                 data: data,
                 itemStyle: {
                     normal: {
                         color: function (params) {
                             var colorList = ['#F8D584', '#AF88E3', '#8BD9AF', '#DE8FE2',
                                 '#87CAE4', '#C96C99',
                                 '#C1232B', '#B5C334', '#FCCE10', '#E87C25', '#27727B',
                                 '#FE8463', '#9BCA63', '#FAD860', '#F3A43B', '#60C0DD',
                                 '#D7504B', '#C6E579', '#F4E001', '#F0805A', '#26C0C0'
                             ];
                             return colorList[params.dataIndex];
                         }
                     },
                     emphasis: {
                         shadowBlur: 10,
                         shadowOffsetX: 0,
                         shadowColor: 'rgba(0, 0, 0, 0.5)'
                     }
                 }
             }]
         };
         footerCenter.setOption(option);
     }
 };
 var durationDstrChartUtil = {
     dom: {
         durationDstrChartDom: null
     },
     data: {
         durationDstrChartData: []
     },
     processChart: function () {
        durationDstrChartUtil.initChart();
        durationDstrChartUtil.loadChart();
     },
     initChart: function () {
         durationDstrChartUtil.dom.durationDstrChartDom = document.querySelector("#footerRight");
     },
     loadChart: function () {
        durationDstrChartUtil.rendChart([990, 900, 780, 565, 435, 236, 160]);
     },
     rendChart: function (data) {
         var footerRight = echarts.init(durationDstrChartUtil.dom.durationDstrChartDom);
         option = {
             // 标题
             title: {
                 text: '入侵时长分布表',
                 textStyle: {
                     color: "#14D7DA"
                 }
             },
             tooltip: {},
             //说明
             legend: {
                 textStyle: {
                     color: "#fff"
                 }
             },
             xAxis: {
                 type: 'category',
                 name: '(秒)',
                 data: ['0~20s', '20~40s', '40~60s', '60~80', '80~100s', '100~120s', '>120s'],
                 axisLine: {
                     lineStyle: {
                         color: "#fff" //X轴字体颜色
                     }
                 }
             },
             yAxis: {
                 type: 'value',
                 name: '(个)',
                 axisLine: {
                     lineStyle: {
                         color: "#fff" //X轴字体颜色
                     }
                 }
             },
             series: [{
                 data: data,
                 type: 'bar',
                 color: "#87CAE4", //折现区域颜色
                 barCategoryGap: "60%"
             }]
         };
         footerRight.setOption(option);
     }
 };
 /* //入侵时长分布表
 var footerRight = echarts.init(document.getElementById('footerRight'));
 option = {
     // 标题
     title: {
         text: '入侵时长分布表',
         textStyle: {
             color: "#14D7DA"
         }
     },
     //说明
     legend: {
         textStyle: {
             color: "#fff"
         }
     },
     xAxis: {
         type: 'category',
         name: '(秒)',
         data: ['0~20s', '20~40s', '40~60s', '60~80', '80~100s', '100~120s', '>120s'],
         axisLine: {
             lineStyle: {
                 color: "#fff" //X轴字体颜色
             }
         }
     },
     yAxis: {
         type: 'value',
         name: '(个)',
         axisLine: {
             lineStyle: {
                 color: "#fff" //X轴字体颜色
             }
         }
     },
     series: [{
         data: [990, 900, 780, 565, 435, 236, 160],
         type: 'bar',
         color: "#87CAE4", //折现区域颜色
         barCategoryGap: "60%"
     }]
 };
 footerRight.setOption(option); */