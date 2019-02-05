<!DOCTYPE HTML >  
<html>  
  <head>  
    <title>demo页面</title>  
<script src="http://cdn.zhiyoubao.com/new/scripts/jquery-1.8.3.min.js?v=0.0.1" type="text/javascript"></script>
<script src="http://cdn.zhiyoubao.com/new/scripts/main.js?v=0.0.1" type="text/javascript"></script>
<script >  
      $(document).ready(function() {  
            $("#btn").click(function(){
			 
			$.ajax({
				type: 'POST',
				async : false,
				url: 'operate.htm',
				data: {
						"name" : $("#name").val()
					  },
				success: function(data){
					alert(data);
				},
				error: function(msg){
					alert("异常");
				}
			});


			});
                
        });  
     </script>
  </head>  
  <body> 
      所在路径:${rc.contextPath}
  </br>
  id:${flameTest.id}
  </br>
  name:${flameTest.name}
  </br>
  ${dwdw!}<#-- 防止null值报错，加一个!符号即可 -->
<#assign index_num = 0><#-- 分页做index序号很不错 -->
时间排序版
  <#list flameTests?sort_by("createTime") as entity>
  ${entity.id?string('currency')}&nbsp${entity.name}&nbsp${entity.createTime?string('HH:mm:ss')}&nbsp${index_num}</br>
  <#assign index_num = index_num + 1>
  </#list>
  </br>
 不做排序版
  <#list flameTests as entity>
  ${entity.id?string('#.00')}&nbsp${entity.name}&nbsp${entity.createTime?string('yyyy-MM-dd HH:mm:ss')}</br>
  </#list>
  
  <input type="button" value="插入" id="btn">
  名称<input type="text" value="" id="name">
  </body>  
</html>