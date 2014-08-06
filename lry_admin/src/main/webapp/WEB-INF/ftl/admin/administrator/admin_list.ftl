<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
    
    <head>
        <meta http-equiv=content-type content="text/html; charset=utf-8">
        <link href="/css/admin.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" href="/css/datePicker.css">
        <script type='text/javascript' src='/wp-includes/js/jquery/jquery.js'></script>
        <link rel="stylesheet" href="/css/jquery-ui.css">
        <script src="/js/jquery-ui.js"></script>
        <script type="text/javascript" src="/js/admin.js"></script>
    </head>
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
    <#if currentInt gte 6>
        <#assign p1 = (currentInt - 4) />
    </#if>
    <#assign p2 = 10 />
    <#if currentInt gte 6>
        <#assign p2 = (currentInt + 5) />
    </#if>
    <#if currentInt gt (totalInt - 5)>
        <#assign p1 = (totalInt - 9) />
    </#if>
    <#if p1 gt 2>
        <a class="dot" href="${urlpattern?replace("%d", "" + (currentInt - 5))}" title="第${currentInt - 5}页"></a>
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
    <body>
        <table cellspacing=0 cellpadding=0 width="100%" align=center border=0 >
            <tr height=28>
                <td background=/imgs/admin/title_bg1.jpg style="padding-left:10px;">
                    当前位置: <a href="main.html">管理首页</a> - 管理员列表
                </td>
            </tr>
            <tr>
                <td bgcolor=#b1ceef height=1>
                </td>
            </tr>
            <tr height=20>
                <td background=/imgs/admin/shadow_bg.jpg>
                </td>
            </tr>
        </table>

        <div class="main_content">
            <!-- 搜索 -->
            <div class="main_search">
                <h4>搜索</h4>
                <form action="/admin/administrator/list" method="get" id="queryAdminForm"/>
                <table width="100%" border="0" cellspacing="2" cellpadding="4">
                    <tr>
                        <td width="100" align="right">用户名：</td>
                        <td><input class="s_input" name="queryname" type="text" <#if queryConditionMap?? && queryConditionMap.get('queryname')??>value="${queryConditionMap.get('queryname')!''}"</#if>></td>
                        <td align="right" width="100">角色：</td>
                        <td width="400">
                        	<select name="queryroleId">
                        		<option value ="-1" <#if queryConditionMap?? && queryConditionMap.get('queryroleId')==-1>selected="selected"</#if>>所有角色</option>
                        		<#if roleList??>
                        		<#list roleList as role>
									<option value ="${role.id!''}" <#if queryConditionMap?? && queryConditionMap.get('queryroleId')==role.id>selected="selected"</#if>>${role.name!''}</option>
                        		</#list>
                        		</#if>
							</select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">状态：</td>
                        <td>
                            <select name="querystatus" id="">
                                <option value="-1" <#if queryConditionMap?? && queryConditionMap.get('querystatus')==-1>selected="selected"</#if>>请选择</option>
                                <option value="1" <#if queryConditionMap?? && queryConditionMap.get('querystatus')==1>selected="selected"</#if>>正常</option>
                                <option value="2" <#if queryConditionMap?? && queryConditionMap.get('querystatus')==2>selected="selected"</#if>>关闭</option>
                            </select>
                        </td>
                        <td align="right"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" class="s_btn" value="搜索">
                        </td>
                        <td></td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                </form>
            </div>

            <a href="/admin/administrator/add" class="s_btn" title="新增管理员">新增管理员</a>
            <table width="100%" class="mytab"  border="1">
              <thead>
                <th width="6%">编号</th>
                <th width="15%">用户名</th>
                <th width="20%">邮箱</th>
                <th width="10%">角色</th>
                <th width="15%">创建日期</th>
                <th width="5%">状态</th>
                <th>操作</th>
              </thead>
              <#if pageIterator?? && pageIterator.getData()??>
	              <#list pageIterator.getData() as admin >
		              <tr align="center">
		                <td>${admin.id!''}</td>
		                <td>${admin.name!''}</td>
		                <td>name@lanrenyou.com</td>
		                <td><#if roleMap?? && roleMap.get(admin.roleId)??>${roleMap.get(admin.roleId)}</#if></td>
		                <td>${admin.createTime[0..18]}</td>
		                <#if admin.status == 1>
		                	<#assign statusStr = '正常'>
		                <#elseif admin.status==2>
		                	<#assign statusStr = '关闭'>
		                </#if>
		                <td>${statusStr}</td>
		                <td>
		                    <a class="black" href="/admin/administrator/update?id=${admin.id!''}" title="修改">修改</a>
		                    <a class="red" href="/admin/administrator/delete?id=${admin.id!''}" title="删除">删除</a>
		                </td>
		              </tr>
	              </#list>
              </#if>
            </table>

			<#if (pageIterator.totalPages)?? && pageIterator.totalPages gt 999>
			    <#assign totalPageCount = 999>
			<#else>
			    <#assign totalPageCount = pageIterator.totalPages!0>
			</#if>
			<@pageNav total="${totalPageCount!0}" current="${(pageIterator.page)!0}" urlpattern="/admin/administrator/list?pageNo=%d"/>
        </div>
    </body>

</html>