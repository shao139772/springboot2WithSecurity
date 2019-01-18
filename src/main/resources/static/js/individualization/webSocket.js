/* webSocket加载注册接受信息处理信息JS */
$(function() {
    initWs();

});

var ws;
function initWs() {
    ws = new WebSocket("ws://localhost:9322?name=" + sessionStorage.getItem('name'));
    ws.onmessage = function (event) {
        //document.getElementById("contentId").value += (event.data + "\r\n");
        show(event);
    };
    ws.onclose = function (event) {

    };
    ws.onopen = function (event) {
        ws.send("hello tio server");
    };
    ws.onerror = function (event) {

    };
}

function show(event){
    console.log(event);
    if(event.data != "收到text消息:hello tio server"){
        var  data = event.data;//获取传过来的数据
        /* 判断是否是JSON数据 */
        if (typeof data == 'string') {
            try {
                /* 整理JSON数据 */
                var obj = eval("(" + data + ")");
                if(typeof obj == 'object' && obj ){
                    var msg = obj.msg;//获取状态码
                    if(msg == "alarm drone"){//代表无人机列表刷新数据
                        var droneData = obj.data;//获取无人机列表数据
                        whiteListTable(droneData[0]);
                        radarChartPoint(1);
                    }
                }else{
                    alert(data);
                }
            } catch(e) {
                console.log(e);
                return false;
            }
        }



        /*var pop=new Pop("警告",
            "http://www.yanue.info/js/pop/pop.html",
            event.data);
        $('#popIntro').speech({
            "speech": true,
            "speed": 1,
            "bg": "./images/speech.png",
            "lang": "zh", //语言
            "content": event.data //直接播报内容
        });*/
    }
}