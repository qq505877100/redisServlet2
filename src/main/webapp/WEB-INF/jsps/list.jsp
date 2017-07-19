<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息列表</title>
<!-- EasyUI相关的主题文件 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
<!-- EasyUI提供的图标文件 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js"></script>
<script>
    //定义提交的url地址
var url="";
//添加学生
function addStu(){
  $("#form1").form("clear");
  $("#dlg").dialog("open");
  //$("#stuId").attr("disabled",true);
  url="${pageContext.request.contextPath}/add";
  
}

//编辑学生信息，回显
function editStu(){
	var obj=$("#dg").datagrid("getSelected");
	if(obj!=null){
		$("#dlg").dialog("open");
		$("#form1").form("load",obj);
		//这里可以让id栏变为不可用状态，即不可以修改它。
        //$("#stuId").attr("disabled",false);
		url="${pageContext.request.contextPath}/edit";
	}
}

//删除学生
function delStu(){
	var obj=$("#dg").datagrid("getSelected");
	//obj中的值为Object{stuAge:18,stuId:4,stuName:"tom4",stuNum:4,stuQQ:"12646123",stuSex: "female" }
	if(obj!=null){
		$.messager.confirm("确认删除","确定删除该学生信息吗?",function(dd){
			if(dd){
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/remove",
                    data: {id:obj.id},
                    dataType : "json",
                    success: function (data) {
                        if(data.result){
                            $("#dg").datagrid("reload");
                            alert(data.msg);
                        }else{
                            alert(data.msg);
                        }
                    }
                });
				/*$.post("{pageContext.request.contextPath}/remove",{id:obj.id},function(data){
					alert(data);
					if(dt.result){
						$("#dg").datagrid("reload");
                        alert(data.msg);
					}else{
						alert(data.msg);
					}
				},"json");*/
			}
		})		
	}
}

//保存学生的对话框
function saveStu(){
   $('#form1').form('submit', {    
	    url:url,
       //效验字段信息
	    onSubmit: function(){    

	    },    
	    success:function(data){
	        //设置返回jsao数据，在服务端拼接即可，result(true,false),msg（提示消息）
	        var dt=eval("("+data+")");
	        if(dt.result){
	        	$("#dlg").dialog("close");
	        	$("#dg").datagrid("reload");
	        }else{
	        	alert(dt.msg);
	        }
	    }    
	});  
}
//关闭添加学生对话框
function closeWin(){
	$("#dlg").dialog("close");
}
</script>
</head>
<body>
<table id="dg" class="easyui-datagrid" style="width:700px;height:315px;align-content: center;"
        data-options="url:'${pageContext.request.contextPath}/list',fitColumns:true,singleSelect:true,pagination:true,
        toolbar:'#tb',rownumbers:true">
    <thead>
        <tr>   
            <th data-options="field:'id',width:230">编号</th>
            <th data-options="field:'name',width:100">姓名</th>
            <th data-options="field:'birthday',width:100">生日</th>
            <th data-options="field:'remark',width:100">备注</th>
            <th data-options="field:'avgScore',width:100">平均分</th>
        </tr>   
    </thead>   
</table>
<div id="tb">
  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addStu()">增加学生</a>
  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="editStu()">编辑学生</a>
  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="delStu()">删除学生</a>
</div>

<div id="dlg" style="" class="easyui-dialog" data-options="width:250,height:200,buttons:'#btn',closed:true">
  <form id="form1" method="post">
    <input type="hidden" name="id" id="stuId"/><br/>
    姓名&nbsp&nbsp:&nbsp<input type="text" name="name"/><br/>
    生日&nbsp&nbsp:&nbsp<input type="text" name="birthday"/><br/>
    备注&nbsp&nbsp:&nbsp<input type="text" name="remark"/><br/>
    平均分:&nbsp<input type="text" name="avgScore"/><br/>
  </form>
</div>
<div id="btn">
  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="saveStu()">保存</a>
  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closeWin()">取消</a>
</div>
</body>
</html>