<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta content="" name="description">
    <meta content="" name="keywords">
    <title>Error_Page_懒人游</title>
    <link href="http://${configs.get('domains.www')}/resources/imgs/favicon.ico" rel="shortcut icon">
    
</head>
<body>
<div class="wrapper">
    <div class="logo"></div>
    <div class="cloud">
        <div class="content">
            <div class="text">${errorMsg}</div>
            <div class="buttonwrap">
                <a class="button guest" id="guest" title="" href="http://${configs.get('domains.www')}"><b>View Lanrenyou</b></a>
                <a class="button report" id="report" title="" href="mailto:service@lanrenyou.com"><b>Report</b></a>
            </div>
        </div>
    </div>
    <div class="error-404"></div>
</div>
</body>
</html>