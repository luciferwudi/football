var teamList = {};
$(function () {

    var $league = $('.league');
    $league.on('click', function () {
        if ($('.sidebar').hasClass('am-sticky')) {
            $("html,body").scrollTop(115);
        }
        if ($(this).hasClass('active')) {
            return false;
        }
        var $lid = $(this).data('lid');
        history.pushState({}, '', G_urls.info + '/injury/' + $lid);
        showTeamList();
    });

    // 监听历史
    if (history.pushState) {
        window.addEventListener("popstate", function () {
            showTeamList();
        });
    }

    function showTeamList() {
        //js 获取字符串中最后一个斜杠后面的内容
        var htmlHref = window.location.href;
        htmlHref = htmlHref.replace(/^http:\/\/[^/]+/, "");
        var addr = htmlHref.substr(htmlHref.lastIndexOf('/', htmlHref.lastIndexOf('/') - 1) + 1);
        var index = addr.lastIndexOf("\/");
        var lid = decodeURI(addr.substring(index + 1, addr.length)) || 92;
        lid = lid == "injury" ? 92 : lid;
        $('.league').removeClass('active');
        var $this = $(".league[data-lid='" + lid + "']");
        $this.addClass('active');
        var $lname = $this.text();
        //设置标题
        var title = "足彩伤停_" + $lname + "伤停_" + $lname + "球员受伤_最新足球联赛运动员伤病信息汇总_足球伤病_有料体育";
        var content = "足彩伤停、" + $lname + "最新伤停、" + $lname + "球员受伤";
        $('title').text(title);
        $('meta[name="keywords"]').attr('content', content);
        $('#teamList .swipe').remove();
        $('.loading').show();
        if (!teamList[lid]) {
            $.ajax({
                url     : G_urls.info + '/injury/get_date',
                type    : 'post',
                data    : {
                    lid: lid
                },
                cache   : true,
                dataType: 'json',
                success : function (result) {
                    teamList[lid] = result.data;
                    htmlTeamList(result.data);
                },
                complete: function () {
                    $('.loading').hide();
                }
            });
        } else {
            htmlTeamList(teamList[lid]);
            $('.loading').hide();
        }
    }

    function htmlTeamList(teamList) {
        var html = '';
        $.each(teamList, function (k, team) {
            html +=
                '<div class="swipe">' +
                '<div class="info-block clearfix">' +
                '<div class="block-left">' +
                '<div class="team-logo-box">' +
                '<img class="team-logo" src="' + team.logo + '" onerror="javascript:this.src=\'https://www.iuliao.com/info/teamlogo/pic_none.gif\'"/>' +
                '</div>' +
                '<div class="team-info">' +
                '<a href="' + team.team_href + '" target="_blank">' + team.name + '<br/><span class="values">' + team.market_value + '</span>' + '</a>' +
                '</div>' +
                '</div>' +
                '<div class="info-list">';

            var playerList = team.injuries;
            var num = playerList.length;
            var mgt20 = num < 2 ? 'mgt20' : '';
            $.each(playerList, function (_, player) {
                var info = player.info;
                var none = info.inleague || 'none';
                var league = info.inleague || '-';
                html +=
                    '<a href="' + player.player_href + '" target="_blank">' +
                    '<div class="injury-swipe clearfix ' + mgt20 + '">' +
                    '<div class="player-logo-box">' +
                    '<img class="player-logo" src="' + player.plogo + '" onerror="javascript:this.src=\'https://m.api.iuliao.com/playerlogo/err.gif\'" />' +
                    '</div>' +
                    '<div class="injury-info">' +
                    '<div class="player pull-left">' + player.shirtnum + ' . ' + player.pname + '</div>' +
                    '<div class="value pull-left">' + player.pmarket_value + '</div>' +
                    '<div class="position pull-left"><span class="color' + player.position + '">' + player.position_name + '</span></div>' +
                    '<div class="cause pull-left"><img src="' + info.logo + '">' + info.text + '</div>' +
                    '<div class="time pull-left">' + info.time + '</div>' +
                    '<div class="affect pull-left ' + none + '">' + league + '</div>' +
                    '</div>' +
                    '</div>' +
                    '</a>';
            });

            html +=
                '</div>' +
                '</div>' +
                '</div>';
        });
        $('#teamList').append(html);
    }
});

