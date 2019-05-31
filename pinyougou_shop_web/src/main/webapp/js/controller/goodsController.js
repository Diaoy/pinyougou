//页面初始化完成后再创建Vue对象
//window.onload=function () {
//创建Vue对象
var app = new Vue({
    //接管id为app的区域
    el:"#app",
    data:{
        //声明数据列表变量，供v-for使用
        list:[],
        //总页数
        pages:1,
        //当前页
        pageNo:1,
        //声明对象
        entity:{goods:{},goodsDesc:{itemImages:[]}},
        //将要删除的id列表
        ids:[],
        //搜索包装对象
        searchEntity:{},
        //图片上传成功后保存的对象
        image_entity:{url:''}

    },
    methods:{
        //查询所有
        findAll:function () {
            axios.get("../goods/findAll.do").then(function (response) {
                //vue把数据列表包装在data属性中
                app.list = response.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        //分页查询
        findPage:function (pageNo) {
            axios.post("../goods/findPage.do?pageNo="+pageNo+"&pageSize="+10,this.searchEntity)
                .then(function (response) {
                    app.pages = response.data.pages;  //总页数
                    app.list = response.data.rows;  //数据列表
                    app.pageNo = pageNo;  //更新当前页
                });
        },
        //让分页插件跳转到指定页
        goPage:function (page) {
            app.$children[0].goPage(page);
        },
        //新增
        add:function () {
            //获取富文本内容
            this.entity.goodsDesc.introduction = editor.html();

            var url = "../goods/add.do";
            if(this.entity.goods.id != null){
                url = "../goods/update.do";
            }
            axios.post(url, this.entity).then(function (response) {
                //提示操作结果
                alert(response.data.message);
                if (response.data.success) {
                    //刷新数据，刷新当前页
                    app.entity = {goods:{},goodsDesc:{itemImages:[]}};
                    //清空富文本
                    editor.html("");
                }
            });
        },
        //跟据id查询
        getById:function (id) {
            axios.get("../goods/getById.do?id="+id).then(function (response) {
                app.entity = response.data;
            })
        },
        //批量删除数据
        dele:function () {
            axios.get("../goods/delete.do?ids="+this.ids).then(function (response) {
                if(response.data.success){
                    //刷新数据
                    app.findPage(app.pageNo);
                    //清空勾选的ids
                    app.ids = [];
                }else{
                    alert(response.data.message);
                }
            })
        },
        //文件上传
        upload:function(){
            //创建<form>表单对象
            var formData = new FormData() // 声明一个FormData对象
            // 'file' 这个名字要和后台获取文件的名字一样-表单的name属性
            //<input name="file" type="file" id="file"/>
            formData.append('file', document.querySelector('input[type=file]').files[0]);

            //post提交
            axios({
                url: '/upload.do',
                data: formData,
                method: 'post',
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function (response) {
                if(response.data.success){
                    //上传成功
                    app.image_entity.url=response.data.message;
                }else{
                    //上传失败
                    alert(response.data.message);
                }
            })
        },
        //把图片添加到图片数组中
        add_image_entity:function () {
            this.entity.goodsDesc.itemImages.push(this.image_entity);
        },
        //从图片数组中删除图片
        delete_image_entity:function (index) {
            this.entity.goodsDesc.itemImages.splice(index, 1);
        }


    },
    //Vue对象初始化后，调用此逻辑
    created:function () {
        //调用用分页查询，初始化时从第1页开始查询
        //this.findPage(1);
    }
});
//}
