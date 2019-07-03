//折线图
var chart1 = echarts.init(document.getElementById('chart1'));

var xAxisData = ['20岁','25岁','30岁','35岁','40岁','45岁','50岁'];

option = {
    title: {
        text: '折线图堆叠'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['男性最低','男性最高','男性平均','女性最低','女性最高','女性平均']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%'
    },
    xAxis: {
        type: 'category',
        name: '年龄',
        boundaryGap: false,
        data: xAxisData
    },
    yAxis: {
        type: 'value',
        name: '金额'
    }
};

chart1.setOption(option);

function getData(){
    $.ajax({
        url : "/charts/data/2",
        type : "POST",
        contentType:'application/json;charset=utf-8',
        dataType : "json",
        success : function(data) {
            chart1.setOption({
                series: [
                    {
                        name:'男性最低',
                        type:'line',
                        data: data.malemin
                    },
                    {
                        name:'男性最高',
                        type:'line',
                        data:data.malemax
                    },
                    {
                        name:'男性平均',
                        type:'line',
                        data:data.maleavg
                    },
                    {
                        name:'女性最低',
                        type:'line',
                        data:data.femalemin
                    },
                    {
                        name:'女性最高',
                        type:'line',
                        data:data.femalemax
                    },
                    {
                        name:'女性平均',
                        type:'line',
                        data:data.femaleavg
                    }
                ]
            });
        }
    });
}


