
var E = window.wangEditor;
var editor = new E('#editor');
//关闭粘贴样式的过滤（当从其他网页复制文本内容粘贴到编辑器中，编辑器会默认过滤掉原有的样式。样式过滤对IE浏览器无效）
editor.customConfig.pasteFilterStyle = false;
// 自定义菜单配置
editor.customConfig.menus = [
    'head',  // 标题
    'bold',  // 粗体
    'italic',  // 斜体
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'foreColor',  // 文字颜色
    'backColor',  // 背景颜色
    'link',  // 插入链接
    'list',  // 列表
    'justify',  // 对齐方式
    'quote',  // 引用
    'table',  // 表格
    'code',  // 插入代码
    'undo',  // 撤销
    'redo'  // 重复
]

editor.create();

//传值到到 html 中一个隐藏的input，再通过表单提交到Servlet。
function getPostContent() {
    var postContent = editor.txt.html();
    var inputContent = document.getElementById("postContent");

    if(postContent.length<12){
        alert("内容太少了");

        // alert(postContent)
        return false;
    }else{
        inputContent.value = postContent;
        // alert(postContent)
        return true;
    }
}
function resetPostContent() {
    editor.txt.clear();
}