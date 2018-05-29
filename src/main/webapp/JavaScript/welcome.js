/**
 * Created by Suxuanhua on 2017/5/7.
 */

//about us
function shield(){
    window.scrollTo(0,0);//滚动到指定像素(返回顶部)
    var s = document.getElementById("about_us_bg");
    s.style.display = "block";

    var l = document.getElementById("about_us");
    l.style.display = "block";
}

function cancel_shield(){
    //原理：获取通过id/class 获取对象，然后在通过名对象调用具体方法
    //改成点击黑色背景就关闭
    var s = document.getElementById("about_us_bg");
    s.style.display = "none";

    var l = document.getElementById("about_us");
    l.style.display = "none";
}


//滚动图片
$(function(){
            var c = 0;
            function run(){
                c ++;
                c = (c == 5)?0:c;
//              siblings---找到兄弟元素
                $("#top_bcakground img").eq(c).fadeIn(500).siblings('img').fadeOut(500);/*两张图片淡入浅出时间速度*/
                /*默认滚动圆点色彩*/
                $("#top_bcakground ul li").eq(c).css({'background':'#ffffff'}).siblings('li').css({'background':'rgba(0, 0, 0, 0.5)'});
            }
            var timer = setInterval(run,5000);/*图片切换需要多少时间*/
            $("#top_bcakground ul li").mouseenter(function(){
            	var jqthis=$(this);
				//停止定时器
	           clearInterval(timer);
			   //设置定时
			gg=setTimeout(function(){

//              获得移入的li的序号
                c =jqthis.index();
                $("#top_bcakground img").eq(c).stop().fadeIn(300).siblings('img').stop().fadeOut(300);
                /*鼠标点击圆点色彩*/
                $("#top_bcakground ul li").eq(c).css({'background':'#AD3944'}).siblings('li').css({'background':'rgba(0, 0, 0, 0.5)'});
								 },500)

            })
			//鼠标移出
            $("#top_bcakground ul li").mouseleave(function(){
				//取消之前设置的定时
				clearTimeout(gg);
				//恢复定时器
                timer=setInterval(run,10000);
            })
        })