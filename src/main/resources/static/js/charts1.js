//饼图
var chart1 = echarts.init(document.getElementById('chart1'));

var xAxisData = [20,25,30,35,40,45,50];
var data1 = [];
var data2 = [];

var itemStyle = {
    normal: {
    },
    emphasis: {
        barBorderWidth: 1,
        shadowBlur: 10,
        shadowOffsetX: 0,
        shadowOffsetY: 0,
        shadowColor: 'rgba(0,0,0,0.5)'
    }
};


    option = {
        backgroundColor: '#eee',
        legend: {
            data: ['男性', '女性'],
            align: 'left',
            left: 10
        },
        xAxis: {
            data: xAxisData,
            name: '年龄',
            silent: false,
            axisLine: {onZero: true},
            splitLine: {show: false},
            splitArea: {show: false}
        },
        yAxis: {
            name: '个数',
            splitArea: {show: false}
        },

    };

chart1.setOption(option);

function getData(){
    $.ajax({
        url : "/charts/data/1",
        type : "POST",
        contentType:'application/json;charset=utf-8',
        dataType : "json",
        success : function(data) {
            chart1.setOption({
                    series : [
                        {
                            name: '男性',
                            type: 'bar',
                            itemStyle: itemStyle,
                            data: data.male,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'inside',
                                    formatter: '{c}'
                                }
                            }
                        },
                        {
                            name: '女性',
                            type: 'bar',
                            itemStyle: itemStyle,
                            data: data.female,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'inside'
                                }
                            }
                        }
                    ]
            });
        }
    });
}


