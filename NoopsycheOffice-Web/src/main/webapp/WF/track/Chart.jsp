<%@page import="cn.jflow.common.model.WF_Chart"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	String WorkID=request.getParameter("WorkID");
	int workid=0;
	String FID=request.getParameter("FID");
	int fid=0;
	String FK_Node=request.getParameter("FK_Node");
	int fk_node=0;
	String FK_Flow=request.getParameter("FK_Flow");
	String DoType=request.getParameter("DoType");
	
	if(WorkID!=null && !WorkID.equals("")){
		workid=Integer.parseInt(WorkID);
	}
	if(FID!=null && !FID.equals("")){
		fid=Integer.parseInt(FID);
	}
	if(FK_Node!=null && !FK_Node.equals("")){
		fk_node=Integer.parseInt(FK_Node);
	}
	WF_Chart wc=new WF_Chart(request,response,workid,fid,fk_node,FK_Flow,DoType,basePath);
	wc.init();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>Flow Chart</title>
    <style type="text/css">
    html, body {
	    height: 100%;
	    overflow: auto;
    }
    body {
	    padding: 0;
	    margin: 0;
    }
    #silverlightControlHost {
	    height: 100%;
	    text-align:center;
    }
    </style>
    <script src="Silverlight.js" type="text/javascript"></script>
    <script type="text/javascript">
        function onSilverlightError(sender, args) {
            var appSource = "";
            if (sender != null && sender != 0) {
                appSource = sender.getHost().Source;
            }

            var errorType = args.ErrorType;
            var iErrorCode = args.ErrorCode;

            if (errorType == "ImageError" || errorType == "MediaError") {
                return;
            }

            var errMsg = "Silverlight 应用程序中未处理的错误 " + appSource + "\n";

            errMsg += "代码: " + iErrorCode + "    \n";
            errMsg += "类别: " + errorType + "       \n";
            errMsg += "消息: " + args.ErrorMessage + "     \n";

            if (errorType == "ParserError") {
                errMsg += "文件: " + args.xamlFile + "     \n";
                errMsg += "行: " + args.lineNumber + "     \n";
                errMsg += "位置: " + args.charPosition + "     \n";
            }
            else if (errorType == "RuntimeError") {
                if (args.lineNumber != 0) {
                    errMsg += "行: " + args.lineNumber + "     \n";
                    errMsg += "位置: " + args.charPosition + "     \n";
                }
                errMsg += "方法名称: " + args.methodName + "     \n";
            }
            alert(errMsg);
        }
    </script>
</head>
<body>
    <form id="form1" runat="server" style="height:100%">
    <div id="silverlightControlHost">
		<object data="data:application/x-silverlight-2," type="application/x-silverlight-2" width="100%" height="100%">
			<param name="source" value="../ClientBin/CCFlowDesigner.xap"/>
			<param name="onerror" value="onSilverlightError" />
			<param name="background" value="white" />
			<param name="minRuntimeVersion" value="2.0.31005.0" />
            <param name="initParams" value="platForm=JAVA,appName=">
		<%--	<param name="windowless" value="true"/>--%>
			<param name="autoUpgrade" value="true" />
			<a href="http://go.microsoft.com/fwlink/?LinkID=124807" style="text-decoration: none;">
     			<img src="http://go.microsoft.com/fwlink/?LinkId=108181" alt="Get Microsoft Silverlight" style="border-style: none"/>
			</a>
		</object>
		<iframe style='visibility:hidden;height:0;width:0;border:0px'></iframe>
    </div>
    </form>
</body>