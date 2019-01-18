var UDEventsUtil = {
    dom: {
        UDEventsTBodyDom: null
    },
    data: {
        UDEventsData: []
    },
    processUDEvents: function () {
        UDEventsUtil.initUDEvents();
        UDEventsUtil.loadUDEvents();
        UDEventsUtil.rendUDEventsList();
        //UDEventsContainer =scroll("#UDEventsContainer");
    },
    initUDEvents: function () {
        UDEventsUtil.dom.UDEventsTBodyDom = document.querySelector("#UDEventsTBody");
    },
    loadUDEvents: function () {
        UDEventsUtil.data.UDEventsData = [{
            createdTime: "10:30",
            ttl: 200,
            name: "大疆",
            droneid: "v266373738",
            whiteList: true,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        }, {
            createdTime: "14:30",
            ttl: 200,
            name: "精灵",
            droneid: "v266373738",
            whiteList: false,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        },{
            createdTime: "10:30",
            ttl: 200,
            name: "大疆",
            droneid: "v266373738",
            whiteList: true,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        }, {
            createdTime: "14:30",
            ttl: 200,
            name: "精灵",
            droneid: "v266373738",
            whiteList: false,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        },{
            createdTime: "10:30",
            ttl: 200,
            name: "大疆",
            droneid: "v266373738",
            whiteList: true,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        }, {
            createdTime: "14:30",
            ttl: 200,
            name: "精灵",
            droneid: "v266373738",
            whiteList: false,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        },{
            createdTime: "10:30",
            ttl: 200,
            name: "大疆",
            droneid: "v266373738",
            whiteList: true,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        }, {
            createdTime: "14:30",
            ttl: 200,
            name: "精灵",
            droneid: "v266373738",
            whiteList: false,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        }, , {
            createdTime: "14:30",
            ttl: 200,
            name: "精灵",
            droneid: "v266373738",
            whiteList: false,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        },{
            createdTime: "10:30",
            ttl: 200,
            name: "大疆",
            droneid: "v266373738",
            whiteList: true,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        }, {
            createdTime: "14:30",
            ttl: 200,
            name: "精灵",
            droneid: "v266373738",
            whiteList: false,
            geo_location: {
                lng: "120.456",
                lat: "21.3345"
            },
            remark: "勘察"
        },];
    },
    rendUDEventsList: function () {
        UDEventsUtil.data.UDEventsData.forEach(function (rowData) {
            let tr = document.createElement("tr");
            let remarkIpt = document.createElement("input");
            tr.classList.add("trs");
            tr.innerHTML = "<td>" + rowData.createdTime + "</td><td>" + rowData.ttl + "s</td><td>" + rowData.name + "</td><td>" + rowData.droneid + "</td><td>北纬：" + rowData.geo_location.lat + " 东经:" + rowData.geo_location.lng + "</td><td>" + rowData.remark + "</td>";
            UDEventsUtil.dom.UDEventsTBodyDom.appendChild(tr);
            let remarkDom = UDEventsUtil.dom.UDEventsTBodyDom.lastElementChild.lastChild;

            let remarkContent = remarkDom.lastChild;
            let blurFunc = function () {
                remarkIpt.remove();
                remarkContent.data = remarkIpt.value;
                remarkDom.appendChild(remarkContent);
            };
            let dblclickFunc = function () {
                remarkIpt.value = remarkContent.data;
                remarkIpt.title = "焦点释放后完成";
                remarkContent.remove();
                remarkDom.appendChild(remarkIpt);
                remarkDom.lastElementChild.addEventListener("blur", blurFunc);
                document.addEventListener("click", blurFunc);
            };
            remarkDom.addEventListener("dblclick", dblclickFunc);
            remarkDom.title = "双击可修改备注";
        });
    },
};
var UDDronesUtil = {
    dom: {
        UDDronesDivDom: null
    },
    data: {
        UDDronesData: []
    },
    processUDDrones: function () {
        UDDronesUtil.initUDDrones();
        UDDronesUtil.loadUDDrones();
        UDDronesUtil.rendUDDrones();
        UDDronesDiv=scroll("#UDDronesDiv");
    },
    initUDDrones: function () {
        UDDronesUtil.dom.UDDronesDivDom = document.querySelector("#UDDronesDiv");
    },
    loadUDDrones: function () {
        UDDronesUtil.data.UDDronesData = [{
            attackTime: "14:05",
            name: "大疆",
            droneid: "v266373738",
            whiteList: true,
            attacked: true,
            rate: "2.4G"
        }, {
            attackTime: "14:05",
            name: "大疆",
            droneid: "v266373738",
            whiteList: true,
            attacked: false,
            rate: "2.4G"
        }, {
            attackTime: "14:05",
            name: "大疆",
            droneid: "v266373738",
            whiteList: false,
            attacked: false,
            rate: "2.4G"
        }];
    },
    rendUDDrones: function () {
        let rowHeaderUl=document.createElement("ul");
        rowHeaderUl.classList.add("rightName");
        rowHeaderUl.innerHTML=" <li>反制时间</li><li>机型</li><li>识别码</li><li>白名单</li><li>状态</li><li>频率</li><li>操作</li>";
        UDDronesUtil.dom.UDDronesDivDom.appendChild(rowHeaderUl);
        if (UDDronesUtil.data.UDDronesData.length > 0) {
            UDDronesUtil.data.UDDronesData.forEach(function (rowData) {
                let rowBoayUl = document.createElement("ul");
                let whiteList="否";
                if(rowData.whiteList){
                    whiteList="是";
                }
                let attacked="未反制";
                let attackedOper="";
                if(rowData.attacked){
                    attacked="已反制";
                    attackedOper="<a href=\"javascript:relieveAttack("+rowData.droneid+");\">解除反制</a>";
                }
                let className="rightNameY";
                if(!rowData.whiteList&&!rowData.attacked){
                    className="rightNameN";
                }
                let rowContent = "<li>" + rowData.attackTime + "</li><li>" + rowData.name + "</li><li>" + rowData.droneid + "</li><li>" + whiteList + "</li><li>" + attacked + "</li><li>" + rowData.rate + "</li><li>" + attackedOper + "</li>";
                rowBoayUl.innerHTML = rowContent;
                rowBoayUl.classList.add(className);
                UDDronesUtil.dom.UDDronesDivDom.appendChild(rowBoayUl);
            });
        }
    },
};