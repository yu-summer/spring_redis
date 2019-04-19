<%--
  Created by IntelliJ IDEA.
  User: summer
  Date: 2019/3/31
  Time: 8:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>rules编辑</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="./static/js/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="title.jsp"/>
<div>
    <div class="row">
        <div class="col-md-2">
            <jsp:include page="navigation.jsp"/>
        </div>
        <div class="col-md-10">
            <form>
                <div>
                    <h3 style="float: left" id="fileName">${title}</h3>
                    <div style="float: right ;text-align:right">
                        <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#myModal">新增
                        </button>
                        <button type="button" class="btn btn-warning" id="update">修改</button>
                        <button type="button" class="btn btn-danger" id="delete">删除</button>
                        <button type="button" class="btn btn-primary" disabled="disabled" id="finish">提交</button>
                    </div>
                </div>
                <textarea id="text" style="height: 690px; width: 100%" readonly>${text}</textarea>
            </form>
        </div>
    </div>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="width: 350px">
                <div class="modal-body">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="fileName2" class="label-padding">文件名:</label>
                            <input type="text" id="fileName2" name="fileName2" class="form-control">
                        </div>
                        <div class="form-group">
                            <a id="btn1" class="btn btn-primary">OK</a>
                        </div>
                    </form>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>
</body>
<script type="text/javascript">
    $(document.body).css({
        "overflow-x": "hidden",
        "overflow-y": "hidden"
    });

    $(document).ready(function (e) {
        $("#update").click(function () {
            $("#finish").removeAttr("disabled");
            $("#text").removeAttr("readonly");
        });
        $("#delete").click(function () {
            alert("${title}");
            window.location.href = "delete.do?fileName=" + "${title}";
        });
        $("#finish").click(function () {
            var fileName = $("#fileName").text();
            var content = $("#text").val();
            var rule = {};
            rule.fileName = fileName;
            rule.content = content;
            $.ajax({
                type: 'POST',
                url: 'update.do',
                data: rule,
                dataType: 'json',
                success: function (result) {
                    alert(result.result);
                },
                error: function (result) {
                    alert("error");
                }
            })
        });
        $("#btn1").click(function () {
            var fileName = $("#fileName2").val();
            var rule = {};
            rule.fileName = fileName;
            $.ajax({
                type: 'GET',
                url: 'add.do',
                data: rule,
                dataType: 'json',
                success: function (result) {
                    if (result.result === 'success') {
                        window.location.href = "edit.do?fileName=" + fileName;
                    }
                }
            })
        })
    });
</script>
</html>
