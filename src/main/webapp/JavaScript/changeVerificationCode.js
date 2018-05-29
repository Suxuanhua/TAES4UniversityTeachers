
//切换验证码
function _change() {
    //1.得到img元素
    //2.修改其src为/VerifyCodeServlet
    var imgEle = document.getElementById("vcode_img");
    //?a="+new Date().getTime() : 因为浏览器会把/VerifyCodeServlet 缓存下载，造成点击换一张图片没反应。
    //解决这个问题的办法就是给/VerifyCodeServlet 加可变化的参数，使imgEle.src 中的链接每一毫秒都不一样。
    //每次点击浏览器要处理的都是一条新的链接，都会去请求服务器，而服务器又不会处理该可变化参数（对服务器不会又影响）。
    //new Date().getTime() 是获取当前时间，毫秒形式。imgEle.src 中的链接地址，每一秒都是一条不同的链接。
    imgEle.src = "/TAES4University-Teachers/admin/getv_code.su?a=" + new Date().getTime();
}