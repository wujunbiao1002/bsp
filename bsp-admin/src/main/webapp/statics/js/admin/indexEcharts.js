//ajax获取男女性别用户数数据
function read() {
    // 1.
    // sexEcharts.showLoading();    //数据加载完之前先显示一段简单的loading动画
    var sex = [];    //部门数组
    var num = [];  //部门工资总数

    $.ajax({
        type: 'get',
        url: '/echarts/sex.do',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            $.each(result.list, function (index, item) {
                sex.push(item.sex);    //挨个取出类别并填入类别数组
                num.push({
                    name: item.sex,
                    value: item.number,
                });
            });
            sexEcharts.hideLoading();    //隐藏加载动画
            sexEcharts.setOption({        //加载数据图表
                legend: {
                    data: sex
                },
                series: [
                    {
                        data: num
                    }
                ]
            });
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            sexEcharts.hideLoading();
        }
    });


// 2.
    $.ajax({
        type: 'get',
        url: '/echarts/checkEcharts.do',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            checkEcharts.hideLoading();    //隐藏加载动画
            checkEcharts.setOption({        //加载数据图表
                legend: {
                    data: ['待审核', '审核未通过', '审核通过']
                },
                series: [
                    {
                        // [{name:'待审核',value:10},{name:'审核未通过',value:13},{name:'通过审核',value:10}]
                        data: result
                    }
                ]
            });
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            checkEcharts.hideLoading();
        }
    });

//    3.
    $.ajax({
        type: 'get',
        url: '/echarts/sharingEcharts.do',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            sharingEcharts.hideLoading();    //隐藏加载动画
            sharingEcharts.setOption({        //加载数据图表
                legend: {
                    data: ['停止共享', '正在共享', '被下架']
                },
                series: [
                    {
                        data: result
                    }
                ]
            });
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            sharingEcharts.hideLoading();
        }
    });

    // 获取指定捐赠的书本数和完成借阅的本数
    $.ajax({
        type: 'get',
        url: '/echarts/sharingDonatedNum.do',//请求数据的地址
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            $("#sharingNum").html(result.sharing);
            $("#donatedNum").html(result.donated);
            // console.log(result.sharing)
        },
    });

}
