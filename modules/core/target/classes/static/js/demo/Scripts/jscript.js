document.writeln("<div id='sel_pan'><table width='1180' align='center'> " +
    "<tr><td align='center'>" +
    "<div>" +
    "<div id='selDetailBtn'><span style='margin-top:8px; display:inline-block; font-weight:bold;'>已选<span id='selCount'>0</span>场</span><br/>(设胆)</div>" +
    "<div id='selDetailDiv'>" +
    "<table  id='selDetailTbl' cellpadding='0' cellspacing='0'>" +
    "</table>" +
    "</div>" +
    "</div>" +
    "</td>" +
    "<td style='padding-bottom:5px;width:631px;'>" +
    "<div style='padding: 5px 0 3px 0; color:#FFE38B; font-size:14px; font-weight:bold;'>本页面目前仅提供奖金计算功能，更多便捷服务将在将来开通。</div>"+
    "<div id='optionHeader'><b>过关方式：</b><input type='radio' index='0' name='optionType' checked />M串N<input index='1' type='radio' name='optionType' />自由过关<label id='optionTip'></label></div>" +
    "<div><span id='optionList' count='0'></span></div>" +
    "</td>" +
    "<td>" +
    "<input type='button' id='subBtn' class='subAddBtn' value='-'><input id='times' type='text' value='1' /><input type='button' id='addBtn' class='subAddBtn' value='+' /><label style='color:#FFFFFF;'> 倍</label>" +
    "<br/><font color='#FFFFFF' style='white-space: nowrap;'>[最高50倍]</font>" +
    "</td>" +
    "<td id='bonusTd'>" +
    "投注金额：<span id='consume'></span>元<br/>理论最高奖金：<span id='bonus'></span>元" +
    "</td>" +
    "<td>" +
    "<input type='button' id='detailBtn' value='奖金详情' />" +
    "<div id='viewDetailDiv'>" +
    "<table cellspacing='0' cellpadding='6' width='100%'><tr><td colspan='4'><a id='detailClose' style='text-decoration:none;' href='javascript:void(0);'>×</a><a id='printBtn' href='javascript:void(0);'>打印</a><span id='mmSpan'></span></td></tr><tr style='font-weight:bold;'><td style='width:38px;border-right:none;'>注号</td><td style='width:100px;border-right:none;'>过关方式</td><td id='dch' style='width:380px;border-right:none;'>注项内容</td><td id='dBTd' style='width:50px;'>奖金</td></tr></table>" +
    "<div id='detailList'><table id='viewDetailTbl' width='100%' cellpadding='6' cellspacing='0'>" +
    "</table></div>" +
    "</div>" +
    "</td>" +
    "<td style='width: 90px;'><div  id='QRcode'>购彩者方案<br>尽在博坎普<span  class='codeLogo'></span></div></td></tr>" +
    "</table></div>" +
    "<table id='filterTbl' cellpadding='0' cellspacing='0'>" +
    "<tr><td id='filterHeader' style='width:68px; height:17px; line-height:17px; background-image:url(images/lotbg.jpg); background-position:left -129px; background-repeat:no-repeat;'></td><td id='filterResize'>&nbsp;</td></tr>" +
    "<tr style='background-color:#FFFFFF;'><td style='border-left:solid 1px #CCCCCC;'>&nbsp;按日期筛选</td><td style='height:24px; border-top:solid 1px #CCCCCC; border-right:solid 1px #CCCCCC; text-align:right;'><span id='filterCloseBtn' style='display:inline-block; width:16px; line-height:16px; text-align:center; cursor:pointer; color:#F69090; border:solid 1px #F69090;'>×</span></td></tr>" +
    "<tr ><td id='dFilterList' style='background-color:#FFFFFF; border-left:solid 1px #CCCCCC; border-right:solid 1px #CCCCCC; padding:6px;' colspan='2'></td></tr>" +
    "<tr style='background-color:#FFFFFF;'><td style='border-left:solid 1px #CCCCCC;'>&nbsp;按赛事筛选</td><td style='height:24px; border-right:solid 1px #CCCCCC;'>&nbsp;</td></tr>" +
    "<tr><td id='mFilterList' style='background-color:#FFFFFF; border-left:solid 1px #CCCCCC; border-right:solid 1px #CCCCCC; border-bottom:solid 1px #CCCCCC; padding:6px;' colspan='2'></td></tr>" +
    "</table>"
);