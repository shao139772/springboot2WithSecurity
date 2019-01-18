//初始化
$(function() {
    whiteListTable(null);//加载已识别无人机表格
    eventTable();//加载无人机时间表格
    powerDiagram();//执行功率图图表内容
    proportionDiagram();//执行无人机比例图图表内容
    probabilityGraph();//散点概率图图表内容
    distributionDiagram();//柱形分布图图表内容
    isAutomaticAttack();//无人机自动攻击开关监听
    addOrDeleteWhiteList();//添加或删除无人机白名单
});

function whiteListTable(str){
    layui.use('table', function(){
        var table = layui.table;
        //表格数据加载 - 白名单列表
        table.render({
            elem: '#whiteListTable'
            ,cols: [[ //标题栏
                {field: 'name', title: '机型', width: 120,color:'red'}
                ,{field: 'id', title: '识别码', minWidth: 100}
                ,{field: 'whitelisted', title: '白名单', minWidth: 100, templet: function(d){
                        if(d.whitelisted == true){//是白名单
                            req = "<li class=\"navigationThree layui-form\">\n" +
                                "                            <input type=\"checkbox\" name=\"close\" lay-skin=\"switch\" lay-text=\"ON|OFF\" lay-filter=\"addOrDeleteWhiteList\" id=\"addOrDeleteWhiteList\" checked >\n" +
                                "                        </li>"
                        }else{
                            req = "<li class=\"navigationThree layui-form\">\n" +
                                "                            <input type=\"checkbox\" name=\"close\" lay-skin=\"switch\" lay-text=\"ON|OFF\" lay-filter=\"addOrDeleteWhiteList\" id=\"addOrDeleteWhiteList\" >\n" +
                                "                        </li>"
                        }
                        return req;
                    }}
                ,{field: 'operation', title: '操作', width: 80, toolbar: '#barDemo'}
            ]]
           // ,data: [str]
            ,data: []
            /*,data: [{"attacking":false,"can_attack":true,"can_takeover":"false","createdTime":"2018-11-08T10:02:12.168291768+08:00","deletedTime":"0001-01-01T00:00:00Z","directions":[{"direction":0,"group":"At"}],"id":"483e241f","image":"images/dji_ji_p3.jpg","name":"DJI Phantom3","peer_directions":[{"direction":0,"group":"At"}],"state":"danger","whitelisted":false}]
            */,even: true
        });

        //监听工具条
        table.on('tool(whiteListTableEvent)', function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if (layEvent === 'strike') { //打击
                strike(data);
            }
        });
    });
}

function eventTable(){
    layui.use('table', function(){
        var table = layui.table;
        //表格数据加载 - 事件
        table.render({
            elem: '#eventTable'
            ,url:'/drone/lzDrone/lzDroneList'
            ,method:'post'
            ,response: {
                statusName: 'status' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,msgName: 'msg' //状态信息的字段名称，默认：msg
                ,countName: 'totalRow' //数据总数的字段名称，默认：count
                ,dataName: 'data' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {field: 'id', title: '识别码', width: 80, sort: true}
                ,{field: 'name', title: '机型', width: 120}
                ,{field: 'createdTime', title: '发现时间', minWidth: 150, templet: function(d){
                        return  (new Date(d.createdTime)).Format("yyyy-MM-dd hh:mm:ss.S")
                    }}
                ,{field: 'state', title: '状态', minWidth: 100}
                ,{field: 'whitelisted', title: '是否在白名单中', width: 120}
            ]]
            ,even: true
            ,page: true //是否显示分页
            ,limits: [5, 7, 10]
            ,limit: 2 //每页默认显示的数量
        });

        //表格数据加载 - 白名单
        table.render({
            elem: '#whiteTable'
            ,url:'/drone/lzWhitelist/lzWhiteList'
            ,method:'post'
            ,response: {
                statusName: 'status' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,msgName: 'msg' //状态信息的字段名称，默认：msg
                ,countName: 'totalRow' //数据总数的字段名称，默认：count
                ,dataName: 'data' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {field: 'id', title: 'ID', width: 80, sort: true}
                ,{field: 'dronetype', title: '机型', width: 120}
                ,{field: 'droneId', title: '识别码', minWidth: 150}
                ,{field: 'operation', title: '操作', width: 80, toolbar: '#barDemo'}
            ]]
            ,even: true
            ,page: true //是否显示分页
            ,limits: [5, 7, 10]
            ,limit: 3 //每页默认显示的数量
        });
        height : 650
    });
}


/* 添加白名单 */
function addDroneWhite(){
    var droneId = $("#droneId").val();//ID
    var droneType = $("#droneType").val();//类型
    $.ajax({
        url : '/drone/lzWhitelist/addLzWhite',
        type : 'post',
        data:{droneId : droneId, droneType : droneType, isWhite : 'true'},
        success : function(data) {
            if (data == true){
                window.location.reload();
            } else{
                alert(data);
            }
        }
    });
}

//时间格式化
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/** 图表相关JS */
//功率图
function powerDiagram(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('powerDiagram'));

    var app = {};

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '功率',
            backgroundColor: '#46a5fe',
            borderRadius: 18,
            textStyle: {
                color: '#ffffff'
            },
            top:10
        },
        backgroundColor: "#19355c",
        color: ["#37A2DA", "#32C5E9", "#67E0E3"],
        series: [{
            name: '业务指标',
            type: 'gauge',
            detail: {
                formatter: '{value}%'
            },
            axisLine: {
                show: true,
                lineStyle: {
                    width: 15,
                    shadowBlur: 0,
                    color: [
                        [0.2, '#67e0e3'],
                        [0.8, '#37a2da'],
                        [1, '#fd666d']
                    ]
                },
            },
            axisLabel: {
                formatter: function(e) {
                    switch (e + "") {
                        case "10":
                            return "低";
                        case "50":
                            return "中";
                        case "90":
                            return "高";
                        default:
                            return "";
                    }
                },
                textStyle: {
                    fontSize: 15,
                    fontWeight: ""
                }
            },
            detail: {
                formatter: '{value}',
                textStyle: {
                    fontSize: 20,
                    fontWeight: ""
                }
            },
            data: [{
                value: 50
            }]

        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    //myChart.setOption(option);
    app.timeTicket = setInterval(function() {
        option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
        myChart.setOption(option, true);
    }, 2000);

}

//无人机比例图
function proportionDiagram(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('proportionDiagram'));

    var app = {};

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '无人机比例图',
            backgroundColor: '#46a5fe',
            borderRadius: 18,
            textStyle: {
                color: '#ffffff'
            }
        },
        backgroundColor: "#19355c",
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'right',
            textStyle:{
                color:'#fff',
                width:'2',
                fontSize:15
            },
            data: ['大疆精灵', '忍者', '神来', '路碳', '小红', '小兰']
        },
        series: [{
            name: '黑白名单情况',
            type: 'pie',
            selectedMode: 'single',
            radius: [0, '33%'],
            itemStyle: {
                normal: {
                    color: function(params) {
                        var colorList = [
                            '#ffd517', '#459af7'
                        ];
                        return colorList[params.dataIndex]
                    },
                    shadowBlur: 20,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            label: {
                normal: {
                    position: 'inner'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [{
                value: 335,
                name: '黑名单',
                selected: true
            },
                {
                    value: 679,
                    name: '白名单'
                }
            ]
        },
            {
                name: '黑白名单情况',
                type: 'pie',
                radius: ['44%', '55%'],
                itemStyle: {
                    normal: {
                        color: function(params) {
                            // build a color map as your need.
                            var colorList = [
                                '#4adcc5', '#ff864d', '#b4a3ea', '#ffe001', '#30c397', '#12bcfa'
                            ];
                            return colorList[params.dataIndex]
                        },
                        shadowBlur: 20,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    normal: {
                        // build a color map as your need.
                        //暂时不显示
                        show: false,
                        formatter: '{b|{b}：}{c}{per|{d}%}  ',
                        backgroundColor: '#eee',
                        rich: {
                            a: {
                                color: '#fff',
                                lineHeight: 22,
                                align: 'center'
                            },
                            b: {
                                fontSize: 12,
                                lineHeight: 33
                            },
                            per: {
                                color: '#eee',
                                backgroundColor: '#334455',
                                padding: [2, 4],
                                borderRadius: 2
                            }
                        }
                    }
                },
                data: [{
                    value: 300,
                    name: '大疆精灵'
                },
                    {
                        value: 35,
                        name: '忍者'
                    },
                    {
                        value: 300,
                        name: '神来'
                    },
                    {
                        value: 100,
                        name: '路碳'
                    },
                    {
                        value: 170,
                        name: '小红'
                    },
                    {
                        value: 109,
                        name: '小兰'
                    }
                ]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    /*app.timeTicket = setInterval(function() {
        option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
        myChart.setOption(option, true);
    }, 2000);*/

}

//散点概率图
function probabilityGraph(){
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('probabilityGraph'));

    var app = {};

    // 指定图表的配置项和数据
    var option = {
        title : {
            text: '散点概率分布图',
            backgroundColor: '#46a5fe'
        },
        grid: {
            left: '20',
            right: '60',
            bottom: '5',
            containLabel: true
        },
        backgroundColor: "#19355c",
        tooltip : {
            trigger: 'item',
            showDelay : 0,
            formatter : function (params) {
                if (params.value.length > 1) {
                    return params.seriesName + ' :<br/>'
                        + params.value[0] + 'cm '
                        + params.value[1] + 'kg ';
                }
                else {
                    return params.seriesName + ' :<br/>'
                        + params.name + ' : '
                        + params.value + 'kg ';
                }
            },
            axisPointer:{
                show: true,
                type : 'cross',
                lineStyle: {
                    type : 'dashed',
                    width : 1
                }
            }
        },
        legend: {
            data: ['黑名单','白名单'],
            textStyle:{
                color:'#fff',
                width:'2',
                fontSize:15

            },
            left: 'center'
        },
        xAxis : [
            {
                type : 'value',
                scale:true,
                axisLabel : {
                    formatter: '{value}',
                    textStyle: {
                        color: '#fff'
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color:'#fff',
                        width:'2'
                    }
                },
                name: '时\n（hour）'
            }
        ],
        yAxis : [
            {
                type : 'value',
                scale:true,
                axisLabel : {
                    formatter: '{value}',
                    textStyle: {
                        color: '#fff'
                    }
                },
                splitLine: {
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                axisLine: {
                    lineStyle: {
                        type: 'solid',
                        color:'#fff',
                        width:'2'
                    }
                },

                name: '分（min）'
            }
        ],
        series : [
            {
                name:'黑名单',
                type:'scatter',
                data: [[161.2, 51.6], [167.5, 59.0], [159.5, 49.2], [157.0, 63.0], [155.8, 53.6],
                    [170.0, 59.0], [159.1, 47.6], [166.0, 69.8], [176.2, 66.8], [160.2, 75.2],
                    [172.5, 55.2], [170.9, 54.2], [172.9, 62.5], [153.4, 42.0], [160.0, 50.0],
                    [147.2, 49.8], [168.2, 49.2], [175.0, 73.2], [157.0, 47.8], [167.6, 68.8],
                    [159.5, 50.6], [175.0, 82.5], [166.8, 57.2], [176.5, 87.8], [170.2, 72.8],
                    [174.0, 54.5], [173.0, 59.8], [179.9, 67.3], [170.5, 67.8], [160.0, 47.0],
                    [154.4, 46.2], [162.0, 55.0], [176.5, 83.0], [160.0, 54.4], [152.0, 45.8],
                    [162.1, 53.6], [170.0, 73.2], [160.2, 52.1], [161.3, 67.9], [166.4, 56.6],
                    [168.9, 62.3], [163.8, 58.5], [167.6, 54.5], [160.0, 50.2], [161.3, 60.3],
                    [167.6, 58.3], [165.1, 56.2], [160.0, 50.2], [170.0, 72.9], [157.5, 59.8],
                    [167.6, 61.0], [160.7, 69.1], [163.2, 55.9], [152.4, 46.5], [157.5, 54.3],
                    [168.3, 54.8], [180.3, 60.7], [165.5, 60.0], [165.0, 62.0], [164.5, 60.3],
                    [156.0, 52.7], [160.0, 74.3], [163.0, 62.0], [165.7, 73.1], [161.0, 80.0],
                    [162.0, 54.7], [166.0, 53.2], [174.0, 75.7], [172.7, 61.1], [167.6, 55.7],
                    [151.1, 48.7], [164.5, 52.3], [163.5, 50.0], [152.0, 59.3], [169.0, 62.5],
                    [164.0, 55.7], [161.2, 54.8], [155.0, 45.9], [170.0, 70.6], [176.2, 67.2],
                    [170.0, 69.4], [162.5, 58.2], [170.3, 64.8], [164.1, 71.6], [169.5, 52.8],
                    [163.2, 59.8], [154.5, 49.0], [159.8, 50.0], [173.2, 69.2], [170.0, 55.9],
                    [161.4, 63.4], [169.0, 58.2], [166.2, 58.6], [159.4, 45.7], [162.5, 52.2],
                    [159.0, 48.6], [162.8, 57.8], [159.0, 55.6], [179.8, 66.8], [162.9, 59.4],
                    [161.0, 53.6], [151.1, 73.2], [168.2, 53.4], [168.9, 69.0], [173.2, 58.4],
                    [171.8, 56.2], [178.0, 70.6], [164.3, 59.8], [163.0, 72.0], [168.5, 65.2],
                    [166.8, 56.6], [172.7, 105.2], [163.5, 51.8], [169.4, 63.4], [167.8, 59.0],
                    [159.5, 47.6], [167.6, 63.0], [161.2, 55.2], [160.0, 45.0], [163.2, 54.0],
                    [162.2, 50.2], [161.3, 60.2], [149.5, 44.8], [157.5, 58.8], [163.2, 56.4],
                    [172.7, 62.0], [155.0, 49.2], [156.5, 67.2], [164.0, 53.8], [160.9, 54.4],
                    [162.8, 58.0], [167.0, 59.8], [160.0, 54.8], [160.0, 43.2], [168.9, 60.5],
                    [158.2, 46.4], [156.0, 64.4], [160.0, 48.8], [167.1, 62.2], [158.0, 55.5],
                    [167.6, 57.8], [156.0, 54.6], [162.1, 59.2], [173.4, 52.7], [159.8, 53.2],
                    [170.5, 64.5], [159.2, 51.8], [157.5, 56.0], [161.3, 63.6], [162.6, 63.2],
                    [160.0, 59.5], [168.9, 56.8], [165.1, 64.1], [162.6, 50.0], [165.1, 72.3],
                    [166.4, 55.0], [160.0, 55.9], [152.4, 60.4], [170.2, 69.1], [162.6, 84.5],
                    [170.2, 55.9], [158.8, 55.5], [172.7, 69.5], [167.6, 76.4], [162.6, 61.4],
                    [167.6, 65.9], [156.2, 58.6], [175.2, 66.8], [172.1, 56.6], [162.6, 58.6],
                    [160.0, 55.9], [165.1, 59.1], [182.9, 81.8], [166.4, 70.7], [165.1, 56.8],
                    [177.8, 60.0], [165.1, 58.2], [175.3, 72.7], [154.9, 54.1], [158.8, 49.1],
                    [172.7, 75.9], [168.9, 55.0], [161.3, 57.3], [167.6, 55.0], [165.1, 65.5],
                    [175.3, 65.5], [157.5, 48.6], [163.8, 58.6], [167.6, 63.6], [165.1, 55.2],
                    [165.1, 62.7], [168.9, 56.6], [162.6, 53.9], [164.5, 63.2], [176.5, 73.6],
                    [168.9, 62.0], [175.3, 63.6], [159.4, 53.2], [160.0, 53.4], [170.2, 55.0],
                    [162.6, 70.5], [167.6, 54.5], [162.6, 54.5], [160.7, 55.9], [160.0, 59.0],
                    [157.5, 63.6], [162.6, 54.5], [152.4, 47.3], [170.2, 67.7], [165.1, 80.9],
                    [172.7, 70.5], [165.1, 60.9], [170.2, 63.6], [170.2, 54.5], [170.2, 59.1],
                    [161.3, 70.5], [167.6, 52.7], [167.6, 62.7], [165.1, 86.3], [162.6, 66.4],
                    [152.4, 67.3], [168.9, 63.0], [170.2, 73.6], [175.2, 62.3], [175.2, 57.7],
                    [160.0, 55.4], [165.1, 104.1], [174.0, 55.5], [170.2, 77.3], [160.0, 80.5],
                    [167.6, 64.5], [167.6, 72.3], [167.6, 61.4], [154.9, 58.2], [162.6, 81.8],
                    [175.3, 63.6], [171.4, 53.4], [157.5, 54.5], [165.1, 53.6], [160.0, 60.0],
                    [174.0, 73.6], [162.6, 61.4], [174.0, 55.5], [162.6, 63.6], [161.3, 60.9],
                    [156.2, 60.0], [149.9, 46.8], [169.5, 57.3], [160.0, 64.1], [175.3, 63.6],
                    [169.5, 67.3], [160.0, 75.5], [172.7, 68.2], [162.6, 61.4], [157.5, 76.8],
                    [176.5, 71.8], [164.4, 55.5], [160.7, 48.6], [174.0, 66.4], [163.8, 67.3]
                ],
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'白名单',
                type:'scatter',
                data: [[174.0, 65.6], [175.3, 71.8], [193.5, 80.7], [186.5, 72.6], [187.2, 78.8],
                    [181.5, 74.8], [184.0, 86.4], [184.5, 78.4], [175.0, 62.0], [184.0, 81.6],
                    [180.0, 76.6], [177.8, 83.6], [192.0, 90.0], [176.0, 74.6], [174.0, 71.0],
                    [184.0, 79.6], [192.7, 93.8], [171.5, 70.0], [173.0, 72.4], [176.0, 85.9],
                    [176.0, 78.8], [180.5, 77.8], [172.7, 66.2], [176.0, 86.4], [173.5, 81.8],
                    [178.0, 89.6], [180.3, 82.8], [180.3, 76.4], [164.5, 63.2], [173.0, 60.9],
                    [183.5, 74.8], [175.5, 70.0], [188.0, 72.4], [189.2, 84.1], [172.8, 69.1],
                    [170.0, 59.5], [182.0, 67.2], [170.0, 61.3], [177.8, 68.6], [184.2, 80.1],
                    [186.7, 87.8], [171.4, 84.7], [172.7, 73.4], [175.3, 72.1], [180.3, 82.6],
                    [182.9, 88.7], [188.0, 84.1], [177.2, 94.1], [172.1, 74.9], [167.0, 59.1],
                    [169.5, 75.6], [174.0, 86.2], [172.7, 75.3], [182.2, 87.1], [164.1, 55.2],
                    [163.0, 57.0], [171.5, 61.4], [184.2, 76.8], [174.0, 86.8], [174.0, 72.2],
                    [177.0, 71.6], [186.0, 84.8], [167.0, 68.2], [171.8, 66.1], [182.0, 72.0],
                    [167.0, 64.6], [177.8, 74.8], [164.5, 70.0], [192.0, 101.6], [175.5, 63.2],
                    [171.2, 79.1], [181.6, 78.9], [167.4, 67.7], [181.1, 66.0], [177.0, 68.2],
                    [174.5, 63.9], [177.5, 72.0], [170.5, 56.8], [182.4, 74.5], [197.1, 90.9],
                    [180.1, 93.0], [175.5, 80.9], [180.6, 72.7], [184.4, 68.0], [175.5, 70.9],
                    [180.6, 72.5], [177.0, 72.5], [177.1, 83.4], [181.6, 75.5], [176.5, 73.0],
                    [175.0, 70.2], [174.0, 73.4], [165.1, 70.5], [177.0, 68.9], [192.0, 102.3],
                    [176.5, 68.4], [169.4, 65.9], [182.1, 75.7], [179.8, 84.5], [175.3, 87.7],
                    [184.9, 86.4], [177.3, 73.2], [167.4, 53.9], [178.1, 72.0], [168.9, 55.5],
                    [157.2, 58.4], [180.3, 83.2], [170.2, 72.7], [177.8, 64.1], [172.7, 72.3],
                    [165.1, 65.0], [186.7, 86.4], [165.1, 65.0], [174.0, 88.6], [175.3, 84.1],
                    [185.4, 66.8], [177.8, 75.5], [180.3, 93.2], [180.3, 82.7], [177.8, 58.0],
                    [177.8, 79.5], [177.8, 78.6], [177.8, 71.8], [177.8, 116.4], [163.8, 72.2],
                    [188.0, 83.6], [198.1, 85.5], [175.3, 90.9], [166.4, 85.9], [190.5, 89.1],
                    [166.4, 75.0], [177.8, 77.7], [179.7, 86.4], [172.7, 90.9], [190.5, 73.6],
                    [185.4, 76.4], [168.9, 69.1], [167.6, 84.5], [175.3, 64.5], [170.2, 69.1],
                    [190.5, 108.6], [177.8, 86.4], [190.5, 80.9], [177.8, 87.7], [184.2, 94.5],
                    [176.5, 80.2], [177.8, 72.0], [180.3, 71.4], [171.4, 72.7], [172.7, 84.1],
                    [172.7, 76.8], [177.8, 63.6], [177.8, 80.9], [182.9, 80.9], [170.2, 85.5],
                    [167.6, 68.6], [175.3, 67.7], [165.1, 66.4], [185.4, 102.3], [181.6, 70.5],
                    [172.7, 95.9], [190.5, 84.1], [179.1, 87.3], [175.3, 71.8], [170.2, 65.9],
                    [193.0, 95.9], [171.4, 91.4], [177.8, 81.8], [177.8, 96.8], [167.6, 69.1],
                    [167.6, 82.7], [180.3, 75.5], [182.9, 79.5], [176.5, 73.6], [186.7, 91.8],
                    [188.0, 84.1], [188.0, 85.9], [177.8, 81.8], [174.0, 82.5], [177.8, 80.5],
                    [171.4, 70.0], [185.4, 81.8], [185.4, 84.1], [188.0, 90.5], [188.0, 91.4],
                    [182.9, 89.1], [176.5, 85.0], [175.3, 69.1], [175.3, 73.6], [188.0, 80.5],
                    [188.0, 82.7], [175.3, 86.4], [170.5, 67.7], [179.1, 92.7], [177.8, 93.6],
                    [175.3, 70.9], [182.9, 75.0], [170.8, 93.2], [188.0, 93.2], [180.3, 77.7],
                    [177.8, 61.4], [185.4, 94.1], [168.9, 75.0], [185.4, 83.6], [180.3, 85.5],
                    [174.0, 73.9], [167.6, 66.8], [182.9, 87.3], [160.0, 72.3], [180.3, 88.6],
                    [167.6, 75.5], [186.7, 101.4], [175.3, 91.1], [175.3, 67.3], [175.9, 77.7],
                    [175.3, 81.8], [179.1, 75.5], [181.6, 84.5], [177.8, 76.6], [182.9, 85.0],
                    [177.8, 102.5], [184.2, 77.3], [179.1, 71.8], [176.5, 87.9], [188.0, 94.3],
                    [174.0, 70.9], [167.6, 64.5], [170.2, 77.3], [167.6, 72.3], [188.0, 87.3],
                    [174.0, 80.0], [176.5, 82.3], [180.3, 73.6], [167.6, 74.1], [188.0, 85.9],
                    [180.3, 73.2], [167.6, 76.3], [183.0, 65.9], [183.0, 90.9], [179.1, 89.1],
                    [170.2, 62.3], [177.8, 82.7], [179.1, 79.1], [190.5, 98.2], [177.8, 84.1],
                    [180.3, 83.2], [180.3, 83.2]
                ],
                markPoint : {
                    data : [
                        {type : 'max', name: '最大值'},
                        {type : 'min', name: '最小值'}
                    ]
                },
                markLine : {
                    data : [
                        {type : 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    /*app.timeTicket = setInterval(function() {
        option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
        myChart.setOption(option, true);
    }, 2000);*/
}

//柱形分布图
function distributionDiagram() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('distributionDiagram'));

    var app = {};

    // 指定图表的配置项和数据
    var option = {
        title: {
            backgroundColor: '#46a5fe',
            text: '柱形分布图'
        },
        backgroundColor: "#19355c",
        color: ['#87cae4'],
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            },
            color:'red',
        },
        grid: {
            left: '25',
            right: '65',
            bottom: '5',

            containLabel: true
        },
        xAxis: [{
            type: 'category',
            data: ['10', '20', '50', '100', '300', '500', '1000'],
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#fff',//左边线的颜色
                    width:'2'//坐标线的宽度
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#fff',//坐标值得具体的颜色

                }
            },
            axisTick: {
                alignWithLabel: true
            },
            name: '发现时间\n（hour）'
        }],
        yAxis: [{
            type: 'value',
            name: '持续时间（min）',
            axisLine: {
                lineStyle: {
                    type: 'solid',
                    color: '#fff',//左边线的颜色
                    width:'2'//坐标线的宽度
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#fff',//坐标值得具体的颜色

                }
            }
        }],
        series: [{
            name: '直接访问',
            type: 'bar',
            barWidth: '60%',
            data: [10, 20, 30, 40, 50, 60, 70]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    /*app.timeTicket = setInterval(function() {
        option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
        myChart.setOption(option, true);
    }, 2000);*/
}

/* 无人机操作 */
//自动打击开启关闭
function isAutomaticAttack(){
    layui.use('form', function(){
        var form = layui.form
            ,layer =layui.layer;
        //监听自动攻击开关
        form.on('switch(isAutomaticAttack)', function(data){
            var index_sms;
            var autoAttack =data.elem.checked;
            $.ajax({
                url : '/lz/isAutomaticAttack',
                type : 'post',
                data:{autoAttack : autoAttack},
                success : function(data) {
                    console.log(data);
                    /*if(data.code == 0){//登录成功，跳转至首页
                        sessionStorage.setItem('name', userLoginName);
                        window.location.href = "/sys/login/index";
                    }else{//登录失败，输出失败原因
                        alert(data.msg);
                    }*/
                }
            });
        });
    });
}

//打击无人机事件
function strike(row){
    var  droneid = row.id;
    $.ajax({
        url : '/lz/combatUAV',
        type : 'post',
        data:{droneid : droneid},
        success : function(data) {
            console.log(data);
            /*if(data.code == 0){//登录成功，跳转至首页
                sessionStorage.setItem('name', userLoginName);
                window.location.href = "/sys/login/index";
            }else{//登录失败，输出失败原因
                alert(data.msg);
            }*/
        }
    });
}

//添加或删除白名单
function addOrDeleteWhiteList(){
    layui.use('form', function(){
        var form = layui.form
            ,layer =layui.layer;
        //监听自动攻击开关
        form.on('switch(addOrDeleteWhiteList)', function(obj){
            //当前元素
            var data = $(obj.elem);
            //遍历父级tr，取第一个，然后查找第二个td，取值
            var droneType = data.parents('tr').first().find('td').eq(0).text();//获取type值
            var droneId = data.parents('tr').first().find('td').eq(1).text();//获取ID
            var isWhite =obj.elem.checked;//当前状态：是否进入白名单
            $.ajax({
                url : '/drone/lzWhitelist/addLzWhite',
                type : 'post',
                data:{droneId : droneId, droneType : droneType, isWhite : isWhite},
                success : function(data) {
                    console.log(data);
                    /*if(data.code == 0){//登录成功，跳转至首页
                        sessionStorage.setItem('name', userLoginName);
                        window.location.href = "/sys/login/index";
                    }else{//登录失败，输出失败原因
                        alert(data.msg);
                    }*/
                }
            });
        });
    });
}