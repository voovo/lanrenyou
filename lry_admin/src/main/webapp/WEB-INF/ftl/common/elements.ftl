<#macro DegreeType degreeType>
    <#if degreeType??>
        <#if degreeType.type == 1>
        <em class="degree-type degree-type1" title="联系人"></em>
        <#elseif degreeType.type == 2>
        <em class="degree-type degree-type2" title="间接联系人"></em>
        <#elseif degreeType.type == 3>
        <em class="degree-type degree-type3" title="三度人脉"></em>
        </#if>
    </#if>
</#macro>
<#macro nec name="" simpleTrans=false><#if simpleTrans>${name?html?replace('  |  ',' &nbsp;','r')?replace('\n','<br />')}<#else >${name!}</#if></#macro>
<#macro upload  id name oncomplete="void">
<div class="floatleft"><input id="${id}-display" class="text" type="text" style="width:200px;" readonly="yes"
                              style="float:left;"/></div>
<div class="floatleft"><label class="file"><span>浏览</span><input id="${id}" type="file" name="${name}" class="file"
                                                                 accept="image/gif,image/jpeg"/></label></div>
</#macro>

<#macro swfupload  id type name="file" oncomplete="void">
<div class="floatleft" id="${id}" style="width:200px;"></div>
    <@holder>
    <script type="text/javascript">
        $('#${id}').swfupload({
            file_post_name:'${name}',
            post_params:{
                type:'${type}'
            },
            upload_success_handler:function () {
                alert(1);
            }
        });
    </script>
    </@holder>
</#macro>

<#macro checkbox name value label prefix="" checked=""><input class="checkbox" name="${name}" type="checkbox"
                                                              value="${value}"
                                                              id="<#if prefix != "">${prefix}-</#if>${name}-${value}-checkbox"
                                                              <#if checked != "">checked="checked"</#if> /><label
        class="checkbox" for="<#if prefix != "">${prefix}-</#if>${name}-${value}-checkbox">${label}</label> </#macro>

<#macro lifetime input>
<span class="lifetime">
    <#if input?is_date>
    </#if>
</span>
</#macro>

<#macro dateinput name short="no" startyear=1970 value="" endyear=0 id="dateinput" present="false" linked="" withtime="no" class="">
<input id="${id}" type="hidden" name="${name}" value="<#if value!=''>${value}</#if>" <#if class!="">class="${class}"</#if>/>
    <#if value == '' || value == '2030-01-01' >
        <#assign valueYear = 0 />
        <#assign valueMonth = 0 />
        <#assign valueDate = 0 />
    <#else>
        <#if withtime == "yes" || withtime == "true">
            <#assign valueYear = value?datetime('yyyy-MM-dd hh:mm')?string('yyyy')?number?int />
            <#assign valueMonth = value?datetime('yyyy-MM-dd hh:mm')?string('MM')?number?int />
            <#assign valueDate = value?datetime('yyyy-MM-dd hh:mm')?string('dd')?number?int />
        <#else>
            <#assign valueYear = 0 />
            <#assign valueMonth = 0 />
            <#assign valueDate = 0 />
            <#assign valueYear = value?date('yyyy-MM-dd')?string('yyyy')?number?int />
            <#assign valueMonth = value?date('yyyy-MM-dd')?string('MM')?number?int />
            <#assign valueDate = value?date('yyyy-MM-dd')?string('dd')?number?int />
        </#if>
    </#if>
    <#assign end = endyear />
    <#if endyear==0>
        <#assign end = 2010 />
    </#if>
    <#assign yearId = id + "-year" />
    <#assign monthId = id + "-month" />
    <#assign dateId = id + "-date" />
    <#assign hourId = id + "-hour" />
    <#assign minuteId = id + "-minute" />
<select id="${yearId}" <#if value == '2030-01-01'>disabled="disabled"</#if> <#if class!="">class="${class}"</#if>>
    <option value=""></option>
    <#list startyear..end as y>
        <option value="${y}"<#if y==valueYear> selected</#if>>${y}</option>
    </#list>
</select> <span class="input">年</span>
<select id="${monthId}" <#if value == '2030-01-01'>disabled="disabled"</#if> <#if class!="">class="${class}"</#if>>
    <option value=""></option>
    <#list 1..12 as m>
        <option value="<#if m<10>0</#if>${m}"<#if m==valueMonth> selected="selected"</#if>>${m}</option>
    </#list>
</select> <span class="input">月</span>
    <#if short == "no" ||  short == "false">
    <select id="${dateId}" <#if value == '2030-01-01'>disabled="disabled"</#if> <#if class!="">class="${class}"</#if>>
        <option value=""></option>
        <#list 1..28 as d>
            <option value="<#if d<10>0</#if>${d}"<#if d==valueDate> selected="selected"</#if>>${d}</option>
        </#list>
        <#if valueMonth == 1 || valueMonth == 3 || valueMonth == 5 || valueMonth == 7 || valueMonth == 8 || valueMonth == 10 || valueMonth == 12 >
            <#list 29..31 as d>
                <option value="<#if d<10>0</#if>${d}"<#if d==valueDate> selected="selected"</#if>>${d}</option>
            </#list>
        </#if>
        <#if valueMonth == 4 || valueMonth == 6 || valueMonth == 9 || valueMonth == 11>
            <#list 29..30 as d>
                <option value="<#if d<10>0</#if>${d}"<#if d==valueDate> selected="selected"</#if>>${d}</option>
            </#list>
        </#if>
        <#if valueMonth == 2 && valueYear % 4 == 0 && valueYear % 400 != 0>
            <option value="29"<#if 29==valueDate> selected="selected"</#if>>29</option>
        </#if>
    </select> <span class="input">日</span>
    </#if>
    <#if present == "true" || present == "yes">
        <#if value == '2030-01-01'>
            <@checkbox name="${id}" value="present" label="至今" checked="checked" />
        <#else>
            <@checkbox name="${id}" value="present" label="至今" />
        </#if>
    </#if>
    <#if withtime == "true" || withtime == "yes">
        <#assign valueHour = value?datetime('yyyy-MM-dd HH:mm')?string('HH')?number?int />
        <#assign valueMinute = value?datetime('yyyy-MM-dd hh:mm')?string('mm')?number?int />
    <select id="${hourId}">
        <option value=""></option>
        <#list 0..23 as d>
            <option value="<#if d<10>0</#if>${d}" <#if d==valueHour> selected="selected"</#if>><#if d<10>
                0</#if>${d}</option>
        </#list>
    </select> 时
    <select id="${minuteId}">
        <option value=""></option>
        <#list 0..59 as d>
            <option value="<#if d<10>0</#if>${d}" <#if d==valueMinute> selected="selected"</#if>><#if d<10>
                0</#if>${d}</option>
        </#list>
    </select> 分
    </#if>
    <#if linked != "">
        <@holder>
        <script type="text/javascript">
            function checkDateinputMonth() {
                if ($('#${linked}-year').attr('disabled')) return true;
                var y1 = $('#${yearId}').val(), m1 = $('#${monthId}').val(), y2 = $('#${linked}-year').val(), m2 = $('#${linked}-month').val();
                if (y1 == '' || m1 == '' || y2 == '' || m2 == '') {
                    return true;
                }
                if (y1 == y2) {
                    if (parseInt(m1, 10) >= parseInt(m2, 10)) {
                        return false;
                    }
                }
                return true;
            }
        </script>
        </@holder>
        <@validate field="#${linked}" type="custom" data="checkDateinputMonth" message="时间选择有误" />
    </#if>
    <script type="text/javascript">
        $('#${monthId}, #${yearId}, #${dateId}, #${hourId}, #${minuteId}').change(function () {
            $('#${id}').triggerHandler('click', 'change');
            var m = $('#${monthId}').val(), y = $('#${yearId}').val();
            if (!$('#${dateId}').length) {
                if (m && y) {
                    $('#${id}').val($('#${yearId}').val() + '-' + $('#${monthId}').val() + '-01');
                    $('#${id}').triggerHandler('blur');
                } else {
                    $('#${id}').val('');
                }
            } else if (!$('#${hourId}').length) {
                var d = $('#${dateId}').val();
                if (m && y && d) {
                    $('#${id}').val($('#${yearId}').val() + '-' + $('#${monthId}').val() + '-' + $('#${dateId}').val());
                    $('#${id}').triggerHandler('blur');
                } else {
                    $('#${id}').val('');
                }
            } else {
                var h = $('#${hourId}').val(), mm = $('#${minuteId}').val();
                $('#${id}').val($('#${yearId}').val() + '-' + $('#${monthId}').val() + '-' + $('#${dateId}').val() + ((h == '' || mm == '') ? '' : ' ' + $('#${hourId}').val() + ':' + $('#${minuteId}').val()));
                $('#${id}').triggerHandler('blur');
            }
            if ($('#${dateId}').length == 0) return;
            if ($.inArray(m, ['02', '04', '06', '09', '11']) != -1) {
                $('#${dateId}').find('option[value="31"]').remove();
                if (m == 2) {
                    $('#${dateId}').find('option[value="30"]').remove();
                    if (parseInt($('#${yearId}').val()) % 4 != 0 || parseInt($('#${yearId}').val()) % 400 == 0) {
                        $('#${dateId}').find('option[value="29"]').remove();
                    } else {
                        if ($('#${dateId}').find('option[value="29"]').length == 0) {
                            $('#${dateId}').append('<option value="29">29</option>');
                        }
                    }
                }
            } else {
                $([29, 30, 31]).each(function () {
                    if (!$('#${dateId}').find('option[value="' + this + '"]').length) {
                        $('#${dateId}').append('<option value="' + this + '">' + this + '</option>');
                    }
                });
            }
        });
        $('#${id}-present-checkbox').click(function () {
            $('#${monthId}').trigger('change');
            $('#${monthId}, #${yearId}, #${dateId}').attr('disabled', $(this).attr('checked'));
            if ($(this).attr('checked')) {
                $('#${id}').val('2030-01-01');
            } else {
                $($('#${id}')[0].form).triggerHandler('submit.dateinput');
            }
            $('#${id}').trigger('change');
        });
    </script>
    <#if linked != "">
        <@holder>
        <script type="text/javascript">
            $('#${yearId}').change(function () {
                var y;
                if ($(this).val() == '') {
                    y = $(this).find('option:eq(1)').val();
                } else {
                    y = $(this).val();
                }
                var end = $('#${linked}-year option:last').val();
                var y2 = $('#${linked}-year').val();
                if ($('#${linked}-present-checkbox').attr('checked')) return;
                $('#${linked}-year').empty().append('<option value=""></option>');
                for (var i = y; i <= end; i++) {
                    $('#${linked}-year').append('<option value="' + i + '">' + i + '</option>');
                }
                $('#${linked}-year').val(y2 > y ? y2 : y).trigger('change');
                $('#${linked}').trigger('change');
            });
            $('#${monthId}').change(function () {
                $('#${linked}').trigger('change');
            });
        </script>
        </@holder>
    </#if>
</#macro>

<#macro share title content link pic_url=''>
<span class="b">分享到：</span>
<a class="qq" id="qq-msn" href="javascript:void(0);">QQ/MSN</a>
<a class="renren" href="javascript:void(0);">人人</a>
<a class="kaixin" href="javascript:void(0);">开心</a>
<a class="sina" href="javascript:void(0)">新浪微博</a>
<div id="msnqq-share" style="display:none">
    <div id="msnqq-share-content" style="padding:30px 0 40px 18px;">
        <p style="font-size:14px;font-weight:bold;">复制此分享链接地址, 用QQ、MSN等发送给你的朋友</p><br>
        <input type="text" style="width:150px; height:20px;width:300px; vertical-align:middle;" id="share-url"
               value="${link}"/>
        <@button class="fresh" tag="a" id="copy-share-url">复制</@button>
    </div>
    <div style="display:none"><@button class="default">确定</@button></div>
</div>
    <@holder>
    <script type="text/javascript">
        var share_url = '${link}';
        var share_title = '${title?url}';
        var share_content = '${content?url}';
        $('.sina').click(function () {
            window.open('http://v.t.sina.com.cn/share/share.php?appkey=&url=' + share_url + '&title=' + share_content + '&source=&sourceUrl=&content=utf-8&pic=', 'mb', ['toolbar=0,status=0,resizable=1,width=440,height=430,left=', (screen.width - 440) / 2, ',top=', (screen.height - 430) / 2].join(''));
            return false;
        });
        $('.renren').click(function () {
            window.open('http://share.renren.com/share/buttonshare.do?link=' + share_url, 'mb', ['toolbar=0,status=0,resizable=1,width=550,height=400,left=', (screen.width - 550) / 2, ',top=', (screen.height - 400) / 2].join(''));
            return false;
        });
        $('.kaixin').click(function () {
            var kaixin = window.open('http://www.kaixin001.com/repaste/share.php?&rurl=' + share_url + '&rtitle=' + share_title + '&rcontent=' + share_content, 'kaixin');
            kaixin.focus();
        });
        $('#qq-msn').click(function () {
            $.dialog('#msnqq-share', {mask:true, title:" 分享", width:480, mercy:false});
            return false;
        });
        $('#copy-share-url').click(function () {
            var val = $('#share-url').val();
            if ($.browser.msie) {
                $('#share-url').focus().select();
                window.clipboardData.setData('text', val);
                if (window.clipboardData.getData('Text').length != 0)
                    alert("复制成功，现在可以粘贴（Ctrl+v）到QQ或MSN中了");
            } else {
                $('#share-url').focus().select();
                alert('您使用的浏览器不支持此复制功能，请使用Ctrl+C或鼠标右键');
            }
            return false;
        });


    </script>
    </@holder>
</#macro>

<#macro cutWords words="" length=5 prefix="" suffix="" simpleTrans=true>
    <#assign wordStr = words?string!>
    <#if wordStr?length gt length>
        <#if simpleTrans>
        ${prefix}${wordStr?substring(0,length)?html}${suffix}
        <#else >
        ${prefix}${wordStr?substring(0,length)}${suffix}
        </#if>
    <#else>
        <#if simpleTrans>
        ${wordStr?html}
        <#else >
        ${wordStr}
        </#if>
    </#if>
</#macro>


<#macro pageNav total = "1" current = "1" urlpattern="#">
    <#assign totalInt = total?number?int />
    <#assign currentInt = current?number?int />
    <#if totalInt lte 1>
        <#return />
    </#if>
<div class="paging">
    <#if currentInt gt 1>
        <a class="prev" href="${urlpattern?replace("%d", "" + (currentInt - 1))}"></a>
    </#if>
    <#if currentInt == 1>
        <span class="current">1</span>
    <#else>
        <a href="${urlpattern?replace("%d", "1")}">1</a>
    </#if>
    <#assign p1 = 2 />
    <#if currentInt gte 3>
        <#assign p1 = (currentInt - 2) />
    </#if>
    <#assign p2 = 10 />
    <#if currentInt gte 3>
        <#assign p2 = (currentInt + 2) />
    </#if>
    <#if currentInt gt (totalInt - 2)>
        <#assign p1 = (totalInt - 4) />
    </#if>
    <#if p1 gt 2>
        <a class="dot" href="${urlpattern?replace("%d", "" + (currentInt - 2))}" title="第${currentInt - 2}页"></a>
    </#if>
    <#list p1 .. p2 as i>
        <#if i gte 2>
            <#if i gte totalInt>
                <#break />
            </#if>
            <#if currentInt == i>
                <span class="current">${i}</span>
            <#else>
                <a href="${urlpattern?replace("%d", "" + i)}">${i}</a>
            </#if>
        </#if>
    </#list>
    <#if p2 lt totalInt>
        <a class="dot" href="${urlpattern?replace("%d", "" + (p2 + 1))}" title="第${p2 + 1}页"></a>
    </#if>
    <#if currentInt == totalInt>
        <span class="current">${totalInt}</span>
    <#else>
        <a href="${urlpattern?replace("%d", "" + totalInt)}">${totalInt}</a>
    </#if>
    <#if currentInt lt totalInt>
        <a class="next" href="${urlpattern?replace("%d", "" + (currentInt + 1))}"></a>
    </#if>
</div>
</#macro>