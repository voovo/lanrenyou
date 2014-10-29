<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
   <head>
      <title>上传图片</title>
      <meta http-equiv="pragma" content="no-cache">
      <meta http-equiv="cache-control" content="no-cache">
      <meta http-equiv="expires" content="0">
      <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
      <meta http-equiv="description" content="This is my page">

   </head>

   <body>
 

      <form name="Webform" action="/upload/submit" method="post" enctype="multipart/form-data">
         图片:
         <br />
         <input type="file" name="upfile">
         <br />
         
         <input type="text" name="alt" />
         
         <input type="text" name="linkUrl" />

         <input type="submit" name="Submit" value="Submit Files" />
      </form>

   </body>
</html>