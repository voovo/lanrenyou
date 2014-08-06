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
                    当前位置: <a href="main.html">管理首页</a> - 审核管理 - 个人信息审核
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
                <form id="querytoaudituserform" action="" >
                <table width="100%" border="0" cellspacing="2" cellpadding="4">
                    <tr>
                        <td align="right">国家：</td>
                        <td>
                            <select name="querycountry" id="">
                                <#include "/common/dict/country_option.ftl">
                            </select>
                        </td>
                        <td align="right">状态：</td>
                        <td>
                            <select name="querystatus" id="">
                                <option value="-1" <#if queryConditionMap?? && queryConditionMap.get('querystatus')==-1>selected="selected"</#if>>请选择</option>
                                <option value="1" <#if queryConditionMap?? && queryConditionMap.get('querystatus')==1>selected="selected"</#if>>待审核</option>
                                <option value="2" <#if queryConditionMap?? && queryConditionMap.get('querystatus')==2>selected="selected"</#if>>审核通过</option>
                                <option value="3" <#if queryConditionMap?? && queryConditionMap.get('querystatus')==3>selected="selected"</#if>>审核不通过</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" class="s_btn" value="搜索">
                        </td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
                </form>
            </div>


            <table width="100%" class="mytab"  border="1">
              <thead>
                <th width="10%">交易编码</th>
                <th width="8%">用户名</th>
                <th width="18%">邮箱</th>
                <th width="5%">国籍</th>
                <th width="15%">年收入</th>
                <th width="15%">账户类型</th>
                <th width="5%">状态</th>
                <th>操作/状态</th>
              </thead>
              <#if pageIterator?? && pageIterator.getData()??>
	              <#list pageIterator.getData() as user >
					<#assign uid = user.userId>
					<#if userAccountMap.get(uid)??>
						<#assign userAccount = userAccountMap.get(uid)>
					</#if>
					<#if investExpMap.get(uid)??>
						<#assign investExp = investExpMap.get(uid)>
					</#if>
					<#if edocMap?? && edocMap.get(uid)??>
						<#assign edoc = edocMap.get(uid)>
					</#if>
		              <tr align="center">
		                <td>${userAccount.tradeCode!''}</td>
		                <td><a href="javascript:;" class="UserInfo">${user.firstName!''} ${user.lastName!''}</a></td>
		                <td><#if userEmailMap.get(uid)??>${userEmailMap.get(uid)!''}</#if></td>
		                <td><#if user.nationalityCode??>${DictionaryUtil.COUNTRY_MAP.get(user.nationalityCode)}</#if></td>
		                <td>${DictionaryUtil.ANNUALINCOME_MAP.get(user.annualIncomeCode)}</td>
		                <td><#if userAccount?? && userAccount.mt4AccountType?? && MT4AccountTypeEnum.valueOf(userAccount.mt4AccountType)??>${MT4AccountTypeEnum.valueOf(userAccount.mt4AccountType).getText()!''}</#if></td>
		                <#assign statusValue="" />
		                <#if user.status == 1>
		                	<#assign statusValue = "待审核" >
		                <#elseif user.status == 2>
		                	<#assign statusValue = "审核通过" >
		                <#elseif user.status == 3>
		                	<#assign statusValue = "审核不通过" >
		                </#if>
		                <td>${statusValue!''}</td>
		                <td>
		                	<#if user.status == 1>
		                    <a class="green" href="/admin/audit/user_pass?uid=${uid!''}" title="同意">同意</a>
		                    <a class="black" href="/admin/audit/user_nopass?uid=${uid!''}" title="拒绝">拒绝</a>
		                    </#if>
		                </td>
		              </tr>
		              <tr class="hide">
		                  <td colspan="8">
		                    <ul class="user_infos clearfix">
		                        <li>用户名：${user.firstName!''} ${user.lastName!''}</li>
		                        <li>邮箱地址：${userEmailMap.get(uid)!''}</li>
		                        <li>电话：${userPhoneMap.get(uid)!''}</li>
		                        <li>交易编码：<#if userAccount??>${userAccount.tradeCode!''}</#if></li>
		                        <li>姓名：${user.cnName!''}</li>
		                        <li>出生日期：${user.birthday!''}</li>
		                        <li>证件类型：<#if user.idType??>${DictionaryUtil.IDENTITY_TYPE_MAP.get(user.idType)}</#if></li>
		                        <li>证件号码：${user.idNum!''}</li>
		                        <li>国籍：<#if user.nationalityCode??>${DictionaryUtil.COUNTRY_MAP.get(user.nationalityCode)}</#if></li>
		                        <li>居住国家：<#if user.residencyCountryCode??>${DictionaryUtil.COUNTRY_MAP.get(user.residencyCountryCode)}</#if></li>
		                        <li>城市：${user.cityCode!''}</li>
		                        <li>居住地址：${user.address!''}</li>
		                        <li>邮编：${user.postcode!''}</li>
		                        <li>就业状况：<#if user.occupationCode??>${DictionaryUtil.OCCUPATION_MAP.get(user.occupationCode)}</#if></li>
		                        <li>行业类型：<#if user.industriesCode??>${DictionaryUtil.INDUSTRIES_MAP.get(user.industriesCode)}</#if></li>
		                        <li>年收入：<#if user.annualIncomeCode??>${DictionaryUtil.ANNUALINCOME_MAP.get(user.annualIncomeCode)}</#if></li>
		                        <li>净资产：<#if user.netAssetsValueCode??>${DictionaryUtil.NETASSETSVALUE_MAP.get(user.netAssetsValueCode)}</#if></li>
		                        <li>外汇/CFD投资经验：<#if investExp.investmentExperienceCode??>${DictionaryUtil.INVESTMENT_EXPERIENCE_MAP.get(investExp.investmentExperienceCode)}</#if></li>
		                        <li>其他投资经验：<#if investExp.othersCode??>${DictionaryUtil.OTHER_EXPERINCE_MAP.get(investExp.othersCode)}</#if></li>
		                        <li>外汇/CFD投资频率：<#if investExp.tradingFrequencyCode??>${DictionaryUtil.TRADING_FREQUENCY_MAP.get(investExp.tradingFrequencyCode)}</#if></li>
		                    </ul>
		                    <p>用户上传资料</p>
		                    <ul class="user_upload clearfix">
		                    	<#if edocList??>
		                    		<#list edocList as edoc>
		                    			<#if edoc.docType == EDocTypeEnum.IDENTITY_PROVE.getValue()>
		                    				<li><a href="${edoc.filePath!'#'}" title="身份证明">身份证明</a></li>
		                    			<#elseif edoc.docType == EDocTypeEnum.ADDRESS_PROVE.getValue()>
				                        	<li><a href="${edoc.filePath!'#'}" title="地址证明">地址证明</a></li>
				                        <#elseif edoc.docType == EDocTypeEnum.WITHDRAW_ACCOUNT.getValue()>
				                        	<li><a href="${edoc.filePath!'#'}" title="出金银行账户信息">出金银行账户信息</a></li>
				                        <#elseif edoc.docType == EDocTypeEnum.CREDIT_CARD.getValue()>
				                        	<li><a href="${edoc.filePath!'#'}" title="信用卡信息">信用卡信息</a></li>
				                        <#elseif edoc.docType == EDocTypeEnum.ADDMONEY_ACCOUNT.getValue()>
				                        	<li><a href="${edoc.filePath!'#'}" title="入金银行卡信息">入金银行卡信息</a></li>
				                        <#elseif edoc.docType == EDocTypeEnum.PAYMENT_AUTHORIZATION.getValue()>
				                        	<li><a href="${edoc.filePath!'#'}" title="付款授权书">付款授权书</a></li>
				                        <#elseif edoc.docType == EDocTypeEnum.OTHER_DOC.getValue()>
				                        	<li><a href="${edoc.filePath!'#'}" title="其他资料">其他资料</a></li>
				                        </#if>
		                    		</#list>
		                    	</#if>
		                    </ul>
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
			<@pageNav total="${totalPageCount!0}" current="${(pageIterator.page)!0}" urlpattern="/admin/audit/user_list?pageNo=%d"/>
        </div>

    </body>

</html>